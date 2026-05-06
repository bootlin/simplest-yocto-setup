# Simple recipe for using mainline U-Boot

require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

# Rewrite SRC_URI so we don't download the CVE patches: we fetch a more recent
# version were they have already been applied.
SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master"

# v2026.04
SRCREV = "88dc2788777babfd6322fa655df549a019aa1e69"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

# Recent versions of U-Boot need gnutls headers on host machine.
DEPENDS += "gnutls-native"

SRC_URI:append:freiheit93 = " \
	file://optee.cfg \
	file://0001-binman-Add-optee-binary-to-i.MX9-platform-types.patch \
	file://0002-imx93-Add-support-for-OPTEE.patch \
"

# We will embed boot firmwares, TFA images and optee image in the generated
# binary: we do depend on them.
DEPENDS:append:freiheit93 = " imx-boot-firmware-files trusted-firmware-a optee-os firmware-ele-imx"
EXTRA_OEMAKE:append:freiheit93 = " BINMAN_INDIRS=${RECIPE_SYSROOT}/firmware"

do_configure:append:freiheit93() {
    # Copy tfa, tee and ele firmware binaries in build directory, so they can be found by mkimage
    config="${@ d.getVar('UBOOT_MACHINE').strip()}"
    cp ${STAGING_DIR_HOST}/firmware/bl31.bin ${B}/${config}/
    cp ${STAGING_DIR_HOST}/${nonarch_base_libdir}/firmware/tee-raw.bin ${B}/${config}/tee.bin
    cp ${STAGING_DIR_HOST}/${nonarch_base_libdir}/firmware/imx/ele/${SECO_FIRMWARE_NAME} ${B}/${config}/
}

do_deploy:append:freiheit93() {
    config="${@ d.getVar('UBOOT_MACHINE').strip()}"
    install -m 0644 ${B}/${config}/flash.bin  ${DEPLOYDIR}/flash.bin

    # From meta-freescale uuu_bootloader_tag.bbclass
    # Create a tagged boot partition file for the SD card image file. The tag
    # contains the size of the boot partition image so UUU can easily find
    # the end of it in the SD card image file.
    #
    # IMPORTANT: The tagged boot partition file should never be used directly with
    #            UUU, as it can cause UUU to hang.
    cp ${DEPLOYDIR}/flash.bin ${DEPLOYDIR}/flash.bin.tagged
    stat -L -cUUUBURNXXOEUZX7+A-XY5601QQWWZ%sEND ${DEPLOYDIR}/flash.bin.tagged >> ${DEPLOYDIR}/flash.bin.tagged
}

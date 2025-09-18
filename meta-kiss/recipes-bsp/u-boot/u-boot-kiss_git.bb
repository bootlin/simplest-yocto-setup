# Simple recipe for using mainline U-Boot

require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

# Rewrite SRC_URI so we don't download the CVE patches: we fetch a more recent
# version were they have already been applied.
SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master"

# v2025.07
SRCREV = "e37de002fac3895e8d0b60ae2015e17bb33e2b5b"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

# Recent versions of U-Boot need gnutls headers on host machine.
DEPENDS += "gnutls-native"

# On stm32mp1-based products we want to store the U-Boot environment as a
# file on the 4th partition of the SD card, formatted as an ext4 filesystem.
# Also, we need to revert an incorrect cleanup commit, that removed the
# possibility to store environment on ext4 filesystem.
SRC_URI:append:stm32mp1 = " file://env-on-mmc0-4-ext4.cfg \
                            file://0001-Revert-board-st-stm32mp1-Clean-env_get_location.patch \
                            "

do_deploy:append:stm32mp1() {
    install -D -m 644 ${B}/u-boot.dtb ${DEPLOYDIR}
}

# We need to add support for FRDM i.MX93 board, not released yet.
SRC_URI:append:freiheit93 = " \
    file://0001-imx93_frdm-Add-initial-board-support.patch \
    file://0002-imx93_frdm-Add-support-for-flashing-board-with-UUU.patch \
    file://optee.cfg \
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

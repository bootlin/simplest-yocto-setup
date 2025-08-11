FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:freiheit93 = " file://0001-imx93_frdm-Add-initial-board-support.patch"

DEPENDS:append:freiheit93 = " imx-boot-firmware-files trusted-firmware-a"
EXTRA_OEMAKE:append:freiheit93 = " BINMAN_INDIRS=${RECIPE_SYSROOT}/firmware"

do_deploy:append:freiheit93() {
    # From meta-freescale imx-boot-container.bbclass
    # Deploy the resulted flash.bin for WIC to pick it up
    if [ -n "${UBOOT_CONFIG}" ]; then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -m 0644 ${B}/${config}/flash.bin  ${DEPLOYDIR}/flash.bin-${MACHINE}-${type}
                    # When there's more than one word in UBOOT_CONFIG,
                    # the first UBOOT_CONFIG listed will be the flash.bin binary
                    if [ ! -f "${DEPLOYDIR}/imx-boot" ]; then
                        ln -sf flash.bin-${MACHINE}-${type} flash.bin
                    else
                        bbwarn "Use custom wks.in for $UBOOT_CONFIG = $type"
                    fi
                fi
            done
            unset  j
        done
        unset  i
    fi

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

do_configure:append:freiheit93() {
    # Copy tfa binary in build directory, so it can be found by mkimage
    if [ -n "${UBOOT_CONFIG}" ]; then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    cp ${STAGING_DIR_HOST}/firmware/bl31.bin ${B}/${config}/
                fi
            done
            unset  j
        done
        unset  i
    fi
}

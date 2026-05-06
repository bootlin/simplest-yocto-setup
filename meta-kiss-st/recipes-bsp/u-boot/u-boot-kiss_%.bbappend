FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# On stm32mp1-based products we want to store the U-Boot environment as a
# file on the 4th partition of the SD card, formatted as an ext4 filesystem.
SRC_URI:append:stm32mp1 = " file://env-on-mmc0-4-ext4.cfg"

do_deploy:append:stm32mp1() {
    install -D -m 644 ${B}/u-boot.dtb ${DEPLOYDIR}
}

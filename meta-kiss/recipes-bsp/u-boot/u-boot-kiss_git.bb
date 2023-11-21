# Simple recipe for using mainline U-Boot

require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

# v2023.10
SRCREV = "4459ed60cb1e0562bc5b40405e2b4b9bbf766d57"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

# On stm32mp1-based products we want to store the U-Boot environment as a
# file on the 4th partition of the SD card, formatted as an ext4 filesystem
SRC_URI:append:stm32mp1 = " file://env-on-mmc0-4-ext4.cfg"

DEPENDS:append:stm32mp1 = " python3-setuptools-native"

do_deploy:append:stm32mp1() {
    install -D -m 644 ${B}/u-boot.dtb ${DEPLOYDIR}
}

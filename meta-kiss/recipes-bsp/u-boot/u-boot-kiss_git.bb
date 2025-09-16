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

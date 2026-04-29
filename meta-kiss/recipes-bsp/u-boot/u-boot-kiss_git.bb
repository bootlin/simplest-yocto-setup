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

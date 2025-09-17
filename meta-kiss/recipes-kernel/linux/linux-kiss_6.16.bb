LICENSE = "GPL-2.0-only"

inherit kernel

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.xz"
SRC_URI[sha256sum] = "1a4be2fe6b5246aa4ac8987a8a4af34c42a8dd7d08b46ab48516bcc1befbcd83"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
S = "${WORKDIR}/linux-${PV}"

# For most realistic a customized defconfig is preferred to the default
# architecture defconfig, to enable all the needed features and drivers
# while trimming the kernel and modules size disabling unneeded ones.
SRC_URI += "file://defconfig"

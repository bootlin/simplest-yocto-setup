# Simple recipe for using a mainline Linux kernel

LICENSE = "GPL-2.0-only"

inherit kernel

# As we are using a fully mainline kernel there is no need to clone an
# entire git repository, which would take time and disk space. Downloading
# a tarball is enough. In real cases you might have an internal kernel
# repository with your branches, so it can make sense to fetch from
# there. Or you could be using a mainline kernel with only a few patches on
# top, and in that case downloading a tarball and applying your patches can
# be a good solution.
SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.xz"
SRC_URI[sha256sum] = "73d4f6ad8dd6ac2a41ed52c2928898b7c3f2519ed5dbdb11920209a36999b77e"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
S = "${WORKDIR}/linux-${PV}"

# For most realistic a customized defconfig is preferred to the default
# architecture defconfig, to enable all the needed features and drivers
# while trimming the kernel and modules size disabling unneeded ones.
SRC_URI += "file://defconfig"

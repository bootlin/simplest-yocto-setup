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
SRC_URI[sha256sum] = "8975216a6cee827390586758ed69d19743367098d1fc5dd5694987bb529e44e5"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
S = "${WORKDIR}/linux-${PV}"

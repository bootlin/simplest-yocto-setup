SUMMARY = "Device Tree Lopper"
DESCRIPTION = "Tool for manipulation of system device tree files"
SECTION = "bootloader"

SRC_URI = "git://github.com/Xilinx/lopper.git;branch=${BRANCH};protocol=https"
SRCREV = "2886fae1570b394c3cc36a990b7ebd3659cb580e"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=8e5f5f691f01c9fdfa7a7f2d535be619"

BRANCH = "xlnx_rel_v${PV}"

S = "${WORKDIR}/git"

inherit setuptools3 native

PYPA_WHEEL = "${PIP_INSTALL_DIST_PATH}/${BPN}-${PV}-*.whl"

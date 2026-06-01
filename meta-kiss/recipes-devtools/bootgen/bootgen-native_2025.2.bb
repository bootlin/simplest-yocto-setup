SUMMARY = "Building and installing bootgen"
DESCRIPTION = "Building and installing bootgen, a Xilinx tool that lets you stitch \
	       binary files together and generate device boot images"

inherit native

SRC_URI = "git://github.com/Xilinx/bootgen.git;protocol=https;branch=${BRANCH}"
BRANCH = "xlnx_rel_v${PV}"
SRCREV = "0e336a00dcff5842648f4a1e9f919abf7c960d97"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=04054e01a445f223956a47542277e6ad"

DEPENDS += "openssl-native"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${bindir}
    install -Dm 0755 ${S}/build/bin/bootgen ${D}${bindir}
}

FILES:${PN} = "${bindir}/bootgen"

PACKAGES = "${PN}"

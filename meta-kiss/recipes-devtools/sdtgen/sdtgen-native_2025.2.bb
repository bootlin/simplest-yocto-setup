SUMMARY = "Fetching and installing sdtgen"
DESCRIPTION = "Fetching and installing sdtgen, a Xilinx tool that extract system \
	       devicetree from a xsa archive"

inherit native

SRC_URI = " \
	https://edf.amd.com/sswreleases/rel-v${PV}/xsct-trim/xsct-2025-2_1110.tar.xz \
	git://github.com/Xilinx/system-device-tree-xlnx.git;protocol=https;branch=${BRANCH};name=system-device-tree-xlnx;subdir=Vitis/${PV}/data/system-device-tree-xlnx \
"
SRC_URI[sha256sum] = "3ae9e4cd07e179a016467d6eb743d04a389af9857a1c72c8c6104cfc47fca823"
SRCREV_system-device-tree-xlnx = "d97e6c72b1af891bc9de2703730d2cbb2044c1e3"

BRANCH = "xlnx_rel_v${PV}"

LICENSE = "CLOSED & BSD-3-Clause"
LIC_FILES_CHKSUM = " \
	file://license/unified_3rd_party_eulas.txt;md5=f4e6e6df3691bb1ea2998469929cccbc \
	file://data/system-device-tree-xlnx/license.txt;md5=cb38da55d94de1ca24b3a8a0a5f2b0d9 \
"

DEPENDS = "rsync-native"

S = "${WORKDIR}/Vitis/${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

do_install() {
	install -d ${D}${bindir}/vitis
	rsync -a ${S}/* ${D}${bindir}/vitis/
}

FILES:${PN} = "${bindir}/vitis/*"

PACKAGES = "${PN}"

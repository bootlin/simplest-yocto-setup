DESCRIPTION = "XSA file source"
LICENSE = "CLOSED"

inherit native

# K24 prebuilt XSA file is used here. To use your custom Vivado-generated
# XSA file, rename it to 'system.xsa' and update XSA_FILE_SRC_URI.
XSA_FILE_SRC_URI ?= "https://edf.amd.com/sswreleases/rel-v2025.2/hdf-examples/2025.2/2025.2_1115_1_11150546/external/k24-smk-kd/k24-smk-kd-2025.2_1115_1.xsa;downloadfilename=system.xsa"
SRC_URI = "${XSA_FILE_SRC_URI}"
SRC_URI[sha256sum] = "9d36d4079f1fa43179780e1ebf2fb43683a60c131f43ed65ff0876b3e4d88142"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}${localstatedir}/xsa
	cp ${WORKDIR}/system.xsa ${D}${localstatedir}/xsa/system.xsa
}
FILES:${PN} = "${localstatedir}/xsa/system.xsa"

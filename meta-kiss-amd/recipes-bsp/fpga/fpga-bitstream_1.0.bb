DESCRIPTION = "Extract FPGA bitstream from XSA"
LICENSE = "CLOSED"

DEPENDS += "unzip-native xsa-native"

do_configure() {
	unzip -o ${STAGING_DIR_NATIVE}${localstatedir}/xsa/system.xsa -d ${S}
}

do_compile[noexec] = "1"

do_install() {
	install -d ${D}/boot
	install -m 0644 ${S}/*.bit ${D}/boot/fpga.bit
}
FILES:${PN} = "/boot/fpga.bit"

SUMMARY = "Building and installing ZynqMP pmufw"

SRC_URI = "git://github.com/Xilinx/embeddedsw.git;protocol=https;branch=${BRANCH}"
BRANCH = "xlnx_rel_v${PV}"
SRCREV = "145cea8fcf98268c8b163f732c181f008e887e53"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSES/MIT;md5=e8f57dd048e186199433be2c41bd3d6d"

S = "${WORKDIR}/git/lib/sw_apps/zynqmp_pmufw/src"

CFLAGS += "-Os -flto -ffat-lto-objects"

EXTRA_OEMAKE += 'CC="${CC}" ARCHIVER="${AR}" COMPILER="${CC}" CFLAGS="${CFLAGS}"'

do_install() {
	install -d ${D}/boot
	install -m 0644 ${B}/executable.elf ${D}/boot/pmufw.elf
}

FILES:${PN} = "/boot/pmufw.elf"

inherit deploy image-artifact-names

PMUFW_BASE_NAME = "pmufw-${MACHINE}${IMAGE_VERSION_SUFFIX}"

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${B}/executable.elf ${DEPLOYDIR}/${PMUFW_BASE_NAME}.elf
	ln -sf ${PMUFW_BASE_NAME}.elf ${DEPLOYDIR}/pmufw-${MACHINE}.elf
	ln -sf ${PMUFW_BASE_NAME}.elf ${DEPLOYDIR}/pmufw.elf
}
addtask deploy after do_install before do_build

COMPATIBLE_MACHINE = "^(zynqmp)$"

PACKAGE_ARCH = "${MACHINE_ARCH}"

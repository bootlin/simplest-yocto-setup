SUMMARY = "Generates boot.bin using bootgen tool"

inherit deploy

SRC_URI = "file://bootgen.bif"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

COMPATIBLE_MACHINE = "^(zynqmp)$"

DEPENDS = "bootgen-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure[noexec] = "1"
do_compile[mcdepends] += "mc::zynqmp-microblaze:zynqmp-pmufw:do_deploy"
do_compile[depends] += " \
	zynqmp-fsbl:do_deploy \
	trusted-firmware-a:do_deploy \
	virtual/bootloader:do_deploy \
"

B = "${S}/build"

do_compile() {
	cp ${TOPDIR}/tmp-newlib_microblaze/deploy/images/${MACHINE}/pmufw.elf ${B}/pmufw.elf
	cp ${DEPLOY_DIR_IMAGE}/fsbl.elf ${B}/fsbl.elf
	cp ${DEPLOY_DIR_IMAGE}/bl31.elf ${B}/bl31.elf
	cp ${DEPLOY_DIR_IMAGE}/u-boot.elf ${B}/u-boot.elf
	cp ${DEPLOY_DIR_IMAGE}/u-boot.dtb ${B}/u-boot.dtb
	bootgen -image ${WORKDIR}/bootgen.bif -arch zynqmp -w -o ${B}/boot.bin
}

do_install() {
	install -d ${D}/boot
	install -m 0644 ${B}/boot.bin ${D}/boot/boot.bin
}
FILES:${PN} += "/boot/boot.bin"

inherit image-artifact-names

BOOTBIN_BASE_NAME = "boot-${MACHINE}${IMAGE_VERSION_SUFFIX}"

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${B}/boot.bin ${DEPLOYDIR}/${BOOTBIN_BASE_NAME}.bin
	ln -sf ${BOOTBIN_BASE_NAME}.bin ${DEPLOYDIR}/boot-${MACHINE}.bin
	ln -sf ${BOOTBIN_BASE_NAME}.bin ${DEPLOYDIR}/boot.bin
}
addtask deploy after do_install

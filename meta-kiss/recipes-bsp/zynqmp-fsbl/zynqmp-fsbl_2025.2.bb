SUMMARY = "Building and installing ZynqMP fsbl"

SRC_URI = "git://github.com/Xilinx/embeddedsw.git;protocol=https;branch=${BRANCH}"
BRANCH = "xlnx_rel_v${PV}"
SRCREV = "145cea8fcf98268c8b163f732c181f008e887e53"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSES/MIT;md5=e8f57dd048e186199433be2c41bd3d6d"

S = "${WORKDIR}/git"

DEPENDS = "xsa-native sdtgen-native lopper-native python3-pyyaml-native \
	   python3-humanfriendly-native python3-ruamel-yaml-native \
	   python3-anytree-native dtc-native ninja-native"

do_configure() {
	${STAGING_DIR_NATIVE}${bindir}/vitis/bin/sdtgen \
		-xsa ${STAGING_DIR_NATIVE}${localstatedir}/xsa/system.xsa -dir ${B}/sdt_out
}

do_compile() {
	export LOPPER_DTC_FLAGS="-b 0 -@"
	export PATH=$PATH:${STAGING_DIR_NATIVE}${bindir}/vitis/gnu/aarch64/lin/aarch64-none/bin/
	python3 ${S}/scripts/pyesw/repo.py -st ${B}
	python3 ${S}/scripts/pyesw/create_bsp.py -w zynqmp_fsbl_bsp -p psu_cortexa53_0 \
		-s ${B}/sdt_out/system-top.dts -t zynqmp_fsbl -r .repo.yaml
	python3 ${S}/scripts/pyesw/create_app.py -d zynqmp_fsbl_bsp -s zynqmp_fsbl/src \
		-t zynqmp_fsbl -n zynqmp_fsbl
	python3 ${S}/scripts/pyesw/build_app.py -s zynqmp_fsbl/src -b zynqmp_fsbl/build
}

do_install() {
    install -d ${D}/boot
    install -Dm 0755 ${B}/zynqmp_fsbl/build/zynqmp_fsbl.elf ${D}/boot/fsbl.elf
}

FILES:${PN} = "/boot/fsbl.elf"

inherit deploy image-artifact-names

FSBL_BASE_NAME = "fsbl-${MACHINE}${IMAGE_VERSION_SUFFIX}"

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${B}/zynqmp_fsbl/build/zynqmp_fsbl.elf ${DEPLOYDIR}/${FSBL_BASE_NAME}.elf
	ln -sf ${FSBL_BASE_NAME}.elf ${DEPLOYDIR}/fsbl-${MACHINE}.elf
	ln -sf ${FSBL_BASE_NAME}.elf ${DEPLOYDIR}/fsbl.elf
}
addtask deploy after do_install

COMPATIBLE_MACHINE = "^(zynqmp)$"

PACKAGE_ARCH = "${MACHINE_ARCH}"

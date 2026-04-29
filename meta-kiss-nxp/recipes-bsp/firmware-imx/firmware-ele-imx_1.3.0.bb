# Copyright 2021-2024 NXP
SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Secure Enclave firmware for i.MX series SoCs"
SECTION = "base"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ca53281cc0caa7e320d4945a896fb837"
LICENSE_FLAGS = "NXP_EULA_v57"
LICENSE_FLAGS_DETAILS[NXP_EULA_v57] = "For further details, see ${THISDIR}/files/EULAv57."

IMX_SRCREV_ABBREV = "17945fc"
NXP_FIRMWARE_ARCHIVE = "firmware-ele-imx-${PV}-${IMX_SRCREV_ABBREV}.bin"
SRC_URI = "https://www.nxp.com/lgfiles/NMG/MAD/YOCTO/${NXP_FIRMWARE_ARCHIVE};fsl-eula=true"
SRC_URI[sha256sum] = "8791109824767346237e53ac2c712824e54608e2092859161e6bb3e5385a7595"

S = "${WORKDIR}/firmware-ele-imx-${PV}-${IMX_SRCREV_ABBREV}"

python do_unpack:append() {
    cmd = "sh %s --auto-accept --force" % d.getVar('NXP_FIRMWARE_ARCHIVE')
    bb.process.run(cmd, shell=True, cwd=d.getVar('WORKDIR', True))
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/imx/ele
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME} ${D}${nonarch_base_libdir}/firmware/imx/ele
}

FILES:${PN} += "${nonarch_base_libdir}/firmware/imx/ele/${SECO_FIRMWARE_NAME}"

COMPATIBLE_MACHINE = "^freiheit93$"

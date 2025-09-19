# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2024 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SUMMARY = "Freescale i.MX Firmware files used for boot"
SECTION = "base"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=c0fb372b5d7f12181de23ef480f225f3"
LICENSE_FLAGS = "NXP_EULA_v58"
LICENSE_FLAGS_DETAILS[NXP_EULA_v58] = "For further details, see ${THISDIR}/files/EULAv58."

NXP_FIRMWARE_ARCHIVE = "firmware-imx-${PV}-${IMX_SRCREV_ABBREV}.bin"
SRC_URI = "https://www.nxp.com/lgfiles/NMG/MAD/YOCTO/${NXP_FIRMWARE_ARCHIVE};fsl-eula=true"
SRC_URI[sha256sum] = "61f925e606ab020b1a36f3f7f7e459c6847f5b9dbc79421f9ef86e8fc124eb2f"

IMX_SRCREV_ABBREV = "5af0ceb"
S = "${WORKDIR}/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}"

inherit deploy nopackages

python do_unpack:append() {
    cmd = "sh %s --auto-accept --force" % d.getVar('NXP_FIRMWARE_ARCHIVE')
    bb.process.run(cmd, shell=True, cwd=d.getVar('WORKDIR', True))
}

do_deploy() {
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done
}

addtask deploy after do_install before do_build

do_install () {
    install -d ${D}/firmware
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${D}/firmware
    done
}

SYSROOT_DIRS += "/firmware"

COMPATIBLE_MACHINE = "^(freiheit93)$"

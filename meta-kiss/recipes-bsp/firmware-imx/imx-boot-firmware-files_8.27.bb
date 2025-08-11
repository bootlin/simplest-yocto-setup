# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2024 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SUMMARY = "Freescale i.MX Firmware files used for boot"
SECTION = "base"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=c0fb372b5d7f12181de23ef480f225f3"
LICENSE_FLAGS = "NXP_EULA_v58"
LICENSE_FLAGS_DETAILS[NXP_EULA_v58] = "For further details, see ${THISDIR}/files/EULA."

SRC_URI = "https://www.nxp.com/lgfiles/NMG/MAD/YOCTO/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "61f925e606ab020b1a36f3f7f7e459c6847f5b9dbc79421f9ef86e8fc124eb2f"

IMX_SRCREV_ABBREV = "5af0ceb"
S = "${WORKDIR}/firmware-imx-${PV}-${IMX_SRCREV_ABBREV}"

inherit deploy nopackages

python do_unpack() {
    src_uri = (d.getVar('SRC_URI') or "").split()
    if len(src_uri) == 0:
        return

    fetcher = bb.fetch2.Fetch(src_uri, d)

    for url in fetcher.ud.values():
        cmd = "sh %s --auto-accept --force" % (url.localpath)
        bb.fetch2.runfetchcmd(cmd, d, quiet=True, workdir=d.getVar('WORKDIR', True))
}

do_check_license() {
    # Make sure the license corresponds to the one pointed by LICENSE_FLAGS_DETAILS.
    diff "${THISDIR}/files/EULA" "${S}/COPYING"
}

addtask check_license after do_unpack before do_populate_lic

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

COMPATIBLE_MACHINE = "(freiheit93)"

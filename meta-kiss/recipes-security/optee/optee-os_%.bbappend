FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Mainline optee-os does not boot on i.MX93 without a fix: apply it here
SRC_URI:append:freiheit93 = "file://0001-core-imx-fix-CFG_TZDRAM_START.patch"

EXTRA_OEMAKE:append:freiheit93 = " PLATFORM=imx-mx93evk"
COMPATIBLE_MACHINE:freiheit93 = "^freiheit93$"

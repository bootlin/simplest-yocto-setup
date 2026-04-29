# This bbappend file is overriding all recipes starting with optee-os: optee-os
# and optee-os-tadevkit.
# This mean we cannot use "${THISDIR}/${PN}" here.
FILESEXTRAPATHS:prepend := "${THISDIR}/optee-os:"

# Mainline optee-os does not boot on i.MX93 without a fix: apply it here
SRC_URI:append:freiheit93 = "file://0001-core-imx-fix-CFG_TZDRAM_START.patch"

EXTRA_OEMAKE:append:freiheit93 = " PLATFORM=imx-mx93evk"
COMPATIBLE_MACHINE:freiheit93 = "^freiheit93$"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BP}:"

# This is a patch from upstream trusted-firmware-a to fix build failure
# with kirkstone:
#
#   dtc version too old (v1.6.1+), you need at least version 1.4.4
#
SRC_URI += "file://0001-fix-st-remove-extra-chars-from-dtc-version.patch"

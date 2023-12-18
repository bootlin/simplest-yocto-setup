# This recipe is an example of a real-world recipe for a custom application
# running on a product.

SUMMARY = "A program to make you smile when you mistype 'ls'"
HOMEPAGE = "https://github.com/mtoyoda/sl"
# Believe it or not, the SL license is in the SPDX database...
LICENSE = "SL"
# ...but it is not in the generic openembedded-core licenses, so avoid
# avoid QA warnings:
NO_GENERIC_LICENSE[SL] = "LICENSE"

SRC_URI = "git://github.com/mtoyoda/sl.git;protocol=https;branch=master"
LIC_FILES_CHKSUM = "file://LICENSE;md5=74e9dd589a0ab212a9002b15ef2b82f0"

PV = "1.0+git${SRCPV}"
SRCREV = "923e7d7ebc5c1f009755bdeb789ac25658ccce03"
S = "${WORKDIR}/git"

DEPENDS = "ncurses"

# Work around the broken Makefile which hard-codes the CC variable
EXTRA_OEMAKE = "-e"

# Work around QA error due to the over-simplistic Makefile:
#   [...] QA Issue: File /usr/bin/sl in package sl doesn't have GNU_HASH (didn't pass LDFLAGS?) [ldflags]
INSANE_SKIP:${PN} += "ldflags"

# Using meson, CMake or other build systems makes life easier also for
# recipe writers and can avoid the need of do_install entirely. This does
# not happen for sl, unfortunately.
do_install () {
	install -d ${D}${bindir}
	install -m 755 sl ${D}${bindir}/
}

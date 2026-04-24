FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS += "dtc-native"

SRC_URI:append:krazymp = " \
	file://0001-usb-onboard-hub-Update-the-delay-for-Microchip-USB57.patch \
	file://0002-config_distro_bootcmd-Add-FPGA-bitstream-loading-dur.patch \
"

# On ZynqMP board as we are building ZynqMP FSBL we have to disable U-boot SPL
# and binman.
# Also, we add an environment to load FPGA bitstream during Kria boot sequence
SRC_URI:append:krazymp = " file://no-spl-binman.cfg"

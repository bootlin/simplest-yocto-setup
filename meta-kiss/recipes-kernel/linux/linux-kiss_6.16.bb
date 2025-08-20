require linux-kiss.inc

SRC_URI[sha256sum] = "1a4be2fe6b5246aa4ac8987a8a4af34c42a8dd7d08b46ab48516bcc1befbcd83"

SRC_URI:append:freiheit93 = " file://0001-arm64-dts-add-i.MX93-11x11-FRDM-basic-support.patch"

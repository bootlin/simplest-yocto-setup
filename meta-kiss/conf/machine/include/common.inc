# Common definitions for all the KISS machines

# Generate an extlinux configuration file
UBOOT_EXTLINUX = "1"
UBOOT_EXTLINUX_ROOT ?= "root=/dev/mmcblk0p2"
# Install the extlinux conf file in /boot, and also the device tree it needs
MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "u-boot-kiss-extlinux kernel-devicetree"

# Install kernel modules if they are enabled
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "kernel-modules"

# Generate a bootable uSD card image using wic. Also generate a bmap file
# to speed up writing to a card by avoiding to write uninitialized data.
WKS_FILE = "${MACHINE}.wks.in"
IMAGE_FSTYPES:append = " wic wic.bmap"

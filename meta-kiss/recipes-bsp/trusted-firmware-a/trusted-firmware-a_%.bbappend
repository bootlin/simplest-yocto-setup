COMPATIBLE_MACHINE:stompduck = "^stompduck$"

# trusted-firmware-a can depend on "u-boot", we have "u-boot-kiss"
DEPENDS += "virtual/bootloader optee-os"

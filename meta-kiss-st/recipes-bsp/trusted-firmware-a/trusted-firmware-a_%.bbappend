COMPATIBLE_MACHINE:stompduck = "^stompduck$"

# trusted-firmware-a can depend on "u-boot", we have "u-boot-kiss"
# Here the dependency is only needed for stompduck.
DEPENDS:append:stompduck = " virtual/bootloader optee-os"

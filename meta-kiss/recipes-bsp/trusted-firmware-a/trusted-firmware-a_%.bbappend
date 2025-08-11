COMPATIBLE_MACHINE:stompduck = "^stompduck$"
COMPATIBLE_MACHINE:freiheit93 = "^freiheit93$"

# trusted-firmware-a can depend on "u-boot", we have "u-boot-kiss"
DEPENDS:append:stompduck = " virtual/bootloader"

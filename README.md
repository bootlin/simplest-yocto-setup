# simplest-yocto-setup

`simplest-yocto-setup` is an example of the simplest, but realistic and
working, Yocto/OpenEmbedded setup.

It aims at providing an example of how Yocto/OE can be used as the embedded
Linux build system for end products without unnecessary complications.

# Why?

While working for several Bootlin customers on their Yocto/OpenEmbedded
setups we have seen many problems caused by unnecessary complications in
their layers.

We have spent a lot of time in educating to writing clean layers, which
often involved fixing problems by removing a lot of the code they had
written or they had taken from existing third-party layers. In other words:
making the code simple and "stupid", resulting in a more understandable,
more efficient, easier to upgrade and less buggy build environment.

This repository is implementing a similar setup, aiming at being a
reference for product companies in need to set up a Yocto/OpenEmbedded
build environment or to clean up what they already have.

# What's inside?

This repository is composed of:

 * `.config.yaml`: a kas configuration file
 * `meta-kiss`: the layer with the (fictitious) metadata for the products
   of a (fictitious) company

The `.config.yaml` is the configuration file for the
[kas](https://kas.readthedocs.io/) utility, which allows to easily download
all the required third-party components in the correct place and enable
them in the configuration. In this example it downloads and enables:

 * the `bitbake` build engine
 * the `openembedded-core` repository which contains the `meta` layer
   with all the "core" recipes
 * the `meta-arm` repository which contains the `meta-arm` and
   `meta-arm-toolchain` layers
 * the `meta-kiss` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`

Using kas is not mandatory to use Yocto/OpenEmbedded, but we found it
simple and convenient. You can use another tool for your project if so you
prefer.

# The `meta-kiss` layer

`meta-kiss` is a layer that demonstrates how a realistic layer for a
product company can (and, in our opinion, should) look like.

It is named after the KISS principle which "states that most systems work
best if they are kept simple rather than made complicated" (source:
[Wikipedia](https://en.wikipedia.org/wiki/KISS_principle)).

Here we used "kiss" as the hypothetical name of a fictitious company. The
machine configurations in meta-kiss implement fictitious products, but
except for their name they are actual development boards and the output
images can be used on these boards. In real world use cases a layer
implementing company products can reasonably be called
`meta-<company-name>` and the machine names should be named after the
product names.

The `meta-kiss` layer provides:

 * support for two machines
 * a distro configuration
 * a few recipes, including kernel, U-Boot, a userspace application and an
   image recipe

Note that `meta-kiss` is a single layer. The `bitbake` Yocto/OE build
engine is powerful enough to handle lots of machines, recipes and even
multiple distros in a single layer. Thus using a simple layer in your
company is perfectly fine and encouraged, unless your need are so complex
that splitting it into multiple layers proves useful.

## Machines

The meta-kiss layer contains two machine configurations, called
**dogbonedark**, **stompduck** and **freiheit93**.

The **dogbonedark** machine describes a fictitious product which in reality
implements the [BeagleBone®
Black](https://www.beagleboard.org/boards/beaglebone-black). In order to
implement it we took the relevant content from [the BeagleBone machine
configuration](https://git.yoctoproject.org/meta-ti/tree/meta-ti-bsp/conf/machine/beaglebone.conf)
found in the meta-ti-bsp layer.

We could of course have used the meta-ti-bsp layer directly, however since
the hardware is very well supported by the mainline kernel and U-Boot we
only needed to write (or copy and paste!) only a small amount of code.

Several BSP layers provided by hardware vendors bring in extra complexity,
deviation from standard coding practices and even bugs and unnecessarily
complex code. In the spirit of this project, we chose to provide an example
of how you can do without them in many cases.

The **stompduck** machine describes a fictitious product which in reality
implements the
[STM32MP157A-DK1](https://www.st.com/en/evaluation-tools/stm32mp157a-dk1.html). For
the same motivations, the minimum necessary code in this case was taken
from [the STM32MP1 machine
configuration](https://github.com/STMicroelectronics/meta-st-stm32mp/blob/mickledore/conf/machine/stm32mp1.conf)
found in the meta-st-stm32mp layer.

In addition to the steps needed to implement the `dogbonedark`, for the
`stompduck` machine we additionally chose to boot it using
[TrustedFirmware-A (TF-A)](https://www.trustedfirmware.org/projects/tf-a/).
In order to build TF-A, using [the existing
recipe](https://git.yoctoproject.org/meta-arm/tree/meta-arm/recipes-bsp/trusted-firmware-a)
from the meta-arm layer looked like a good choice given the balance between
the code quality of the meta-arm layer itself and the complexity required
for a recipe to build TF-A. So we added this layer to the kas configuration
file together with the meta-arm-toolchain layer it depends on.

The **freiheit93** machine describes a fictitious product which in reality
implements the [FRDM
i.MX93](https://www.nxp.com/design/design-center/development-boards-and-designs/frdm-i-mx-93-development-board:FRDM-IMX93).
Here the minimal necessary code was take from [meta-imx-frdm i.MX93 machine
configuration](https://github.com/nxp-imx-support/meta-imx-frdm/blob/lf-6.6.36-2.1.0/meta-imx-bsp/conf/machine/imx93-11x11-lpddr4x-frdm.conf)
and [meta-freescale i.MX93 machine
include](https://git.yoctoproject.org/meta-freescale/tree/conf/machine/include/imx93-evk.inc).
Additionally, firmware recipe was taken from [meta-freescale boot
firmwares](https://git.yoctoproject.org/meta-freescale/tree/recipes-bsp/firmware-imx/imx-boot-firmware-files_8.27.bb).

As for the `stompduck` machine, we are relying on meta-arm to build the TF-A
firmware.

Here we also showcase the handling of proprietary licenses that have to be
accepted before building a component: the NXP firmwares require acceptance of
the NXP EULA, by adding `NXP_EULA_v57` and `NXP_EULA_v58` to the
`LICENSE_FLAGS_ACCEPTED` variable.

# How do I use it?

According to the kas configuration file, after cloning this repository the
`dogbonedark` board is configured by default. Here's how you can have a
working image for that board in a few steps:

```bash
# If you don't have kas yet (needed once only):
pip install kas

# Use kas to download the third-party repositories needed
# (required the first time, or after changes to .config.yaml)
kas checkout

# Initialize the build environment
. openembedded-core/oe-init-build-env

# For i.MX93, you will have to accept the NXP licenses, after reading their
# text in meta-kiss/recipes-bsp/firmware-imx/files:
echo 'LICENSE_FLAGS_ACCEPTED += "NXP_EULA_v57 NXP_EULA_v58"' >> conf/site.conf

# Run your first build
bitbake kiss-image

# Have dinner

# Find the output images here
ls -l tmp-glibc/deploy/images/dogbonedark/

# Flash the image (use your uSD card device instead of XYZ!):
sudo bmaptool copy tmp-glibc/deploy/images/dogbonedark/kiss-image-dogbonedark.wic /dev/XYZ
```

To use a different machine you can set `MACHINE ?= "stompduck"` in
`conf/site.conf` or in your shell environment and replace the machine name
in the paths.

# That's all!

That's all! Have a look around the code to know about the implementation
details. We wrote some explanatory comments here and there which should
help you understand the reason of several choices we made.

We also added some explanations in the git commit messages when the changes
being committed were possibly not obvious. Look at the git history to
discover the various steps we took, for example how we created the new
meta-kiss layer skeleton initially and the process we took to add and
modify a kernel defconfig.

In the end we hope you like the advantages of this clean setup:

 * uses Yocto scarthgap and builds on modern distros without the need of a
   container -- which you can of course use when it makes sense to
 * no manual cloning of layers: kas does it all for you
 * no manual configuration, except of course for selecting a machine
 * a little amount of code: 333 lines in meta-kiss at the time of this
   writing, documentation included, not including the kernel defconfigs
 * and, most important, readable code -- at least we hope so!

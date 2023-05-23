# simplest-yocto-setup

`simplest-yocto-setup` is an example of the simplest, but realistic and
working, Yocto/OpenEmbedded setup.

It aims at providing an example of how Yocto/OE can be used as the embedded
Linux build system for end products without unnecessary complications.

# How it is made?

This repository is made by:

 * `.config.yaml`: a kas configuration file
 * `meta-kiss`: the layer with the (fictitious) metadata for the products
   of a (fictitious) company

The `.config.yaml` is the configuration file for the kas utility, which
allows to easily download all the required third-party components in the
correct place. In this example it downloads the `bitbake` build engine, the
`openembedded-core` repository which contains all the core recipes and the
`meta-arm` repository which contains the `meta-arm` and
`meta-arm-toolchain` layers.

Using kas is not mandatory to use Yocto/OpenEmbedded, but we found it
simple and convenient. You can use another tool for your project of so you
prefer.

`meta-kiss` is a layer that demonstrates how a realistic layer for a
product company can look like.

It is named after a fictitious company and its machines implement fictioius
products. However the machines implemented here, except for their name, are
actual development boards and the output images can be used on these
boards. In real world use cases a layer implementing a company products can
reasonably be called `meta-<company-name>`.

The `meta-kiss` layer provides:

 * a distro configuration
 * a few recipes
 * support for two machines

Note that `meta-kiss` is a single layer. The `bitbake` Yocto/OE build
engine is powerful enough to handle lots of machines, recipes and even
multiple distros in a single layer. Thus using a simple layer in your
company is perfetcly fine and encouraged, unless your need are so complex
that splitting it into multiple layers is useful.

# How do I use it?

Quick user guide:

```bash
# If you don't have kas yet:
pip install kas

# Use kas to download the third-party repositories needed
# (required the first time, or after changes to .config.yaml)
kas checkout

# Initialize the build environment
. openembedded-core/oe-init-build-env

# Run your first build
bitbake kiss-image

# Have dinner

# Find the output images here
ls -l tmp-glibc/deploy/images/dogboneblack/

# Flash the image (replace your device name!):
sudo bmaptool copy tmp-glibc/deploy/images/dogboneblack/kiss-image-dogboneblack.wic /dev/XYZ
```

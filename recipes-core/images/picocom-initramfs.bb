SUMMARY = "initramfs bundle image for picocom pc80x board"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL} \
                mtd-utils"

INITRAMFS_IMAGE = "core-image-minimal"
INITRAMFS_IMAGE_BUNDLE = "1"
IMAGE_FSTYPES = "cpio.gz"
IMAGE_LINGUAS = " "
LICENSE = "MIT"

inherit core-image

SUMMARY = "standard image for picocom pc80x board"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL} \
                e2fsprogs mtd-utils packagegroup-core-ssh-openssh \
                openssh-sftp-server e2fsprogs-resize2fs libyang \
                libnetconf2 netopeer2 sysrepo"

EXTRA_IMAGE_FEATURES += "package-management"

TOOLCHAIN_TARGET_TASK += "kernel-devsrc"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "65536"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

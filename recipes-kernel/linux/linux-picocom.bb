require linux-mainline-common.inc

SUMMARY = "Pico ast-v5_2_0 Linux 5.4"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV = "3beafe6a3b3be93ac70f1a2fabdf3b6f8c79fb9f"
BRANCH = "dev_pc805_bringup"
SRC_URI = "git://github.com/picocom-chips/linux.git;protocol=https;branch=${BRANCH} \
           file://pc805_defconfig \
           file://pc805.dts \
          "

do_configure:append:pc805() {
    mkdir -p ${S}/arch/riscv/boot/dts/picocom
    cp ${WORKDIR}/pc805.dts ${S}/arch/riscv/boot/dts/picocom
}

LINUX_VERSION ?= "v5.4.147"
LINUX_VERSION_EXTENSION:append:pc805 = "-pc805"

KERNEL_DEFCONFIG:pc805 = "${WORKDIR}/pc805_defconfig"

COMPATIBLE_MACHINE = "(pc805)"

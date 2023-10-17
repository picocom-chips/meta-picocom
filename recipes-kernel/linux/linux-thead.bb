require recipes-kernel/linux/linux-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SUMMARY = "Linux 5.10 from t-head"

SRCREV ="${AUTOREV}"
SRCBRANCH = "th1520"

SRC_URI = "git://git@github.com/T-head-Semi/linux.git;protocol=http;branch=${SRCBRANCH} \
           file://0001-riscv-defconfig-remove-CONFIG_VECTOR.patch \
           "

COMPATIBLE_MACHINE = "(pc803)"

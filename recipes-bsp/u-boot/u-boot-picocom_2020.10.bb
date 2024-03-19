require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/vfw4spl:"
DEPENDS:append = " bc-native u-boot-tools-native python3-setuptools-native"

BRANCH = "picocom/master"
SRCREV = "af0e46e2dfd8bb0152fd04466737aecf83c1214f"
SRC_URI = " \
    git://github.com/picocom-chips/u-boot.git;protocol=https;branch=${BRANCH} \
    file://mmc-support.cfg \
    file://opensbi-options.cfg \
    file://tftp-mmc-boot.txt \
    file://uEnv.txt \
    file://pc805_vfw4spl.h \
    file://libpc805_vfw4spl.a \
    "

do_compile[depends] += "opensbi-picocom:do_deploy"

do_compile:prepend:pc805() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
    export PICO_VFW_LIB_PATH=${WORKDIR}
}

do_deploy:append:pc805() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOYDIR}
    fi

    if [ -f "${WORKDIR}/uEnv.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${WORKDIR}/uEnv.txt ${DEPLOYDIR}
    fi

    if [ -f "${B}/u-boot.bin" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${B}/u-boot.bin ${DEPLOYDIR}/u-boot.bin-${MACHINE}-${PV}-${PR}
        ln -sf u-boot.bin-${MACHINE}-${PV}-${PR} u-boot.bin-${MACHINE}
        ln -sf u-boot.bin-${MACHINE}-${PV}-${PR} u-boot.bin
    fi
}

COMPATIBLE_MACHINE = "(pc805)"


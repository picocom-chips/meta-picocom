require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot 2022.01"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
DEPENDS:append = " bc dtc opensbi-picocom u-boot-tools-native python3-setuptools-native bc-native dtc-native "


SRCBRANCH = "zm/develop"
SRC_URI = "\
    git://git@glhk.picocomtech.com/zaid.mougamadou/u-boot.git;protocol=ssh;branch=${SRCBRANCH} \
    "

#SRCREV = "d637294e264adfeb29f390dfc393106fd4d41b17"
SRCREV = "${AUTOREV}"

do_compile:prepend() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

COMPATIBLE_MACHINE = "(pc803)"

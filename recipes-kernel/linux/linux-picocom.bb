LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

inherit kernel
inherit cml1

SUMMARY = "AndeSight 5.1.0 Linux 5.4 + Picocom"

SRCREV ="${AUTOREV}"
SRCBRANCH = "pz1_dev"

SRC_URI = "git://git@glhk.picocomtech.com/tools/linux.git;protocol=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

do_configure() {
    oe_runmake -C ${S} O=${B} "${KBUILD_DEFCONFIG}"

    config_fragments="${@" ".join(find_cfgs(d))}"
    if [ -n "${config_fragments}" ]; then
        ${STAGING_KERNEL_DIR}/scripts/kconfig/merge_config.sh -m -o ${B} ${B}/.config ${config_fragments}
        oe_runmake -C ${S} O=${B} oldconfig
    fi
}

COMPATIBLE_MACHINE = "(pc805)"

KERNEL_VERSION_SANITY_SKIP = "1"

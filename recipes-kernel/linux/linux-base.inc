LICENSE = "GPL-2.0-only"

inherit kernel
inherit cml1

S = "${WORKDIR}/git"

do_configure() {
    oe_runmake -C ${S} O=${B} "${KBUILD_DEFCONFIG}"

    config_fragments="${@" ".join(find_cfgs(d))}"
    if [ -n "${config_fragments}" ]; then
        ${STAGING_KERNEL_DIR}/scripts/kconfig/merge_config.sh -m -o ${B} ${B}/.config ${config_fragments}
        oe_runmake -C ${S} O=${B} oldconfig
    fi
}

KERNEL_VERSION_SANITY_SKIP = "1"

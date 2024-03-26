# give sudo access to sudo groups 
do_install:append () {
    sed -i 's/# %sudo/%sudo/' ${D}/etc/sudoers
}

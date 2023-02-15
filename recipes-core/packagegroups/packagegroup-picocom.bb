SUMMARY = "Picocom package groups for the target"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = "\
    ${PN}-base \
    ${PN}-connectivity \
    ${PN}-debugging \
    ${PN}-libraries \
"

RDEPENDS:${PN}-base = "\
   udev \
   systemd-initramfs \
"

RDEPENDS:${PN}-connectivity = "\
   bridge-utils \
   linuxptp \
   netopeer2 \
   openssh \
   openssh-sshd \
   sysrepo \
"

RDEPENDS:${PN}-debugging= "\
   devmem2 \
"

RDEPENDS:${PN}-libraries = "\
   libnetconf2 \
   libyang \
"

SUMMARY = "Picocom package groups for the target"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = "\
    ${PN}-connectivity \
    ${PN}-libraries \
"

RDEPENDS:${PN}-connectivity = "\
   linuxptp \
   netopeer2 \
   openssh \
   openssh-sshd \
   sysrepo \
"

RDEPENDS:${PN}-libraries = "\
   libnetconf2 \
   libyang \
"

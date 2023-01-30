SUMMARY = "Picocom package groups for the target"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = "\
    ${PN}-connectivity \
    ${PN}-libraries \
"

RDEPENDS:${PN}-connectivity = "\
   linuxptp \
   openssh \
   openssh-sshd \
"

RDEPENDS:${PN}-libraries = "\
   libyang \
"

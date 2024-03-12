SUMMARY = "Linux PAM (Pluggable Authentication Modules for Linux) project"
DESCRIPTION = "Linux PAM (Pluggable Authentication Modules for Linux) project"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=7eb5c1bf854e8881005d673599ee74d3"

SRC_URI = "git://github.com/linux-pam/linux-pam.git;protocol=https;branch=master"

PV = "1.5.1+git${SRCPV}"
SRCREV = "225f17470eed9f44282f435ad1ed64c94d9a2ddf"

S = "${WORKDIR}/git"

DEPENDS = "libxcrypt bison-native"

inherit autotools gettext pkgconfig

# Package does not support out of tree builds.
B = "${S}"

do_configure() {
    ./autogen.sh

    ./configure --host=riscv32-poky-linux --enable-db=no --disable-doc --libdir=/usr/lib
}

FILES:${PN} += "/usr/lib/security/*"

do_install:append () {
    rm -r ${D}/usr/lib/systemd
}
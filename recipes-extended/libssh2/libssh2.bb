SUMMARY = "libssh2 is a client-side C library implementing the SSH2 protocol."
DESCRIPTION = "libssh2 is a client-side C library implementing the SSH2 protocol."
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=3e089ad0cf27edf1e7f261dfcd06acc7"

SRC_URI = "git://github.com/libssh2/libssh2.git;protocol=https;branch=master"

PV = "1.10.0+git${SRCPV}"
SRCREV = "635caa90787220ac3773c1d5ba11f1236c22eae8"

S = "${WORKDIR}/git"

DEPENDS = "openssl libgcrypt"

inherit autotools

# Package does not support out of tree builds.
B = "${S}"

do_configure() {
    autoreconf -fi
    ./configure --host=riscv32-poky-linux --libdir=/usr/lib
}

do_install:append () {
    rm -r ${D}/usr/local
}
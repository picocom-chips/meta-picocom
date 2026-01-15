SUMMARY = "The SSH library"
DESCRIPTION = "libssh is a multiplatform C library implementing the SSHv2 and SSHv1 protocol on client and server side."
SECTION = "libs"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=dabb4958b830e5df11d2b0ed8ea255a0"

HOMEPAGE = "https://www.libssh.org"

SRC_URI = "https://www.libssh.org/files/0.11/libssh-0.11.3.tar.xz"
SRC_URI[sha256sum] = "7d8a1361bb094ec3f511964e78a5a4dba689b5986e112afabe4f4d0d6c6125c3"

S = "${WORKDIR}/libssh-0.11.3"

DEPENDS = "openssl zlib"

inherit cmake

EXTRA_OECMAKE = " \
    -DUNIT_TESTING=OFF \
    -DCLIENT_TESTING=OFF \
    -DSERVER_TESTING=OFF \
"

PACKAGECONFIG ??= "libz gssapi"
PACKAGECONFIG[libz] = "-DWITH_ZLIB=ON,-DWITH_ZLIB=OFF,zlib"
PACKAGECONFIG[gssapi] = "-DWITH_GSSAPI=ON,-DWITH_GSSAPI=OFF,krb5"
PACKAGECONFIG[gcrypt] = "-DWITH_GCRYPT=ON,-DWITH_GCRYPT=OFF,libgcrypt"

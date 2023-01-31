SUMMARY = "libnetconf2 is a NETCONF library in C intended for building NETCONF clients and servers"
DESCRIPTION = "The library handles NETCONF authentication and all NETCONF RPC communication both server and client-side."
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=08a5578c9bab06fb2ae84284630b973f"

inherit cmake pkgconfig

SRCREV = "9b7f03e050f38866275772de62c0b7a305e71268"
SRC_URI = "git://github.com/CESNET/libnetconf2.git;protocol=https;branch=devel"

PV = "2.1.23+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "libssh openssl libyang libxcrypt"

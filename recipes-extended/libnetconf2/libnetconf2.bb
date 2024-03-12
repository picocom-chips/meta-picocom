SUMMARY = "libnetconf2 is a NETCONF library in C intended for building NETCONF clients and servers"
DESCRIPTION = "The library handles NETCONF authentication and all NETCONF RPC communication both server and client-side."
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=08a5578c9bab06fb2ae84284630b973f"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

inherit cmake pkgconfig

SRCREV = "1f50231323e9dd198cf7c120563897f42b8cae1d"
SRC_URI = "git://github.com/CESNET/libnetconf2.git;protocol=https;branch=devel \
    file://libnetconf2_support_recall_home.patch"

PV = "2.1.18+git${SRCPV}"

S = "${WORKDIR}/git"

DEPENDS = "libssh libpam openssl libyang libxcrypt"

FILES:${PN} += "/usr/share/yang/*"

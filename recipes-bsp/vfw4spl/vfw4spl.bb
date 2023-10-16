SUMMARY = "Firmware for pc805"
DESCRIPTION = "Firmware library for pc805 u-boot spl"
LICENSE = "CLOSED"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# inherit autotools-brokensep

BRANCH = "master"
SRCREV = "${AUTOREV}"
SRC_URI = " \
	file://pc805_vfw4spl.h \
	file://libpc805_vfw4spl.a \
	"

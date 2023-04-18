require recipes-kernel/linux/linux-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SUMMARY = "AndeSight 5.1.0 Linux 5.4 + Picocom"

SRCREV ="${AUTOREV}"
SRCBRANCH = "pz1_dev"

SRC_URI = "git://git@glhk.picocomtech.com/tools/linux.git;protocol=ssh;branch=${SRCBRANCH}"

COMPATIBLE_MACHINE = "(pc805|pc803)"

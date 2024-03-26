SUMMARY = "ISC DHCP."
DESCRIPTION = "ISC DHCP is enterprise grade, open source solution for DHCP servers, relay agents, and clients, supports both IPv4 and IPv6, and is suitable for use in high-volume and high-reliability applications."
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c463f4afde26d9eb60f14f50aeb85f8f"

SRC_URI = "git://github.com/isc-projects/dhcp.git;protocol=https;branch=master"

PV = "4.4.3+git${SRCPV}"
SRCREV = "33226f2d76b6b7a06df6b76abbb3526100f5ae2d"
S = "${WORKDIR}/git"

DEPENDS = "openssl libcap zlib"
RDEPENDS:${PN} += " bash "

inherit autotools

B = "${S}"

CFLAGS += "-D_GNU_SOURCE -fcommon"
LDFLAGS:append = " -pthread"
EXTRA_OECONF = "--host=riscv32-unknown-linux \
                --prefix=/usr \
                --enable-paranoia \
                --disable-shared \
                --enable-libtool \
                --with-randomdev=/dev/random \
               "

# Enable shared libs per dhcp README
do_configure:prepend () {
    cp configure.ac+lt configure.ac
}

do_install:append () {
    # Remove dhcpd and dhcrelay
    rm ${D}/usr/sbin/dhcpd
    rm ${D}/etc/dhcpd.conf.example
    rm ${D}/usr/sbin/dhcrelay

    # Add dhclient-script
    install -d ${D}/sbin
    install -d ${D}/var/db
    install -o root -g root ${S}/client/scripts/linux ${D}/sbin/dhclient-script
    sed -i 's/chown --reference=\/etc\/resolv.conf/chown root:root/g' ${D}/sbin/dhclient-script
    sed -i 's/chmod --reference=\/etc\/resolv.conf/chmod 0644/g' ${D}/sbin/dhclient-script
}

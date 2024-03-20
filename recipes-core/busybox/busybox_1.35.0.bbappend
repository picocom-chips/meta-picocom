FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = "file://fragment.cfg \
    file://ntp.conf \
    file://ntpd"

# install ntp conf script
do_install:append () {
    install -m 644 ${WORKDIR}/ntp.conf ${D}/etc/ntp.conf
    install -o root -g root ${WORKDIR}/ntpd ${D}/etc/init.d/ntpd
    install -d ${D}/etc/rcS.d
    ln -s -r ${D}/etc/init.d/ntpd ${D}/etc/rcS.d/S40ntpd.sh
}

# Override the package version (PV)
PV = "0.9.6"

# Override the source URI (SRC_URI) to fetch the new version's tarball
# Make sure this URL is correct for the specific version you want
SRC_URI = "https://www.libssh.org/files/0.9/libssh-0.9.6.tar.xz"

# !!! CRITICAL: Update the MD5 and SHA256 checksums for libssh-0.11.1.tar.xz !!!
# You MUST get the correct checksums from libssh.org/download or by
# downloading the file and running 'md5sum' and 'sha256sum' on it yourself.
SRC_URI[md5sum] = "0174df377361221a31a9576afbaba330" 
SRC_URI[sha256sum] = "86bcf885bd9b80466fe0e05453c58b877df61afa8ba947a58c356d7f0fab829b" 

LIC_FILES_CHKSUM = "file://COPYING;md5=dabb4958b830e5df11d2b0ed8ea255a0;sha256=1656186e951db1c010a8485481fa94587f7e53a26d24976bef97945ad0c4df5a"

S = "${WORKDIR}/${PN}-${PV}"
PACKAGECONFIG:remove = "gcrypt"
inherit image
inherit extrausers

IMAGE_FEATURES += "debug-tweaks"

EXTRA_IMAGE_FEATURES += "allow-root-login allow-empty-password empty-root-password"

IMAGE_LINGUAS = ""

INITRAMFS_IMAGE_BUNDLE = "1"

NO_RECOMMENDATIONS = "1"

PACKAGE_INSTALL += " \
    packagegroup-picocom-base \
    packagegroup-picocom-connectivity \
    packagegroup-picocom-debugging \
    packagegroup-picocom-libraries \
"

EXTRA_USERS_PARAMS = " \
    usermod -p '' root; \
"

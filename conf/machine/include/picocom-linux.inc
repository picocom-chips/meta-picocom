KERNEL_CLASSES = "kernel-fitimage"
KERNEL_IMAGETYPE = "Image"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-picocom"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-picocom"

SERIAL_CONSOLES = "115200;ttyS0"

IMAGE_FSTYPES ?= "wic.gz wic.bmap ext4"

# U-Boot ITB (FIT image with U-Boot S-mode, OpenSBI FW_DYNAMIC, DTB [from
# U-Boot source code tree])
UBOOT_SUFFIX ?= "itb"

UBOOT_ENTRYPOINT = "0x600000"
UBOOT_DTB_LOADADDRESS = "0x20000000"

## wic default support
WKS_FILE_DEPENDS ?= " \
    u-boot-picocom \
    opensbi-picocom \
    e2fsprogs-native \
    bmap-tools-native \
"

IMAGE_BOOT_FILES ?= " \
    ${KERNEL_IMAGETYPE} \
    ${RISCV_SBI_FDT} \
    boot.scr.uimg \
    uEnv.txt \
    u-boot.itb \
    u-boot-spl.bin \
"

##TODO
WKS_FILE ?= "ae350-ax45mp-spl.wks"


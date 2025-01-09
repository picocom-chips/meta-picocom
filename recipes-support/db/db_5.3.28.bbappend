# many configure tests are failing with gcc-14
CFLAGS += "-Wno-error=implicit-int -Wno-error=implicit-function-declaration"
BUILD_CFLAGS += "-Wno-error=implicit-int -Wno-error=implicit-function-declaration"
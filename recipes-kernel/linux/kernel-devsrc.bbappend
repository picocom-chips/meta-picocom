do_install() {
    kerneldir=${D}${KERNEL_BUILD_ROOT}${KERNEL_VERSION}
    install -d $kerneldir

    # create the directory structure
    rm -f $kerneldir/build
    rm -f $kerneldir/source
    mkdir -p $kerneldir/build

    # for compatibility with some older variants of this package, we
    # create  a /usr/src/kernel symlink to /lib/modules/<version>/source
    mkdir -p ${D}/usr/src
    (
	cd ${D}/usr/src
	ln -rs ${D}${KERNEL_BUILD_ROOT}${KERNEL_VERSION}/source kernel
    )

    # for on target purposes, we unify build and source
    (
	cd $kerneldir
	ln -s build source
    )

    # first copy everything
    (
	cd ${S}
	cp --parents $(find  -type f -name "Makefile*" -o -name "Kconfig*") $kerneldir/build
	cp --parents $(find  -type f -name "Build" -o -name "Build.include") $kerneldir/build
    )

    # then drop all but the needed Makefiles/Kconfig files
    rm -rf $kerneldir/build/scripts
    rm -rf $kerneldir/build/include

    # now copy in parts from the build that we'll need later
    (
	cd ${B}

	if [ -s Module.symvers ]; then
	    cp Module.symvers $kerneldir/build
	fi
	cp System.map* $kerneldir/build
	if [ -s Module.markers ]; then
	    cp Module.markers $kerneldir/build
	fi

	cp -a .config $kerneldir/build

	# This scripts copy blow up QA, so for now, we require a more
	# complex 'make scripts' to restore these, versus copying them
	# here. Left as a reference to indicate that we know the scripts must
	# be dealt with.
	# cp -a scripts $kerneldir/build

	# although module.lds can be regenerated on target via 'make modules_prepare'
	# there are several places where 'makes scripts prepare' is done, and that won't
	# regenerate the file. So we copy it onto the target as a migration to using
	# modules_prepare
	cp -a --parents scripts/module.lds $kerneldir/build/ 2>/dev/null || :

        if [ -d arch/${ARCH}/scripts ]; then
	    cp -a arch/${ARCH}/scripts $kerneldir/build/arch/${ARCH}
	fi
	if [ -f arch/${ARCH}/*lds ]; then
	    cp -a arch/${ARCH}/*lds $kerneldir/build/arch/${ARCH}
	fi

	rm -f $kerneldir/build/scripts/*.o
	rm -f $kerneldir/build/scripts/*/*.o

	if [ "${ARCH}" = "arm64" -o "${ARCH}" = "riscv" ]; then
            if [ -e arch/${ARCH}/kernel/vdso/vdso.lds ]; then
	        cp -a --parents arch/${ARCH}/kernel/vdso/vdso.lds $kerneldir/build/
            fi
	fi

	cp -a include $kerneldir/build/include

	# we don't usually copy generated files, since they can be rebuilt on the target,
	# but without this file, we get a forced syncconfig run in v5.8+, which prompts and
	# breaks workflows.
	cp -a --parents include/generated/autoconf.h $kerneldir/build 2>/dev/null || :

	if [ -e $kerneldir/include/generated/.vdso-offsets.h.cmd ] ||
	     [ -e $kerneldir/build/include/generated/.vdso-offsets.h.cmd ] ||
	     [ -e $kerneldir/build/include/generated/.vdso32-offsets.h.cmd ] ; then
	    rm -f $kerneldir/include/generated/.vdso-offsets.h.cmd
	    rm -f $kerneldir/build/include/generated/.vdso-offsets.h.cmd
	    rm -f $kerneldir/build/include/generated/.vdso32-offsets.h.cmd
	fi
    )

    # now grab the chunks from the source tree that we need
    (
	cd ${S}

	cp -a scripts $kerneldir/build

	# if our build dir had objtool, it will also be rebuilt on target, so
	# we copy what is required for that build
	if [ -f ${B}/tools/objtool/objtool ]; then
	    # these are a few files associated with objtool, since we'll need to
	    # rebuild it
	    cp -a --parents tools/build/Build.include $kerneldir/build/
	    cp -a --parents tools/build/Build $kerneldir/build/
	    cp -a --parents tools/build/fixdep.c $kerneldir/build/
	    cp -a --parents tools/scripts/utilities.mak $kerneldir/build/

	    # extra files, just in case
	    cp -a --parents tools/objtool/* $kerneldir/build/
	    cp -a --parents tools/lib/* $kerneldir/build/
	    cp -a --parents tools/lib/subcmd/* $kerneldir/build/

	    cp -a --parents tools/include/* $kerneldir/build/

	    cp -a --parents $(find tools/arch/${ARCH}/ -type f) $kerneldir/build/
	fi

	if [ "${ARCH}" = "riscv" ]; then
            cp -a --parents arch/riscv/kernel/vdso/*gettimeofday.* $kerneldir/build/
            if [ -e arch/riscv/kernel/vdso/note.S ]; then
                cp -a --parents arch/riscv/kernel/vdso/note.S $kerneldir/build/
            fi
            if [ -e arch/riscv/kernel/vdso/gen_vdso_offsets.sh ]; then
                    cp -a --parents arch/riscv/kernel/vdso/gen_vdso_offsets.sh $kerneldir/build/
            fi
            if [ -e arch/riscv/kernel/*lds ]; then
                cp -a --parents arch/riscv/kernel/*lds $kerneldir/build/
            fi
           cp -a --parents arch/riscv/kernel/vdso/* $kerneldir/build/ 2>/dev/null || :
	fi

	if [ -d arch/${ARCH}/include ]; then
	    cp -a --parents arch/${ARCH}/include $kerneldir/build/
	fi

	cp -a include $kerneldir/build

	cp -a --parents lib/vdso/* $kerneldir/build/ 2>/dev/null || :

	cp -a --parents tools/include/tools/le_byteshift.h $kerneldir/build/
	cp -a --parents tools/include/tools/be_byteshift.h $kerneldir/build/

	# required for generate missing syscalls prepare phase
	cp -a --parents $(find arch/x86 -type f -name "syscall_32.tbl") $kerneldir/build
	cp -a --parents $(find arch/arm -type f -name "*.tbl") $kerneldir/build 2>/dev/null || :

    # required to build scripts/selinux/genheaders/genheaders
    cp -a --parents security/selinux/include/* $kerneldir/build/

	# copy any localversion files
	cp -a localversion* $kerneldir/build/ 2>/dev/null || :
    )

    # Make sure the Makefile and version.h have a matching timestamp so that
    # external modules can be built
    touch -r $kerneldir/build/Makefile $kerneldir/build/include/generated/uapi/linux/version.h

    # Copy .config to include/config/auto.conf so "make prepare" is unnecessary.
    cp $kerneldir/build/.config $kerneldir/build/include/config/auto.conf

    # make sure these are at least as old as the .config, or rebuilds will trigger
    touch -r $kerneldir/build/.config $kerneldir/build/include/generated/autoconf.h 2>/dev/null || :
    touch -r $kerneldir/build/.config $kerneldir/build/include/config/auto.conf* 2>/dev/null || :

    if [ -e "$kerneldir/build/include/config/auto.conf.cmd" ]; then
        sed -i 's/ifneq "$(CC)" ".*-linux-.*gcc.*$/ifneq "$(CC)" "gcc"/' "$kerneldir/build/include/config/auto.conf.cmd"
        sed -i 's/ifneq "$(LD)" ".*-linux-.*ld.bfd.*$/ifneq "$(LD)" "ld"/' "$kerneldir/build/include/config/auto.conf.cmd"
        sed -i 's/ifneq "$(AR)" ".*-linux-.*ar.*$/ifneq "$(AR)" "ar"/' "$kerneldir/build/include/config/auto.conf.cmd"
        sed -i 's/ifneq "$(OBJCOPY)" ".*-linux-.*objcopy.*$/ifneq "$(OBJCOPY)" "objcopy"/' "$kerneldir/build/include/config/auto.conf.cmd"
        if [ "${ARCH}" = "powerpc" ]; then
            sed -i 's/ifneq "$(NM)" ".*-linux-.*nm.*$/ifneq "$(NM)" "nm --synthetic"/' "$kerneldir/build/include/config/auto.conf.cmd"
        else
            sed -i 's/ifneq "$(NM)" ".*-linux-.*nm.*$/ifneq "$(NM)" "nm"/' "$kerneldir/build/include/config/auto.conf.cmd"
        fi
        sed -i 's/ifneq "$(HOSTCXX)" ".*$/ifneq "$(HOSTCXX)" "g++"/' "$kerneldir/build/include/config/auto.conf.cmd"
        sed -i 's/ifneq "$(HOSTCC)" ".*$/ifneq "$(HOSTCC)" "gcc"/' "$kerneldir/build/include/config/auto.conf.cmd"
        sed -i 's/ifneq "$(CC_VERSION_TEXT)".*\(gcc.*\)"/ifneq "$(CC_VERSION_TEXT)" "\1"/' "$kerneldir/build/include/config/auto.conf.cmd"
        sed -i 's/ifneq "$(srctree)" ".*"/ifneq "$(srctree)" "."/' "$kerneldir/build/include/config/auto.conf.cmd"
        # we don't build against the defconfig, so make sure it isn't the trigger for syncconfig
        sed -i 's/ifneq "$(KBUILD_DEFCONFIG)".*"\(.*\)"/ifneq "\1" "\1"/' "$kerneldir/build/include/config/auto.conf.cmd"
    fi

    # make the scripts python3 safe. We won't be running these, and if they are
    # left as /usr/bin/python rootfs assembly will fail, since we only have python3
    # in the RDEPENDS (and the python3 package does not include /usr/bin/python)
    for ss in $(find $kerneldir/build/scripts -type f -name '*'); do
	sed -i 's,/usr/bin/python2,/usr/bin/env python3,' "$ss"
	sed -i 's,/usr/bin/env python2,/usr/bin/env python3,' "$ss"
	sed -i 's,/usr/bin/python,/usr/bin/env python3,' "$ss"
    done

    chown -R root:root ${D}
}

COMPATIBLE_MACHINE = "(pc805)"

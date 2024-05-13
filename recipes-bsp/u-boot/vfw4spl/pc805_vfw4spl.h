#ifndef __PC805_VFW4SPL_H__
#define __PC805_VFW4SPL_H__
#include <stdint.h>
typedef enum
{
    DDR_RANK_128MBYTE = 1,
    DDR_RANK_256MBYTE,
    DDR_RANK_384MBYTE,
    DDR_RANK_512MBYTE,
    DDR_RANK_768MBYTE,
    DDR_RANK_1024MBYTE,
    DDR_RANK_1536MBYTE,
    DDR_RANK_2048MBYTE,
} pc805_ddr_rank_size_t;

extern void vfw_cpu_crg_init();
extern void vfw_sys_crg_init();
extern void vfw_init_ddr(pc805_ddr_rank_size_t rank_size, bool inline_ecc);
extern void vfw_ddr_mr_write(uintptr_t reg, uint32_t value);
#endif
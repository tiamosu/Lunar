package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil


/**
 * 描述：流月
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class LiuYue(
    private var liuNian: LiuNian,

    /**
     * 序数，0-9
     */
    private var index: Int
) {

    fun getIndex(): Int {
        return index
    }

    /**
     * 获取中文的月
     *
     * @return 中文月，如正
     */
    fun getMonthInChinese(): String {
        return LunarUtil.MONTH[index + 1]
    }

    /**
     * 获取干支
     *
     *
     * 《五虎遁》
     * 甲己之年丙作首，
     * 乙庚之年戊为头，
     * 丙辛之年寻庚上，
     * 丁壬壬寅顺水流，
     * 若问戊癸何处走，
     * 甲寅之上好追求。
     *
     * @return 干支
     */
    fun getGanZhi(): String {
        var offset = 0
        val yearGan = liuNian.getGanZhi().substring(0, 1)
        if ("甲" == yearGan || "己" == yearGan) {
            offset = 2
        } else if ("乙" == yearGan || "庚" == yearGan) {
            offset = 4
        } else if ("丙" == yearGan || "辛" == yearGan) {
            offset = 6
        } else if ("丁" == yearGan || "壬" == yearGan) {
            offset = 8
        }
        val gan = LunarUtil.GAN[(index + offset) % 10 + 1]
        val zhi = LunarUtil.ZHI[(index + LunarUtil.BASE_MONTH_ZHI_INDEX) % 12 + 1]
        return gan + zhi
    }

    /**
     * 获取所在旬
     * @return 旬
     */
    fun getXun(): String {
        return LunarUtil.getXun(getGanZhi())
    }

    /**
     * 获取旬空(空亡)
     * @return 旬空(空亡)
     */
    fun getXunKong(): String {
        return LunarUtil.getXunKong(getGanZhi())
    }
}
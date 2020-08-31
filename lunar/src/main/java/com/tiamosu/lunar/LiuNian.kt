package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil
import com.tiamosu.lunar.utils.LunarUtil.getJiaZiIndex

/**
 * 描述：流年
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class LiuNian(
    /**
     * 大运
     */
    private var daYun: DaYun,

    /**
     * 序数，0-9
     */
    private var index: Int
) {

    /**
     * 年
     */
    private var year = 0

    /**
     * 年龄
     */
    private var age = 0

    private var lunar = daYun.getLunar()

    init {
        year = daYun.getStartYear() + index
        age = daYun.getStartAge() + index
    }

    fun getIndex(): Int {
        return index
    }

    fun getYear(): Int {
        return year
    }

    fun getAge(): Int {
        return age
    }

    /**
     * 获取干支
     *
     * @return 干支
     */
    fun getGanZhi(): String {
        var offset = getJiaZiIndex(lunar.getYearInGanZhiExact()) + index
        if (daYun.getIndex() > 0) {
            offset += daYun.getStartAge() - 1
        }
        offset %= LunarUtil.JIA_ZI.size
        return LunarUtil.JIA_ZI[offset]
    }

    fun getLiuYue(): Array<LiuYue?> {
        val n = 12
        val l = arrayOfNulls<LiuYue>(n)
        for (i in 0 until n) {
            l[i] = LiuYue(this, i)
        }
        return l
    }
}
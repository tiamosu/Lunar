package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil
import com.tiamosu.lunar.utils.LunarUtil.getJiaZiIndex

/**
 * 描述：大运
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class DaYun(
    private var yun: Yun,

    /**
     * 序数，0-9
     */
    private var index: Int
) {

    /**
     * 开始年(含)
     */
    private var startYear = 0

    /**
     * 结束年(含)
     */
    private var endYear = 0

    /**
     * 开始年龄(含)
     */
    private var startAge = 0

    /**
     * 结束年龄(含)
     */
    private var endAge = 0

    /**
     * 运
     */
    private var lunar = yun.getLunar()

    init {
        val year = yun.getStartSolar().getYear()
        if (index < 1) {
            startYear = lunar.getSolar().getYear()
            startAge = 1
            endYear = year - 1
            endAge = yun.getStartYear()
        } else {
            val add = (index - 1) * 10
            startYear = year + add
            startAge = yun.getStartYear() + add + 1
            endYear = startYear + 9
            endAge = startAge + 9
        }
    }

    fun getStartYear(): Int {
        return startYear
    }

    fun getEndYear(): Int {
        return endYear
    }

    fun getStartAge(): Int {
        return startAge
    }

    fun getEndAge(): Int {
        return endAge
    }

    fun getIndex(): Int {
        return index
    }

    fun getLunar(): Lunar {
        return lunar
    }

    /**
     * 获取干支
     *
     * @return 干支
     */
    fun getGanZhi(): String {
        if (index < 1) {
            return ""
        }
        var offset = getJiaZiIndex(lunar.getMonthInGanZhiExact())
        offset += if (yun.isForward()) index else -index
        val size = LunarUtil.JIA_ZI.size
        if (offset >= size) {
            offset -= size
        }
        if (offset < 0) {
            offset += size
        }
        return LunarUtil.JIA_ZI[offset]
    }

    /**
     * 获取流年
     *
     * @return 流年
     */
    fun getLiuNian(): Array<LiuNian?> {
        var n = 10
        if (index < 1) {
            n = endYear - startYear + 1
        }
        val l = arrayOfNulls<LiuNian>(n)
        for (i in 0 until n) {
            l[i] = LiuNian(this, i)
        }
        return l
    }

    /**
     * 获取小运
     *
     * @return 小运
     */
    fun getXiaoYun(): Array<XiaoYun?> {
        var n = 10
        if (index < 1) {
            n = endYear - startYear + 1
        }
        val l = arrayOfNulls<XiaoYun>(n)
        for (i in 0 until n) {
            l[i] = XiaoYun(this, i, yun.isForward())
        }
        return l
    }
}
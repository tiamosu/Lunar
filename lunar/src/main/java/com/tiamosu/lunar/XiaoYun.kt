package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil
import com.tiamosu.lunar.utils.LunarUtil.getJiaZiIndex

/**
 * 描述：小运
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class XiaoYun(
    /**
     * 大运
     */
    private var daYun: DaYun,

    /**
     * 序数，0-9
     */
    private var index: Int,
    /**
     * 是否顺推
     */
    private var forward: Boolean
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
        var offset = getJiaZiIndex(lunar.getTimeInGanZhi())
        var add = index + 1
        if (daYun.getIndex() > 0) {
            add += daYun.getStartAge() - 1
        }
        offset += if (forward) add else -add
        val size = LunarUtil.JIA_ZI.size
        while (offset < 0) {
            offset += size
        }
        offset %= size
        return LunarUtil.JIA_ZI[offset]
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
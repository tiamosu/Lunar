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
        // 干支与出生日期和起运日期都没关系
        var offset = getJiaZiIndex(
            (lunar.getJieQiTable()["立春"] ?: Solar()).getLunar().getYearInGanZhiExact()
        ) + index
        if (daYun.getIndex() > 0) {
            offset += daYun.getStartAge() - 1
        }
        offset %= LunarUtil.JIA_ZI.size
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

    /**
     * 获取流月
     * @return 流月
     */
    fun getLiuYue(): Array<LiuYue> {
        val list = mutableListOf<LiuYue>()
        for (i in 0 until 12) {
            list.add(LiuYue(this, i))
        }
        return list.toTypedArray()
    }
}
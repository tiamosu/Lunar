package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil
import com.tiamosu.lunar.utils.LunarUtil.getJiaZiIndex

/**
 * 描述：八字
 *
 * @author tiamosu
 * @date 2020/7/29.
 */
class EightChar(private var lunar: Lunar) {
    /** 月支，按正月起寅排列  */
    private val MONTH_ZHI = arrayOf("", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑")

    /** 长生十二神  */
    val CHANG_SHENG = arrayOf("长生", "沐浴", "冠带", "临官", "帝旺", "衰", "病", "死", "墓", "绝", "胎", "养")

    /** 长生十二神日干偏移值，五阳干顺推，五阴干逆推  */
    private val CHANG_SHENG_OFFSET by lazy {
        hashMapOf(
            //阳
            "甲" to 11,
            "丙" to 2,
            "戊" to 2,
            "庚" to 5,
            "壬" to 8,
            //阴
            "乙" to 6,
            "丁" to 9,
            "己" to 9,
            "辛" to 0,
            "癸" to 3,
        )
    }

    override fun toString(): String {
        return getYear() + " " + getMonth() + " " + getDay() + " " + getTime()
    }

    /**
     * 获取年柱
     * @return 年柱
     */
    fun getYear(): String {
        return lunar.getYearInGanZhiExact()
    }

    /**
     * 获取年干
     * @return 天干
     */
    fun getYearGan(): String {
        return lunar.getYearGanExact()
    }

    /**
     * 获取年支
     * @return 地支
     */
    fun getYearZhi(): String {
        return lunar.getYearZhiExact()
    }

    /**
     * 获取年柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 天干
     */
    fun getYearHideGan(): List<String> {
        return LunarUtil.ZHI_HIDE_GAN[getYearZhi()] ?: emptyList()
    }

    /**
     * 获取年柱五行
     * @return 五行
     */
    fun getYearWuXing(): String {
        return LunarUtil.WU_XING_GAN[lunar.getYearGanExact()] + LunarUtil.WU_XING_ZHI[lunar.getYearZhiExact()]
    }

    /**
     * 获取年柱纳音
     * @return 纳音
     */
    fun getYearNaYin(): String {
        return LunarUtil.NAYIN[getYear()] ?: ""
    }

    /**
     * 获取年柱天干十神
     * @return 十神
     */
    fun getYearShiShenGan(): String {
        return LunarUtil.SHI_SHEN_GAN[getDayGan() + getYearGan()] ?: ""
    }

    private fun getShiShenZhi(zhi: String): List<String> {
        val hideGan = LunarUtil.ZHI_HIDE_GAN[zhi] ?: emptyList()
        val l: MutableList<String> = ArrayList(hideGan.size)
        for (gan in hideGan) {
            l.add(LunarUtil.SHI_SHEN_ZHI[getDayGan() + zhi + gan] ?: "")
        }
        return l
    }

    /**
     * 获取年柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 十神
     */
    fun getYearShiShenZhi(): List<String> {
        return getShiShenZhi(getYearZhi())
    }

    private fun getDiShi(zhiIndex: Int): String {
        val offset = CHANG_SHENG_OFFSET[getDayGan()] ?: 0
        var index = offset + if (lunar.getDayGanIndexExact() % 2 == 0) zhiIndex else -zhiIndex
        if (index >= 12) {
            index -= 12
        }
        if (index < 0) {
            index += 12
        }
        return CHANG_SHENG[index]
    }

    /**
     * 获取年柱地势（长生十二神）
     * @return 地势
     */
    fun getYearDiShi(): String {
        return getDiShi(lunar.getYearZhiIndexExact())
    }

    /**
     * 获取月柱
     * @return 月柱
     */
    fun getMonth(): String {
        return lunar.getMonthInGanZhiExact()
    }

    /**
     * 获取月干
     * @return 天干
     */
    fun getMonthGan(): String {
        return lunar.getMonthGanExact()
    }

    /**
     * 获取月支
     * @return 地支
     */
    fun getMonthZhi(): String {
        return lunar.getMonthZhiExact()
    }

    /**
     * 获取月柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 天干
     */
    fun getMonthHideGan(): List<String> {
        return LunarUtil.ZHI_HIDE_GAN[getMonthZhi()] ?: emptyList()
    }

    /**
     * 获取月柱五行
     * @return 五行
     */
    fun getMonthWuXing(): String {
        return LunarUtil.WU_XING_GAN[lunar.getMonthGanExact()] + LunarUtil.WU_XING_ZHI[lunar.getMonthZhiExact()]
    }

    /**
     * 获取月柱纳音
     * @return 纳音
     */
    fun getMonthNaYin(): String {
        return LunarUtil.NAYIN[getMonth()] ?: ""
    }

    /**
     * 获取月柱天干十神
     * @return 十神
     */
    fun getMonthShiShenGan(): String {
        return LunarUtil.SHI_SHEN_GAN[getDayGan() + getMonthGan()] ?: ""
    }

    /**
     * 获取月柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 十神
     */
    fun getMonthShiShenZhi(): List<String> {
        return getShiShenZhi(getMonthZhi())
    }

    /**
     * 获取月柱地势（长生十二神）
     * @return 地势
     */
    fun getMonthDiShi(): String {
        return getDiShi(lunar.getMonthZhiIndexExact())
    }

    /**
     * 获取日柱
     * @return 日柱
     */
    fun getDay(): String {
        return lunar.getDayInGanZhiExact()
    }

    /**
     * 获取日干
     * @return 天干
     */
    fun getDayGan(): String {
        return lunar.getDayGanExact()
    }

    /**
     * 获取日支
     * @return 地支
     */
    fun getDayZhi(): String {
        return lunar.getDayZhiExact()
    }

    /**
     * 获取日柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 天干
     */
    fun getDayHideGan(): List<String> {
        return LunarUtil.ZHI_HIDE_GAN[getDayZhi()] ?: emptyList()
    }

    /**
     * 获取日柱五行
     * @return 五行
     */
    fun getDayWuXing(): String {
        return LunarUtil.WU_XING_GAN[lunar.getDayGanExact()] + LunarUtil.WU_XING_ZHI[lunar.getDayZhiExact()]
    }

    /**
     * 获取日柱纳音
     * @return 纳音
     */
    fun getDayNaYin(): String {
        return LunarUtil.NAYIN[getDay()] ?: ""
    }

    /**
     * 获取日柱天干十神，也称日元、日干
     * @return 十神
     */
    fun getDayShiShenGan(): String {
        return "日主"
    }

    /**
     * 获取日柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 十神
     */
    fun getDayShiShenZhi(): List<String> {
        return getShiShenZhi(getDayZhi())
    }

    /**
     * 获取日柱地势（长生十二神）
     * @return 地势
     */
    fun getDayDiShi(): String {
        return getDiShi(lunar.getDayZhiIndexExact())
    }

    /**
     * 获取时柱
     * @return 时柱
     */
    fun getTime(): String {
        return lunar.getTimeInGanZhi()
    }

    /**
     * 获取时干
     * @return 天干
     */
    fun getTimeGan(): String {
        return lunar.getTimeGan()
    }

    /**
     * 获取时支
     * @return 地支
     */
    fun getTimeZhi(): String {
        return lunar.getTimeZhi()
    }

    /**
     * 获取时柱地支藏干，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 天干
     */
    fun getTimeHideGan(): List<String> {
        return LunarUtil.ZHI_HIDE_GAN[getTimeZhi()] ?: emptyList()
    }

    /**
     * 获取时柱五行
     * @return 五行
     */
    fun getTimeWuXing(): String {
        return LunarUtil.WU_XING_GAN[lunar.getTimeGan()] + LunarUtil.WU_XING_ZHI[lunar.getTimeZhi()]
    }

    /**
     * 获取时柱纳音
     * @return 纳音
     */
    fun getTimeNaYin(): String {
        return LunarUtil.NAYIN[getTime()] ?: ""
    }

    /**
     * 获取时柱天干十神
     * @return 十神
     */
    fun getTimeShiShenGan(): String {
        return LunarUtil.SHI_SHEN_GAN[getDayGan() + getTimeGan()] ?: ""
    }

    /**
     * 获取时柱地支十神，由于藏干分主气、余气、杂气，所以返回结果可能为1到3个元素
     * @return 十神
     */
    fun getTimeShiShenZhi(): List<String> {
        return getShiShenZhi(getTimeZhi())
    }

    /**
     * 获取时柱地势（长生十二神）
     * @return 地势
     */
    fun getTimeDiShi(): String {
        return getDiShi(lunar.getTimeZhiIndex())
    }

    /**
     * 获取胎元
     * @return 胎元
     */
    fun getTaiYuan(): String {
        var ganIndex = lunar.getMonthGanIndexExact() + 1
        if (ganIndex >= 10) {
            ganIndex -= 10
        }
        var zhiIndex = lunar.getMonthZhiIndexExact() + 3
        if (zhiIndex >= 12) {
            zhiIndex -= 12
        }
        return LunarUtil.GAN[ganIndex + 1] + LunarUtil.ZHI[zhiIndex + 1]
    }

    /**
     * 获取胎元纳音
     * @return 纳音
     */
    fun getTaiYuanNaYin(): String {
        return LunarUtil.NAYIN[getTaiYuan()] ?: ""
    }

    /**
     * 获取命宫
     * @return 命宫
     */
    fun getMingGong(): String {
        var monthZhiIndex = 0
        var timeZhiIndex = 0
        var i = 0
        val j = MONTH_ZHI.size
        while (i < j) {
            val zhi = MONTH_ZHI[i]
            if (lunar.getMonthZhiExact() == zhi) {
                monthZhiIndex = i
            }
            if (lunar.getTimeZhi() == zhi) {
                timeZhiIndex = i
            }
            i++
        }
        var zhiIndex = 26 - (monthZhiIndex + timeZhiIndex)
        if (zhiIndex > 12) {
            zhiIndex -= 12
        }
        var jiaZiIndex = getJiaZiIndex(lunar.getMonthInGanZhiExact()) - (monthZhiIndex - zhiIndex)
        if (jiaZiIndex >= 60) {
            jiaZiIndex -= 60
        }
        if (jiaZiIndex < 0) {
            jiaZiIndex += 60
        }
        return LunarUtil.JIA_ZI[jiaZiIndex]
    }

    /**
     * 获取命宫纳音
     * @return 纳音
     */
    fun getMingGongNaYin(): String {
        return LunarUtil.NAYIN[getMingGong()] ?: ""
    }

    /**
     * 获取身宫
     * @return 身宫
     */
    fun getShenGong(): String {
        var monthZhiIndex = 0
        var timeZhiIndex = 0
        var i = 0
        val j = MONTH_ZHI.size
        while (i < j) {
            val zhi = MONTH_ZHI[i]
            if (lunar.getMonthZhiExact() == zhi) {
                monthZhiIndex = i
            }
            if (lunar.getTimeZhi() == zhi) {
                timeZhiIndex = i
            }
            i++
        }
        val zhiIndex = (2 + (monthZhiIndex + timeZhiIndex)) % 12
        var jiaZiIndex = getJiaZiIndex(lunar.getMonthInGanZhiExact()) - (monthZhiIndex - zhiIndex)
        if (jiaZiIndex >= 60) {
            jiaZiIndex -= 60
        }
        if (jiaZiIndex < 0) {
            jiaZiIndex += 60
        }
        return LunarUtil.JIA_ZI[jiaZiIndex]
    }

    /**
     * 获取身宫纳音
     * @return 纳音
     */
    fun getShenGongNaYin(): String {
        return LunarUtil.NAYIN[getShenGong()] ?: ""
    }

    fun getLunar(): Lunar {
        return lunar
    }

    /**
     * 获取运
     *
     * @param gender 性别：1男，0女
     * @return 运
     */
    fun getYun(gender: Int): Yun {
        return Yun(this, gender)
    }
}
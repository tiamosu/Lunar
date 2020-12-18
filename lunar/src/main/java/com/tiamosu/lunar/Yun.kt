package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil.getTimeZhiIndex
import java.util.*

/**
 * 描述：运
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class Yun// 阳
// 男
    (
    eightChar: EightChar,
    /**
     * 性别(1男，0女)
     */
    private var gender: Int
) {

    /**
     * 起运年数
     */
    private var startYear = 0

    /**
     * 起运月数
     */
    private var startMonth = 0

    /**
     * 起运天数
     */
    private var startDay = 0

    /**
     * 是否顺推
     */
    private var forward = false

    private var lunar = eightChar.getLunar()

    init {
        val yang = 0 == lunar.getYearGanIndexExact() % 2
        val man = 1 == gender
        forward = yang && man || !yang && !man
        computeStart()
    }

    /**
     * 起运计算
     */
    private fun computeStart() {
        // 上节
        val prev = lunar.getPrevJie()
        // 下节
        val next = lunar.getNextJie()
        // 出生日期
        val current = lunar.getSolar()
        // 阳男阴女顺推，阴男阳女逆推
        val start = (if (forward) current else prev?.getSolar()) ?: return
        val end = (if (forward) next?.getSolar() else current) ?: return
        // 时辰差
        var hourDiff = getTimeZhiIndex(end.toYmdHms().substring(11, 16)) - getTimeZhiIndex(
            start.toYmdHms().substring(11, 16)
        )
        val endCalendar: Calendar = Calendar.getInstance()
        endCalendar.set(end.getYear(), end.getMonth() - 1, end.getDay(), 0, 0, 0)
        endCalendar.set(Calendar.MILLISECOND, 0)
        val startCalendar: Calendar = Calendar.getInstance()
        startCalendar.set(start.getYear(), start.getMonth() - 1, start.getDay(), 0, 0, 0)
        startCalendar.set(Calendar.MILLISECOND, 0)
        // 天数差
        var dayDiff =
            ((endCalendar.timeInMillis - startCalendar.timeInMillis) / (1000 * 3600 * 24)).toInt()
        if (hourDiff < 0) {
            hourDiff += 12
            dayDiff--
        }
        val monthDiff = hourDiff * 10 / 30
        var month = dayDiff * 4 + monthDiff
        val day = hourDiff * 10 - monthDiff * 30
        val year = month / 12
        month -= year * 12
        startYear = year
        startMonth = month
        startDay = day
    }

    /**
     * 获取性别
     *
     * @return 性别(1男 ， 0女)
     */
    fun getGender(): Int {
        return gender
    }

    /**
     * 获取起运年数
     *
     * @return 起运年数
     */
    fun getStartYear(): Int {
        return startYear
    }

    /**
     * 获取起运月数
     *
     * @return 起运月数
     */
    fun getStartMonth(): Int {
        return startMonth
    }

    /**
     * 获取起运天数
     *
     * @return 起运天数
     */
    fun getStartDay(): Int {
        return startDay
    }

    /**
     * 是否顺推
     *
     * @return true/false
     */
    fun isForward(): Boolean {
        return forward
    }

    fun getLunar(): Lunar {
        return lunar
    }

    /**
     * 获取起运的阳历日期
     *
     * @return 阳历日期
     */
    fun getStartSolar(): Solar {
        val birth = lunar.getSolar()
        val c = Calendar.getInstance()
        c.set(
            birth.getYear() + startYear,
            birth.getMonth() - 1 + startMonth,
            birth.getDay() + startDay,
            0,
            0,
            0
        )
        return Solar.fromCalendar(c)
    }

    /**
     * 获取大运
     *
     * @return 大运
     */
    fun getDaYun(): Array<DaYun?> {
        val n = 10
        val l = arrayOfNulls<DaYun>(n)
        for (i in 0 until n) {
            l[i] = DaYun(this, i)
        }
        return l
    }
}
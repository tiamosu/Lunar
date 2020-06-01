package com.tiamosu.lunar

import com.tiamosu.lunar.utils.SolarUtil
import java.util.*
import kotlin.collections.ArrayList

/**
 * 描述：阳历月
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class SolarMonth {
    /** 年  */
    private var year = 0

    /** 月  */
    private var month = 0

    /**
     * 默认当月
     */
    constructor() : this(Date())

    /**
     * 通过日期初始化
     */
    constructor(date: Date) {
        val c: Calendar = Calendar.getInstance()
        c.time = date
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH) + 1
    }

    /**
     * 通过日历初始化
     */
    constructor(calendar: Calendar) {
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
    }

    /**
     * 通过年月初始化
     *
     * @param year 年
     * @param month 月
     */
    constructor(year: Int, month: Int) {
        this.year = year
        this.month = month
    }

    /**
     * 通过指定日期获取阳历月
     *
     * @param date 日期
     * @return 阳历月
     */
    fun fromDate(date: Date): SolarMonth {
        return SolarMonth(date)
    }

    /**
     * 通过指定日历获取阳历月
     *
     * @param calendar 日历
     * @return 阳历月
     */
    fun fromCalendar(calendar: Calendar): SolarMonth {
        return SolarMonth(calendar)
    }

    /**
     * 通过指定年月获取阳历月
     *
     * @param year 年
     * @param month 月
     * @return 阳历月
     */
    fun fromYm(year: Int, month: Int): SolarMonth {
        return SolarMonth(year, month)
    }

    /**
     * 获取年
     *
     * @return 年
     */
    fun getYear(): Int {
        return year
    }

    /**
     * 获取月
     *
     * @return 月
     */
    fun getMonth(): Int {
        return month
    }

    /**
     * 获取本月的阳历日期列表
     *
     * @return 阳历日期列表
     */
    fun getDays(): List<Solar> {
        val l: MutableList<Solar> = ArrayList(31)
        val d = Solar(year, month, 1)
        l.add(d)
        val days: Int = SolarUtil.getDaysOfMonth(year, month)
        for (i in 1 until days) {
            l.add(d.next(i))
        }
        return l
    }

    /**
     * 获取往后推几个月的阳历月，如果要往前推，则月数用负数
     * @param months 月数
     * @return 阳历月
     */
    fun next(months: Int): SolarMonth {
        val c: Calendar = Calendar.getInstance()
        c.set(year, month - 1, 1)
        c.add(Calendar.MONTH, months)
        return SolarMonth(c)
    }

    override fun toString(): String {
        return "$year-$month"
    }

    fun toFullString(): String {
        return year.toString() + "年" + month + "月"
    }
}
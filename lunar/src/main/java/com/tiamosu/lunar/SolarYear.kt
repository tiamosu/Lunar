package com.tiamosu.lunar

import java.util.*
import kotlin.collections.ArrayList

/**
 * 描述：阳历年
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class SolarYear {
    /** 年  */
    private var year = 0

    /** 一年的月数  */
    val MONTH_COUNT = 12

    /**
     * 默认当年
     */
    constructor() : this(Date())

    /**
     * 通过日期初始化
     */
    constructor(date: Date) {
        val c: Calendar = Calendar.getInstance()
        c.time = date
        year = c.get(Calendar.YEAR)
    }

    /**
     * 通过日历初始化
     */
    constructor(calendar: Calendar) {
        year = calendar.get(Calendar.YEAR)
    }

    /**
     * 通过年初始化
     *
     * @param year 年
     */
    constructor(year: Int) {
        this.year = year
    }

    /**
     * 通过指定日期获取阳历年
     *
     * @param date 日期
     * @return 阳历年
     */
    fun fromDate(date: Date): SolarYear {
        return SolarYear(date)
    }

    /**
     * 通过指定日历获取阳历年
     *
     * @param calendar 日历
     * @return 阳历年
     */
    fun fromCalendar(calendar: Calendar): SolarYear {
        return SolarYear(calendar)
    }

    /**
     * 通过指定年份获取阳历年
     *
     * @param year 年
     * @return 阳历年
     */
    fun fromYear(year: Int): SolarYear {
        return SolarYear(year)
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
     * 获取本年的阳历月列表
     *
     * @return 阳历月列表
     */
    fun getMonths(): List<SolarMonth> {
        val l: MutableList<SolarMonth> = ArrayList(MONTH_COUNT)
        val m = SolarMonth(year, 1)
        l.add(m)
        for (i in 1 until MONTH_COUNT) {
            l.add(m.next(i))
        }
        return l
    }

    /**
     * 获取往后推几年的阳历年，如果要往前推，则年数用负数
     * @param years 年数
     * @return 阳历年
     */
    fun next(years: Int): SolarYear {
        val c: Calendar = Calendar.getInstance()
        c.set(year, Calendar.JANUARY, 1)
        c.add(Calendar.YEAR, years)
        return SolarYear(c)
    }

    override fun toString(): String {
        return year.toString() + ""
    }

    fun toFullString(): String? {
        return year.toString() + "年"
    }
}
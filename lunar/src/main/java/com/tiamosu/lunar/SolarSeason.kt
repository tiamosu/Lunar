package com.tiamosu.lunar

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

/**
 * 描述：阳历季度
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class SolarSeason {
    /** 年  */
    private var year = 0

    /** 月  */
    private var month = 0

    companion object {

        /** 一个季度的月数  */
        const val MONTH_COUNT = 3

        /**
         * 通过指定日期获取阳历季度
         *
         * @param date 日期
         * @return 阳历季度
         */
        fun fromDate(date: Date): SolarSeason {
            return SolarSeason(date)
        }

        /**
         * 通过指定日历获取阳历季度
         *
         * @param calendar 日历
         * @return 阳历季度
         */
        fun fromCalendar(calendar: Calendar): SolarSeason {
            return SolarSeason(calendar)
        }

        /**
         * 通过指定年月获取阳历季度
         *
         * @param year 年
         * @param month 月
         * @return 阳历季度
         */
        fun fromYm(year: Int, month: Int): SolarSeason {
            return SolarSeason(year, month)
        }
    }

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
     * 获取当月是第几季度
     * @return 季度序号，从1开始
     */
    fun getIndex(): Int {
        return ceil(month * 1.0 / MONTH_COUNT).toInt()
    }

    /**
     * 季度推移
     * @param seasons 推移的季度数，负数为倒推
     * @return 推移后的季度
     */
    fun next(seasons: Int): SolarSeason? {
        if (0 == seasons) {
            return SolarSeason(year, month)
        }
        val c: Calendar = Calendar.getInstance()
        c.set(year, month - 1, 1)
        c.add(Calendar.MONTH, MONTH_COUNT * seasons)
        return SolarSeason(c)
    }

    /**
     * 获取本季度的月份
     * @return 本季度的月份列表
     */
    fun getMonths(): List<SolarMonth> {
        val l: MutableList<SolarMonth> = ArrayList()
        val index = getIndex() - 1
        for (i in 0 until MONTH_COUNT) {
            l.add(SolarMonth(year, MONTH_COUNT * index + i + 1))
        }
        return l
    }

    override fun toString(): String {
        return year.toString() + "." + getIndex()
    }

    fun toFullString(): String {
        return year.toString() + "年" + getIndex() + "季度"
    }
}
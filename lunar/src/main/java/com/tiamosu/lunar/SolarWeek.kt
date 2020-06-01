package com.tiamosu.lunar

import com.tiamosu.lunar.utils.SolarUtil
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

/**
 * 描述：阳历周
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class SolarWeek {
    /** 年  */
    private var year = 0

    /** 月  */
    private var month = 0

    /** 日  */
    private var day = 0

    /** 星期几作为一周的开始，1234560分别代表星期一至星期天  */
    private var start = 0

    companion object {

        /**
         * 通过指定日期获取阳历周
         *
         * @param date 日期
         * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
         * @return 阳历周
         */
        fun fromDate(date: Date, start: Int): SolarWeek {
            return SolarWeek(date, start)
        }

        /**
         * 通过指定日历获取阳历周
         *
         * @param calendar 日历
         * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
         * @return 阳历周
         */
        fun fromCalendar(calendar: Calendar, start: Int): SolarWeek {
            return SolarWeek(calendar, start)
        }

        /**
         * 通过指定年月日获取阳历周
         *
         * @param year 年
         * @param month 月，1到12
         * @param day 日，1到31
         * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
         * @return 阳历周
         */
        fun fromYmd(year: Int, month: Int, day: Int, start: Int): SolarWeek {
            return SolarWeek(year, month, day, start)
        }
    }

    /**
     * 默认当月
     * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
     */
    constructor(start: Int) : this(Date(), start)

    /**
     * 通过日期初始化
     * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
     */
    constructor(date: Date, start: Int) {
        val c: Calendar = Calendar.getInstance()
        c.time = date
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH) + 1
        day = c.get(Calendar.DATE)
        this.start = start
    }

    /**
     * 通过日历初始化
     * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
     */
    constructor(calendar: Calendar, start: Int) {
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DATE)
        this.start = start
    }

    /**
     * 通过年月初始化
     *
     * @param year 年
     * @param month 月，1到12
     * @param day 日，1到31
     * @param start 星期几作为一周的开始，1234560分别代表星期一至星期天
     */
    constructor(year: Int, month: Int, day: Int, start: Int) {
        this.year = year
        this.month = month
        this.day = day
        this.start = start
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
     * @return 1到12
     */
    fun getMonth(): Int {
        return month
    }

    /**
     * 获取日期
     *
     * @return 1到31之间的数字
     */
    fun getDay(): Int {
        return day
    }

    /**
     * 获取星期几作为一周的开始，1234560分别代表星期一至星期天
     *
     * @return 1234560分别代表星期一至星期天
     */
    fun getStart(): Int {
        return start
    }

    /**
     * 获取当前日期是在当月第几周
     * @return 周序号，从1开始
     */
    fun getIndex(): Int {
        val c: Calendar = Calendar.getInstance()
        c.set(year, month - 1, 1)
        var firstDayWeek = c.get(Calendar.DAY_OF_WEEK) - 1
        if (firstDayWeek == 0) {
            firstDayWeek = 7
        }
        return ceil((day + firstDayWeek - start) / 7.0).toInt()
    }

    /**
     * 周推移
     * @param weeks 推移的周数，负数为倒推
     * @param separateMonth 是否按月单独计算
     * @return 推移后的阳历周
     */
    fun next(weeks: Int, separateMonth: Boolean): SolarWeek {
        if (0 == weeks) {
            return SolarWeek(year, month, day, start)
        }
        return if (separateMonth) {
            var n = weeks
            val c = Calendar.getInstance()
            c.set(year, month - 1, day)
            var week = SolarWeek(c, start)
            var month = month
            val plus = n > 0
            while (0 != n) {
                c.add(Calendar.DATE, if (plus) 7 else -7)
                week = SolarWeek(c, start)
                var weekMonth = week.getMonth()
                if (month != weekMonth) {
                    val index = week.getIndex()
                    if (plus) {
                        if (1 == index) {
                            val firstDay: Solar = week.getFirstDay()
                            week = SolarWeek(
                                firstDay.getYear(),
                                firstDay.getMonth(),
                                firstDay.getDay(),
                                start
                            )
                            weekMonth = week.getMonth()
                        } else {
                            c.set(week.getYear(), week.getMonth() - 1, 1)
                            week = SolarWeek(c, start)
                        }
                    } else {
                        val size = SolarUtil.getWeeksOfMonth(week.getYear(), week.getMonth(), start)
                        if (size == index) {
                            val firstDay: Solar = week.getFirstDay()
                            val lastDay = firstDay.next(6)
                            week = SolarWeek(
                                lastDay.getYear(),
                                lastDay.getMonth(),
                                lastDay.getDay(),
                                start
                            )
                            weekMonth = week.getMonth()
                        } else {
                            c.set(
                                week.getYear(),
                                week.getMonth() - 1,
                                SolarUtil.getDaysOfMonth(week.getYear(), week.getMonth())
                            )
                            week = SolarWeek(c, start)
                        }
                    }
                    month = weekMonth
                }
                n -= if (plus) 1 else -1
            }
            week
        } else {
            val c: Calendar = Calendar.getInstance()
            c.set(year, month - 1, day)
            c.add(Calendar.DATE, weeks * 7)
            SolarWeek(c, start)
        }
    }

    /**
     * 获取本周第一天的阳历日期（可能跨月）
     * @return 本周第一天的阳历日期
     */
    fun getFirstDay(): Solar {
        val c: Calendar = Calendar.getInstance()
        c.set(year, month - 1, day)
        val week: Int = c.get(Calendar.DAY_OF_WEEK) - 1
        var prev = week - start
        if (prev < 0) {
            prev += 7
        }
        c.add(Calendar.DATE, -prev)
        return Solar(c)
    }

    /**
     * 获取本周第一天的阳历日期（仅限当月）
     * @return 本周第一天的阳历日期
     */
    fun getFirstDayInMonth(): Solar? {
        val days = getDays()
        for (day in days) {
            if (month == day.getMonth()) {
                return day
            }
        }
        return null
    }

    /**
     * 获取本周的阳历日期列表（可能跨月）
     * @return 本周的阳历日期列表
     */
    fun getDays(): List<Solar> {
        val firstDay = getFirstDay()
        val l: MutableList<Solar> = ArrayList()
        l.add(firstDay)
        for (i in 1..6) {
            l.add(firstDay.next(i))
        }
        return l
    }

    /**
     * 获取本周的阳历日期列表（仅限当月）
     * @return 本周的阳历日期列表（仅限当月）
     */
    fun getDaysInMonth(): List<Solar> {
        val days = getDays()
        val l: MutableList<Solar> = ArrayList()
        for (day in days) {
            if (month != day.getMonth()) {
                continue
            }
            l.add(day)
        }
        return l
    }

    override fun toString(): String {
        return year.toString() + "." + month + "." + getIndex()
    }

    fun toFullString(): String {
        return year.toString() + "年" + month + "月第" + getIndex() + "周"
    }
}
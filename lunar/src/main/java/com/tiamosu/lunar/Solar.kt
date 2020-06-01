package com.tiamosu.lunar

import com.tiamosu.lunar.utils.SolarUtil
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

/**
 * 描述：阳历日期
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class Solar {
    /** 年  */
    private var year = 0

    /** 月  */
    private var month = 0

    /** 日  */
    private var day = 0

    /** 时  */
    private var hour = 0

    /** 分  */
    private var minute = 0

    /** 秒  */
    private var second = 0

    /** 日历  */
    private var calendar: Calendar

    companion object {
        /** 2000年儒略日数(2000-1-1 12:00:00 UTC)  */
        const val J2000 = 2451545.0

        /**
         * 通过指定日期获取阳历
         *
         * @param date 日期
         * @return 阳历
         */
        fun fromDate(date: Date): Solar {
            return Solar(date)
        }

        /**
         * 通过指定日历获取阳历
         *
         * @param calendar 日历
         * @return 阳历
         */
        fun fromCalendar(calendar: Calendar): Solar {
            return Solar(calendar)
        }

        /**
         * 通过指定儒略日获取阳历
         *
         * @param julianDay 儒略日
         * @return 阳历
         */
        fun fromJulianDay(julianDay: Double): Solar {
            return Solar(julianDay)
        }

        /**
         * 通过指定年月日获取阳历
         *
         * @param year 年
         * @param month 月，1到12
         * @param day 日，1到31
         * @return 阳历
         */
        fun fromYmd(year: Int, month: Int, day: Int): Solar {
            return Solar(year, month, day)
        }

        /**
         * 通过指定年月日时分获取阳历
         *
         * @param year 年
         * @param month 月，1到12
         * @param day 日，1到31
         * @param hour 小时，0到23
         * @param minute 分钟，0到59
         * @param second 秒钟，0到59
         * @return 阳历
         */
        fun fromYmdHms(
            year: Int,
            month: Int,
            day: Int,
            hour: Int,
            minute: Int,
            second: Int
        ): Solar {
            return Solar(year, month, day, hour, minute, second)
        }
    }

    /**
     * 默认使用当前日期初始化
     */
    constructor() : this(Date())

    /**
     * 通过年月日初始化
     *
     * @param year 年
     * @param month 月，1到12
     * @param day 日，1到31
     */
    constructor(year: Int, month: Int, day: Int) : this(year, month, day, 0, 0, 0)

    /**
     * 通过年月日初始化
     *
     * @param year 年
     * @param month 月，1到12
     * @param day 日，1到31
     * @param hour 小时，0到23
     * @param minute 分钟，0到59
     * @param second 秒钟，0到59
     */
    constructor(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int
    ) {
        calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, hour, minute, second)
        this.year = year
        this.month = month
        this.day = day
        this.hour = hour
        this.minute = minute
        this.second = second
    }

    /**
     * 通过日期初始化
     *
     * @param date 日期
     */
    constructor(date: Date) {
        calendar = Calendar.getInstance().apply {
            time = date
            year = get(Calendar.YEAR)
            month = get(Calendar.MONTH) + 1
            day = get(Calendar.DATE)
            hour = get(Calendar.HOUR_OF_DAY)
            minute = get(Calendar.MINUTE)
            second = get(Calendar.SECOND)
        }
    }

    /**
     * 通过日历初始化
     *
     * @param calendar 日历
     */
    constructor(calendar: Calendar) {
        this.calendar = calendar.apply {
            year = get(Calendar.YEAR)
            month = get(Calendar.MONTH) + 1
            day = get(Calendar.DATE)
            hour = get(Calendar.HOUR_OF_DAY)
            minute = get(Calendar.MINUTE)
            second = get(Calendar.SECOND)
        }
    }

    /**
     * 通过儒略日初始化
     * @param julianDay 儒略日
     */
    constructor(julianDay: Double) {
        var julianDay1 = julianDay
        julianDay1 += 0.5

        // 日数的整数部份
        var a = int2(julianDay1)
        // 日数的小数部分
        var f = julianDay1 - a
        var jd: Double
        if (a > 2299161) {
            jd = int2((a - 1867216.25) / 36524.25)
            a += 1 + jd - int2(jd / 4)
        }
        // 向前移4年零2个月
        a += 1524.0
        var y = int2((a - 122.1) / 365.25)
        // 去除整年日数后余下日数
        jd = a - int2(365.25 * y)
        var m = int2(jd / 30.6001)
        // 去除整月日数后余下日数
        val d = int2(jd - int2(m * 30.6001))
        y -= 4716.0
        m--
        if (m > 12) {
            m -= 12.0
        }
        if (m <= 2) {
            y++
        }

        // 日的小数转为时分秒
        f *= 24.0
        val h = int2(f)
        f -= h
        f *= 60.0
        val mi = int2(f)
        f -= mi
        f *= 60.0
        val s = int2(f)

        calendar = Calendar.getInstance().apply {
            set(y.toInt(), m.toInt() - 1, d.toInt(), h.toInt(), mi.toInt(), s.toInt())
            year = get(Calendar.YEAR)
            month = get(Calendar.MONTH) + 1
            day = get(Calendar.DATE)
            hour = get(Calendar.HOUR_OF_DAY)
            minute = get(Calendar.MINUTE)
            second = get(Calendar.SECOND)
        }
    }

    /**
     * 是否闰年
     *
     * @return true/false 闰年/非闰年
     */
    fun isLeapYear(): Boolean {
        return SolarUtil.isLeapYear(year)
    }

    /**
     * 获取星期，0代表周日，1代表周一
     *
     * @return 0123456
     */
    fun getWeek(): Int {
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    /**
     * 获取星期的中文
     *
     * @return 日一二三四五六
     */
    fun getWeekInChinese(): String {
        return SolarUtil.WEEK[getWeek()]
    }

    /**
     * 获取节日，有可能一天会有多个节日
     *
     * @return 劳动节等
     */
    fun getFestivals(): List<String> {
        val l: MutableList<String> = ArrayList()
        //获取几月几日对应的节日
        var f = SolarUtil.FESTIVAL["$month-$day"]
        if (null != f) {
            l.add(f)
        }
        //计算几月第几个星期几对应的节日
        //第几周
        var weekInMonth: Int = calendar.get(Calendar.WEEK_OF_MONTH)
        //星期几，0代表星期天
        val week = getWeek()
        //星期天很奇葩，会多算一周，需要减掉
        if (0 == week) {
            weekInMonth--
        }
        f = SolarUtil.WEEK_FESTIVAL["$month-$weekInMonth-$week"]
        if (null != f) {
            l.add(f)
        }
        return l
    }

    /**
     * 获取非正式的节日，有可能一天会有多个节日
     *
     * @return 非正式的节日列表，如中元节
     */
    fun getOtherFestivals(): List<String> {
        val l: MutableList<String> = ArrayList()
        val fs = SolarUtil.OTHER_FESTIVAL["$month-$day"]
        if (null != fs) {
            l.addAll(fs)
        }
        return l
    }

    /**
     * 获取星座
     *
     * @return 星座
     */
    fun getXingZuo(): String {
        var index = 11
        val m = month
        val d = day
        val y = m * 100 + d
        if (y in 321..419) {
            index = 0
        } else if (y in 420..520) {
            index = 1
        } else if (y in 521..620) {
            index = 2
        } else if (y in 621..722) {
            index = 3
        } else if (y in 723..822) {
            index = 4
        } else if (y in 823..922) {
            index = 5
        } else if (y in 923..1022) {
            index = 6
        } else if (y in 1023..1121) {
            index = 7
        } else if (y in 1122..1221) {
            index = 8
        } else if (y >= 1222 || y <= 119) {
            index = 9
        } else if (y <= 218) {
            index = 10
        }
        return SolarUtil.XINGZUO[index]
    }

    /**
     * 获取年份
     *
     * @return 如2015
     */
    fun getYear(): Int {
        return year
    }

    /**
     * 获取月份
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
     * 获取小时
     *
     * @return 0到23之间的数字
     */
    fun getHour(): Int {
        return hour
    }

    /**
     * 获取分钟
     *
     * @return 0到59之间的数字
     */
    fun getMinute(): Int {
        return minute
    }

    /**
     * 获取秒钟
     *
     * @return 0到59之间的数字
     */
    fun getSecond(): Int {
        return second
    }

    /**
     * 获取农历
     * @return 农历
     */
    fun getLunar(): Lunar {
        return Lunar(calendar.time)
    }

    private fun int2(v: Double): Double {
        var v1 = v
        v1 = floor(v1)
        return if (v1 < 0) v1 + 1 else v1
    }

    /**
     * 获取儒略日
     * @return 儒略日
     */
    fun getJulianDay(): Double {
        var y = year.toDouble()
        var m = month.toDouble()
        var n = 0.0
        if (m <= 2) {
            m += 12.0
            y--
        }

        // 判断是否为UTC日1582*372+10*31+15
        if (year * 372 + month * 31 + day >= 588829) {
            n = int2(y / 100)
            // 加百年闰
            n = 2 - n + int2(n / 4)
        }

        // 加上年引起的偏移日数
        n += int2(365.2500001 * (y + 4716))
        // 加上月引起的偏移日数及日偏移数
        n += int2(30.6 * (m + 1)) + day
        n += ((second * 1.0 / 60 + minute) / 60 + hour) / 24 - 1524.5
        return n
    }

    /**
     * 获取日历
     *
     * @return 日历
     */
    fun getCalendar(): Calendar {
        return calendar
    }

    override fun toString(): String {
        return toYmd()
    }

    fun toYmd(): String {
        return year.toString() + "-" + (if (month < 10) "0" else "") + month + "-" + (if (day < 10) "0" else "") + day
    }

    fun toYmdHms(): String {
        return toYmd() + " " + (if (hour < 10) "0" else "") + hour + ":" +
                (if (minute < 10) "0" else "") + minute + ":" + (if (second < 10) "0" else "") + second
    }

    fun toFullString(): String {
        val s = StringBuilder()
        s.append(toYmdHms())
        if (isLeapYear()) {
            s.append(" ")
            s.append("闰年")
        }
        s.append(" ")
        s.append("星期")
        s.append(getWeekInChinese())

        for (f in getFestivals()) {
            s.append(" (")
            s.append(f)
            s.append(")")
        }
        for (f in getOtherFestivals()) {
            s.append(" (")
            s.append(f)
            s.append(")")
        }
        s.append(" ")
        s.append(getXingZuo())
        s.append("座")
        return s.toString()
    }

    /**
     * 获取往后推几天的阳历日期，如果要往前推，则天数用负数
     * @param days 天数
     * @return 阳历日期
     */
    fun next(days: Int): Solar {
        val c: Calendar = Calendar.getInstance()
        c.set(year, month - 1, day, hour, minute, second)
        c.add(Calendar.DATE, days)
        return Solar(c)
    }
}
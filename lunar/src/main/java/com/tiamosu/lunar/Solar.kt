package com.tiamosu.lunar

import com.tiamosu.lunar.utils.HolidayUtil.getHoliday
import com.tiamosu.lunar.utils.LunarUtil
import com.tiamosu.lunar.utils.LunarUtil.getJiaZiIndex
import com.tiamosu.lunar.utils.SolarUtil
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.roundToInt

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
        var d = (julianDay + 0.5).toInt()
        var f = julianDay + 0.5 - d

        if (d >= 2299161) {
            val c = ((d - 1867216.25) / 36524.25).toInt()
            d += 1 + c - (c * 1.0 / 4).toInt()
        }
        d += 1524
        var year = ((d - 122.1) / 365.25).toInt()
        d -= (365.25 * year).toInt()
        var month = (d * 1.0 / 30.601).toInt()
        d -= (30.601 * month).toInt()
        val day = d
        if (month > 13) {
            month -= 13
            year -= 4715
        } else {
            month -= 1
            year -= 4716
        }
        f *= 24.0
        val hour = f.toInt()

        f -= hour.toDouble()
        f *= 60.0
        val minute = f.toInt()

        f -= minute.toDouble()
        f *= 60.0
        val second = f.roundToInt()

        calendar = Calendar.getInstance()
        calendar[year, month - 1, day, hour, minute] = second

        this.year = year
        this.month = month
        this.day = day
        this.hour = hour
        this.minute = minute
        this.second = second
    }

    /**
     * 通过八字获取阳历列表（晚子时日柱按当天）
     * @param yearGanZhi 年柱
     * @param monthGanZhi 月柱
     * @param dayGanZhi 日柱
     * @param timeGanZhi 时柱
     * @return 符合的阳历列表
     */
    fun fromBaZi(
        yearGanZhi: String,
        monthGanZhi: String,
        dayGanZhi: String,
        timeGanZhi: String
    ): List<Solar> {
        return fromBaZi(yearGanZhi, monthGanZhi, dayGanZhi, timeGanZhi, 2)
    }

    /**
     * 通过八字获取阳历列表（晚子时日柱按当天）
     * @param yearGanZhi 年柱
     * @param monthGanZhi 月柱
     * @param dayGanZhi 日柱
     * @param timeGanZhi 时柱
     * @return 符合的阳历列表
     */
    fun fromBaZi(
        yearGanZhi: String,
        monthGanZhi: String,
        dayGanZhi: String,
        timeGanZhi: String,
        sect: Int
    ): List<Solar> {
        val newSect = if (1 == sect) 1 else 2
        val l: MutableList<Solar> = ArrayList()
        val today = Solar()
        var lunar = today.getLunar()
        var offsetYear = getJiaZiIndex(lunar.getYearInGanZhiExact()) - getJiaZiIndex(yearGanZhi)
        if (offsetYear < 0) {
            offsetYear += 60
        }
        var startYear = today.getYear() - offsetYear
        var hour = 0
        val timeZhi = timeGanZhi.substring(1)
        var i = 0
        val j = LunarUtil.ZHI.size
        while (i < j) {
            if (LunarUtil.ZHI[i] == timeZhi) {
                hour = (i - 1) * 2
            }
            i++
        }
        while (startYear >= SolarUtil.BASE_YEAR - 1) {
            var year = startYear - 1
            var counter = 0
            var month = 12
            var day: Int
            var found = false
            while (counter < 15) {
                if (year >= SolarUtil.BASE_YEAR) {
                    day = 1
                    if (year == SolarUtil.BASE_YEAR && month == SolarUtil.BASE_MONTH) {
                        day = SolarUtil.BASE_DAY
                    }
                    val solar = Solar(year, month, day, hour, 0, 0)
                    lunar = solar.getLunar()
                    if (lunar.getYearInGanZhiExact() == yearGanZhi && lunar.getMonthInGanZhiExact() == monthGanZhi) {
                        found = true
                        break
                    }
                }
                month++
                if (month > 12) {
                    month = 1
                    year++
                }
                counter++
            }
            if (found) {
                counter = 0
                month--
                if (month < 1) {
                    month = 12
                    year--
                }
                day = 1
                if (year == SolarUtil.BASE_YEAR && month == SolarUtil.BASE_MONTH) {
                    day = SolarUtil.BASE_DAY
                }
                var solar = Solar(year, month, day, hour, 0, 0)
                while (counter < 61) {
                    lunar = solar.getLunar()
                    val dgz =
                        if (2 == newSect) lunar.getDayInGanZhiExact2() else lunar.getDayInGanZhiExact()
                    if (lunar.getYearInGanZhiExact() == yearGanZhi
                        && lunar.getMonthInGanZhiExact() == monthGanZhi
                        && dgz == dayGanZhi
                        && lunar.getTimeInGanZhi() == timeGanZhi
                    ) {
                        l.add(solar)
                        break
                    }
                    solar = solar.next(1)
                    counter++
                }
            }
            startYear -= 60
        }
        return l
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
        val weeks = ceil(day / 7.0).toInt()
        //星期几，0代表星期天
        val week = getWeek()
        f = SolarUtil.WEEK_FESTIVAL["$month-$weeks-$week"]
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
        val y = month * 100 + day
        if (y in 321..419) {
            index = 0
        } else if (y in 420..520) {
            index = 1
        } else if (y in 521..621) {
            index = 2
        } else if (y in 622..722) {
            index = 3
        } else if (y in 723..822) {
            index = 4
        } else if (y in 823..922) {
            index = 5
        } else if (y in 923..1023) {
            index = 6
        } else if (y in 1024..1122) {
            index = 7
        } else if (y in 1123..1221) {
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

    /**
     * 获取儒略日
     * @return 儒略日
     */
    fun getJulianDay(): Double {
        var y = year
        var m = month
        val d = day + ((second * 1.0 / 60 + minute) / 60 + hour) / 24
        var n = 0
        var g = false
        if (y * 372 + m * 31 + d.toInt() >= 588829) {
            g = true
        }
        if (m <= 2) {
            m += 12
            y--
        }
        if (g) {
            n = (y * 1.0 / 100).toInt()
            n = 2 - n + (n * 1.0 / 4).toInt()
        }
        return (365.25 * (y + 4716)).toInt() + (30.6001 * (m + 1)).toInt() + d + n - 1524.5
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

    fun next(days: Int): Solar {
        return next(days, false)
    }

    /**
     * 获取往后推几天的阳历日期，如果要往前推，则天数用负数
     * @param days 天数
     * @return 阳历日期
     */
    fun next(days: Int, onlyWorkday: Boolean): Solar {
        val c = Calendar.getInstance()
        c.set(year, month - 1, day, hour, minute, second)

        if (0 != days) {
            if (!onlyWorkday) {
                c.add(Calendar.DATE, days)
            } else {
                var rest = abs(days)
                val add = if (days < 1) -1 else 1
                while (rest > 0) {
                    c.add(Calendar.DATE, add)
                    var work = true
                    val holiday = getHoliday(
                        c[Calendar.YEAR], c[Calendar.MONTH] + 1,
                        c[Calendar.DAY_OF_MONTH]
                    )
                    if (null == holiday) {
                        val week = c[Calendar.DAY_OF_WEEK]
                        if (1 == week || 7 == week) {
                            work = false
                        }
                    } else {
                        work = holiday.isWork()
                    }
                    if (work) {
                        rest--
                    }
                }
            }
        }
        return Solar(c)
    }
}
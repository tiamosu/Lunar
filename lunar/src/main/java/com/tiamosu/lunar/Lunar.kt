package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil
import com.tiamosu.lunar.utils.SolarUtil
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * 描述：农历日期
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class Lunar {
    /** 1角度对应的弧度  */
    private val RAD_PER_DEGREE = Math.PI / 180

    /** 1弧度对应的角度  */
    private val DEGREE_PER_RAD = 180 / Math.PI

    /** 1弧度对应的角秒  */
    private val SECOND_PER_RAD = 180 * 3600 / Math.PI

    /** 节气表，国标以冬至为首个节气  */
    private val JIE_QI = arrayOf(
        "冬至",
        "小寒",
        "大寒",
        "立春",
        "雨水",
        "惊蛰",
        "春分",
        "清明",
        "谷雨",
        "立夏",
        "小满",
        "芒种",
        "夏至",
        "小暑",
        "大暑",
        "立秋",
        "处暑",
        "白露",
        "秋分",
        "寒露",
        "霜降",
        "立冬",
        "小雪",
        "大雪"
    )

    /** 黄经周期项  */
    private val E10 = doubleArrayOf(
        1.75347045673,
        0.00000000000,
        0.0000000000,
        0.03341656456,
        4.66925680417,
        6283.0758499914,
        0.00034894275,
        4.62610241759,
        12566.1516999828,
        0.00003417571,
        2.82886579606,
        3.5231183490,
        0.00003497056,
        2.74411800971,
        5753.3848848968,
        0.00003135896,
        3.62767041758,
        77713.7714681205,
        0.00002676218,
        4.41808351397,
        7860.4193924392,
        0.00002342687,
        6.13516237631,
        3930.2096962196,
        0.00001273166,
        2.03709655772,
        529.6909650946,
        0.00001324292,
        0.74246356352,
        11506.7697697936,
        0.00000901855,
        2.04505443513,
        26.2983197998,
        0.00001199167,
        1.10962944315,
        1577.3435424478,
        0.00000857223,
        3.50849156957,
        398.1490034082,
        0.00000779786,
        1.17882652114,
        5223.6939198022,
        0.00000990250,
        5.23268129594,
        5884.9268465832,
        0.00000753141,
        2.53339053818,
        5507.5532386674,
        0.00000505264,
        4.58292563052,
        18849.2275499742,
        0.00000492379,
        4.20506639861,
        775.5226113240,
        0.00000356655,
        2.91954116867,
        0.0673103028,
        0.00000284125,
        1.89869034186,
        796.2980068164,
        0.00000242810,
        0.34481140906,
        5486.7778431750,
        0.00000317087,
        5.84901952218,
        11790.6290886588,
        0.00000271039,
        0.31488607649,
        10977.0788046990,
        0.00000206160,
        4.80646606059,
        2544.3144198834,
        0.00000205385,
        1.86947813692,
        5573.1428014331,
        0.00000202261,
        2.45767795458,
        6069.7767545534,
        0.00000126184,
        1.08302630210,
        20.7753954924,
        0.00000155516,
        0.83306073807,
        213.2990954380,
        0.00000115132,
        0.64544911683,
        0.9803210682,
        0.00000102851,
        0.63599846727,
        4694.0029547076,
        0.00000101724,
        4.26679821365,
        7.1135470008,
        0.00000099206,
        6.20992940258,
        2146.1654164752,
        0.00000132212,
        3.41118275555,
        2942.4634232916,
        0.00000097607,
        0.68101272270,
        155.4203994342,
        0.00000085128,
        1.29870743025,
        6275.9623029906,
        0.00000074651,
        1.75508916159,
        5088.6288397668,
        0.00000101895,
        0.97569221824,
        15720.8387848784,
        0.00000084711,
        3.67080093025,
        71430.6956181291,
        0.00000073547,
        4.67926565481,
        801.8209311238,
        0.00000073874,
        3.50319443167,
        3154.6870848956,
        0.00000078756,
        3.03698313141,
        12036.4607348882,
        0.00000079637,
        1.80791330700,
        17260.1546546904,
        0.00000085803,
        5.98322631256,
        161000.6857376741,
        0.00000056963,
        2.78430398043,
        6286.5989683404,
        0.00000061148,
        1.81839811024,
        7084.8967811152,
        0.00000069627,
        0.83297596966,
        9437.7629348870,
        0.00000056116,
        4.38694880779,
        14143.4952424306,
        0.00000062449,
        3.97763880587,
        8827.3902698748,
        0.00000051145,
        0.28306864501,
        5856.4776591154,
        0.00000055577,
        3.47006009062,
        6279.5527316424,
        0.00000041036,
        5.36817351402,
        8429.2412664666,
        0.00000051605,
        1.33282746983,
        1748.0164130670,
        0.00000051992,
        0.18914945834,
        12139.5535091068,
        0.00000049000,
        0.48735065033,
        1194.4470102246,
        0.00000039200,
        6.16832995016,
        10447.3878396044,
        0.00000035566,
        1.77597314691,
        6812.7668150860,
        0.00000036770,
        6.04133859347,
        10213.2855462110,
        0.00000036596,
        2.56955238628,
        1059.3819301892,
        0.00000033291,
        0.59309499459,
        17789.8456197850,
        0.00000035954,
        1.70876111898,
        2352.8661537718
    )

    /** 黄经泊松1项  */
    private val E11 = doubleArrayOf(
        6283.31966747491,
        0.00000000000,
        0.0000000000,
        0.00206058863,
        2.67823455584,
        6283.0758499914,
        0.00004303430,
        2.63512650414,
        12566.1516999828,
        0.00000425264,
        1.59046980729,
        3.5231183490,
        0.00000108977,
        2.96618001993,
        1577.3435424478,
        0.00000093478,
        2.59212835365,
        18849.2275499742,
        0.00000119261,
        5.79557487799,
        26.2983197998,
        0.00000072122,
        1.13846158196,
        529.6909650946,
        0.00000067768,
        1.87472304791,
        398.1490034082,
        0.00000067327,
        4.40918235168,
        5507.5532386674,
        0.00000059027,
        2.88797038460,
        5223.6939198022,
        0.00000055976,
        2.17471680261,
        155.4203994342,
        0.00000045407,
        0.39803079805,
        796.2980068164,
        0.00000036369,
        0.46624739835,
        775.5226113240,
        0.00000028958,
        2.64707383882,
        7.1135470008,
        0.00000019097,
        1.84628332577,
        5486.7778431750,
        0.00000020844,
        5.34138275149,
        0.9803210682,
        0.00000018508,
        4.96855124577,
        213.2990954380,
        0.00000016233,
        0.03216483047,
        2544.3144198834,
        0.00000017293,
        2.99116864949,
        6275.9623029906
    )

    /** 黄经泊松2项  */
    private val E12 = doubleArrayOf(
        0.00052918870,
        0.00000000000,
        0.0000000000,
        0.00008719837,
        1.07209665242,
        6283.0758499914,
        0.00000309125,
        0.86728818832,
        12566.1516999828,
        0.00000027339,
        0.05297871691,
        3.5231183490,
        0.00000016334,
        5.18826691036,
        26.2983197998,
        0.00000015752,
        3.68457889430,
        155.4203994342,
        0.00000009541,
        0.75742297675,
        18849.2275499742,
        0.00000008937,
        2.05705419118,
        77713.7714681205,
        0.00000006952,
        0.82673305410,
        775.5226113240,
        0.00000005064,
        4.66284525271,
        1577.3435424478
    )
    private val E13 = doubleArrayOf(
        0.00000289226,
        5.84384198723,
        6283.0758499914,
        0.00000034955,
        0.00000000000,
        0.0000000000,
        0.00000016819,
        5.48766912348,
        12566.1516999828
    )
    private val E14 = doubleArrayOf(
        0.00000114084,
        3.14159265359,
        0.0000000000,
        0.00000007717,
        4.13446589358,
        6283.0758499914,
        0.00000000765,
        3.83803776214,
        12566.1516999828
    )
    private val E15 = doubleArrayOf(0.00000000878, 3.14159265359, 0.0000000000)

    /** 黄纬周期项  */
    private val E20 = doubleArrayOf(
        0.00000279620,
        3.19870156017,
        84334.6615813083,
        0.00000101643,
        5.42248619256,
        5507.5532386674,
        0.00000080445,
        3.88013204458,
        5223.6939198022,
        0.00000043806,
        3.70444689758,
        2352.8661537718,
        0.00000031933,
        4.00026369781,
        1577.3435424478,
        0.00000022724,
        3.98473831560,
        1047.7473117547,
        0.00000016392,
        3.56456119782,
        5856.4776591154,
        0.00000018141,
        4.98367470263,
        6283.0758499914,
        0.00000014443,
        3.70275614914,
        9437.7629348870,
        0.00000014304,
        3.41117857525,
        10213.2855462110
    )
    private val E21 = doubleArrayOf(
        0.00000009030,
        3.89729061890,
        5507.5532386674,
        0.00000006177,
        1.73038850355,
        5223.6939198022
    )

    /** 离心率  */
    private val GXC_E = doubleArrayOf(0.016708634, -0.000042037, -0.0000001267)

    /** 近点  */
    private val GXC_P = doubleArrayOf(
        102.93735 / DEGREE_PER_RAD,
        1.71946 / DEGREE_PER_RAD,
        0.00046 / DEGREE_PER_RAD
    )

    /** 太平黄经  */
    private val GXC_L = doubleArrayOf(
        280.4664567 / DEGREE_PER_RAD,
        36000.76982779 / DEGREE_PER_RAD,
        0.0003032028 / DEGREE_PER_RAD,
        1 / 49931000 / DEGREE_PER_RAD,
        -1 / 153000000 / DEGREE_PER_RAD
    )

    /** 光行差常数  */
    private val GXC_K = 20.49552 / SECOND_PER_RAD

    /** 章动表  */
    private val ZD = doubleArrayOf(
        2.1824391966,
        -33.757045954,
        0.0000362262,
        3.7340E-08,
        -2.8793E-10,
        -171996.0,
        -1742.0,
        92025.0,
        89.0,
        3.5069406862,
        1256.663930738,
        0.0000105845,
        6.9813E-10,
        -2.2815E-10,
        -13187.0,
        -16.0,
        5736.0,
        -31.0,
        1.3375032491,
        16799.418221925,
        -0.0000511866,
        6.4626E-08,
        -5.3543E-10,
        -2274.0,
        -2.0,
        977.0,
        -5.0,
        4.3648783932,
        -67.514091907,
        0.0000724525,
        7.4681E-08,
        -5.7586E-10,
        2062.0,
        2.0,
        -895.0,
        5.0,
        0.0431251803,
        -628.301955171,
        0.0000026820,
        6.5935E-10,
        5.5705E-11,
        -1426.0,
        34.0,
        54.0,
        -1.0,
        2.3555557435,
        8328.691425719,
        0.0001545547,
        2.5033E-07,
        -1.1863E-09,
        712.0,
        1.0,
        -7.0,
        0.0,
        3.4638155059,
        1884.965885909,
        0.0000079025,
        3.8785E-11,
        -2.8386E-10,
        -517.0,
        12.0,
        224.0,
        -6.0,
        5.4382493597,
        16833.175267879,
        -0.0000874129,
        2.7285E-08,
        -2.4750E-10,
        -386.0,
        -4.0,
        200.0,
        0.0,
        3.6930589926,
        25128.109647645,
        0.0001033681,
        3.1496E-07,
        -1.7218E-09,
        -301.0,
        0.0,
        129.0,
        -1.0,
        3.5500658664,
        628.361975567,
        0.0000132664,
        1.3575E-09,
        -1.7245E-10,
        217.0,
        -5.0,
        -95.0,
        3.0
    )

    /** 农历年  */
    private var year = 0

    /** 农历月，闰月为负，即闰2月=-2  */
    private var month = 0

    /** 农历日  */
    private var day = 0

    /** 对应阳历  */
    private var solar: Solar

    /** 相对于基准日的偏移天数  */
    private var dayOffset = 0

    /** 时对应的天干下标，0-9  */
    private var timeGanIndex = 0

    /** 时对应的地支下标，0-11  */
    private var timeZhiIndex = 0

    /** 日对应的天干下标，0-9  */
    private var dayGanIndex = 0

    /** 日对应的地支下标，0-11  */
    private var dayZhiIndex = 0

    /** 日对应的天干下标（最精确的，供八字用，晚子时算第二天），0-9  */
    private var dayGanIndexExact = 0

    /** 日对应的地支下标（最精确的，供八字用，晚子时算第二天），0-11  */
    private var dayZhiIndexExact = 0

    /** 月对应的天干下标（以节交接当天起算），0-9  */
    private var monthGanIndex = 0

    /** 月对应的地支下标（以节交接当天起算），0-11  */
    private var monthZhiIndex = 0

    /** 月对应的天干下标（最精确的，供八字用，以节交接时刻起算），0-9  */
    private var monthGanIndexExact = 0

    /** 月对应的地支下标（最精确的，供八字用，以节交接时刻起算），0-11  */
    private var monthZhiIndexExact = 0

    /** 年对应的天干下标（国标，以正月初一为起点），0-9  */
    private var yearGanIndex = 0

    /** 年对应的地支下标（国标，以正月初一为起点），0-11  */
    private var yearZhiIndex = 0

    /** 年对应的天干下标（月干计算用，以立春为起点），0-9  */
    private var yearGanIndexByLiChun = 0

    /** 年对应的地支下标（月支计算用，以立春为起点），0-11  */
    private var yearZhiIndexByLiChun = 0

    /** 年对应的天干下标（最精确的，供八字用，以立春交接时刻为起点），0-9  */
    private var yearGanIndexExact = 0

    /** 年对应的地支下标（最精确的，供八字用，以立春交接时刻为起点），0-11  */
    private var yearZhiIndexExact = 0

    /** 周下标，0-6  */
    private var weekIndex = 0

    /** 阳历小时  */
    private var hour = 0

    /** 阳历分钟  */
    private var minute = 0

    /** 阳历秒钟  */
    private var second = 0

    /** 24节气表（对应阳历的准确时刻）  */
    private val jieQi: MutableMap<String, Solar> = LinkedHashMap()

    /**
     * 默认使用当前日期初始化
     */
    constructor() : this(Date())

    /**
     * 通过农历年月日初始化
     *
     * @param lunarYear 年（农历）
     * @param lunarMonth 月（农历），1到12，闰月为负，即闰2月=-2
     * @param lunarDay 日（农历），1到30
     */
    constructor(
        lunarYear: Int,
        lunarMonth: Int,
        lunarDay: Int
    ) : this(lunarYear, lunarMonth, lunarDay, 0, 0, 0)

    /**
     * 通过农历年月日时初始化
     *
     * @param lunarYear 年（农历）
     * @param lunarMonth 月（农历），1到12，闰月为负，即闰2月=-2
     * @param lunarDay 日（农历），1到30
     * @param hour 小时（阳历）
     * @param minute 分钟（阳历）
     * @param second 秒钟（阳历）
     */
    constructor(
        lunarYear: Int,
        lunarMonth: Int,
        lunarDay: Int,
        hour: Int,
        minute: Int,
        second: Int
    ) {
        val m = abs(lunarMonth)
        require(!(m < 1 || m > 12)) { "lunar month must between 1 and 12, or negative" }
        if (lunarMonth < 0) {
            val leapMonth: Int = LunarUtil.getLeapMonth(lunarYear)
            require(leapMonth != 0) {
                String.format(
                    "no leap month in lunar year %d",
                    lunarYear
                )
            }
            require(leapMonth == m) {
                String.format(
                    "leap month is %d in lunar year %d",
                    leapMonth,
                    lunarYear
                )
            }
        }
        require(!(lunarDay < 1 || lunarDay > 30)) { "lunar day must between 1 and 30" }
        val days: Int = LunarUtil.getDaysOfMonth(lunarYear, lunarMonth)
        require(lunarDay <= days) {
            String.format(
                "only %d days in lunar year %d month %d",
                days,
                lunarYear,
                lunarMonth
            )
        }
        year = lunarYear
        month = lunarMonth
        day = lunarDay
        this.hour = hour
        this.minute = minute
        this.second = second
        dayOffset = LunarUtil.computeAddDays(year, month, day)
        solar = toSolar()
        compute()
    }

    /**
     * 通过阳历日期初始化
     * @param date 阳历日期
     */
    constructor(date: Date) {
        solar = Solar(date)
        val y: Int = solar.getYear()
        val m: Int = solar.getMonth()
        val d: Int = solar.getDay()
        val startYear: Int
        val startMonth: Int
        val startDay: Int
        var lunarYear: Int
        var lunarMonth: Int
        var lunarDay: Int
        if (y < 2000) {
            startYear = SolarUtil.BASE_YEAR
            startMonth = SolarUtil.BASE_MONTH
            startDay = SolarUtil.BASE_DAY
            lunarYear = LunarUtil.BASE_YEAR
            lunarMonth = LunarUtil.BASE_MONTH
            lunarDay = LunarUtil.BASE_DAY
        } else {
            startYear = SolarUtil.BASE_YEAR + 99
            startMonth = 1
            startDay = 1
            lunarYear = LunarUtil.BASE_YEAR + 99
            lunarMonth = 11
            lunarDay = 25
        }
        var diff = 0
        for (i in startYear until y) {
            diff += 365
            if (SolarUtil.isLeapYear(i)) {
                diff += 1
            }
        }
        for (i in startMonth until m) {
            diff += SolarUtil.getDaysOfMonth(y, i)
        }
        diff += d - startDay
        lunarDay += diff
        var lastDate: Int = LunarUtil.getDaysOfMonth(lunarYear, lunarMonth)
        while (lunarDay > lastDate) {
            lunarDay -= lastDate
            lunarMonth = LunarUtil.nextMonth(lunarYear, lunarMonth)
            if (lunarMonth == 1) {
                lunarYear++
            }
            lastDate = LunarUtil.getDaysOfMonth(lunarYear, lunarMonth)
        }
        year = lunarYear
        month = lunarMonth
        day = lunarDay
        hour = solar.getHour()
        minute = solar.getMinute()
        second = solar.getSecond()
        dayOffset = LunarUtil.computeAddDays(year, month, day)
        compute()
    }

    /**
     * 计算节气表（冬至的太阳黄经是-90度或270度）
     */
    private fun computeJieQi() {
        //儒略日，冬至在阳历上一年，所以这里多减1年以从去年开始
        val jd = 365.2422 * (solar.getYear() - 2001)
        var i = 0
        val j = JIE_QI.size
        while (i < j) {
            val t = calJieQi(jd + i * 15.2, i * 15 - 90.toDouble()) + Solar.J2000 + 8.0 / 24
            jieQi[JIE_QI[i]] = Solar.fromJulianDay(t)
            i++
        }
    }

    /**
     * 计算干支纪年
     */
    private fun computeYear() {
        yearGanIndex = (year + LunarUtil.BASE_YEAR_GANZHI_INDEX) % 10
        yearZhiIndex = (year + LunarUtil.BASE_YEAR_GANZHI_INDEX) % 12

        //以立春作为新一年的开始的干支纪年
        var g = yearGanIndex
        var z = yearZhiIndex

        //精确的干支纪年，以立春交接时刻为准
        var gExact = yearGanIndex
        var zExact = yearZhiIndex
        if (year == solar.getYear()) {
            //获取立春的阳历时刻
            val liChun = jieQi["立春"]
            //立春日期判断
            if (solar.toYmd() < liChun?.toYmd() ?: "") {
                g--
                if (g < 0) {
                    g += 10
                }
                z--
                if (z < 0) {
                    z += 12
                }
            }
            //立春交接时刻判断
            if (solar.toYmdHms() < liChun?.toYmdHms() ?: "") {
                gExact--
                if (gExact < 0) {
                    gExact += 10
                }
                zExact--
                if (zExact < 0) {
                    zExact += 12
                }
            }
        }
        yearGanIndexByLiChun = g
        yearZhiIndexByLiChun = z
        yearGanIndexExact = gExact
        yearZhiIndexExact = zExact
    }

    /**
     * 干支纪月计算
     */
    private fun computeMonth() {
        var start: Solar? = null
        var end: Solar?
        //干偏移值（以立春当天起算）
        val gOffset = (yearGanIndexByLiChun % 5 + 1) * 2 % 10
        //干偏移值（以立春交接时刻起算）
        val gOffsetExact = (yearGanIndexExact % 5 + 1) * 2 % 10

        //序号：大雪到小寒之间-2，小寒到立春之间-1，立春之后0
        var index = -2
        for (jie in LunarUtil.JIE) {
            end = jieQi[jie]
            val ymd = solar.toYmd()
            val symd = start?.toYmd() ?: ymd
            val eymd = end?.toYmd() ?: ""
            if (ymd >= symd && ymd < eymd) {
                break
            }
            start = end
            index++
        }
        if (index < 0) {
            index += 12
        }
        monthGanIndex = (index + gOffset) % 10
        monthZhiIndex = (index + LunarUtil.BASE_MONTH_ZHI_INDEX) % 12

        //序号：大雪到小寒之间-2，小寒到立春之间-1，立春之后0
        var indexExact = -2
        for (jie in LunarUtil.JIE) {
            end = jieQi[jie]
            val time = solar.toYmdHms()
            val stime = start?.toYmdHms() ?: time
            val etime = end?.toYmdHms() ?: ""
            if (time >= stime && time < etime) {
                break
            }
            start = end
            indexExact++
        }
        if (indexExact < 0) {
            indexExact += 12
        }
        monthGanIndexExact = (indexExact + gOffsetExact) % 10
        monthZhiIndexExact = (indexExact + LunarUtil.BASE_MONTH_ZHI_INDEX) % 12
    }

    /**
     * 干支纪日计算
     */
    private fun computeDay() {
        val addDays: Int = (dayOffset + LunarUtil.BASE_DAY_GANZHI_INDEX) % 60
        dayGanIndex = addDays % 10
        dayZhiIndex = addDays % 12
        var dayGanExact = dayGanIndex
        var dayZhiExact = dayZhiIndex

        // 晚子时（夜子/子夜）应算作第二天
        val hm =
            (if (hour < 10) "0" else "") + hour + ":" + (if (minute < 10) "0" else "") + minute
        if (hm >= "23:00" && hm <= "23:59") {
            dayGanExact++
            if (dayGanExact >= 10) {
                dayGanExact -= 10
            }
            dayZhiExact++
            if (dayZhiExact >= 12) {
                dayZhiExact -= 12
            }
        }
        dayGanIndexExact = dayGanExact
        dayZhiIndexExact = dayZhiExact
    }

    /**
     * 干支纪时计算
     */
    private fun computeTime() {
        val hm =
            (if (hour < 10) "0" else "") + hour + ":" + (if (minute < 10) "0" else "") + minute
        timeZhiIndex = LunarUtil.getTimeZhiIndex(hm)
        timeGanIndex = (dayGanIndexExact % 5 * 2 + timeZhiIndex) % 10
    }

    /**
     * 星期计算
     */
    private fun computeWeek() {
        weekIndex = (dayOffset + LunarUtil.BASE_WEEK_INDEX) % 7
    }

    private fun compute() {
        computeJieQi()
        computeYear()
        computeMonth()
        computeDay()
        computeTime()
        computeWeek()
    }

    /**
     * 通过指定阳历日期获取农历
     *
     * @param date 阳历日期
     * @return 农历
     */
    fun fromDate(date: Date): Lunar {
        return Lunar(date)
    }

    /**
     * 通过指定农历年月日获取农历
     *
     * @param lunarYear 年（农历）
     * @param lunarMonth 月（农历），1到12，闰月为负，即闰2月=-2
     * @param lunarDay 日（农历），1到31
     * @return 农历
     */
    fun fromYmd(lunarYear: Int, lunarMonth: Int, lunarDay: Int): Lunar {
        return Lunar(lunarYear, lunarMonth, lunarDay)
    }

    /**
     * 通过指定农历年月日获取农历
     *
     * @param lunarYear 年（农历）
     * @param lunarMonth 月（农历），1到12，闰月为负，即闰2月=-2
     * @param lunarDay 日（农历），1到31
     * @param hour 小时（阳历）
     * @param minute 分钟（阳历）
     * @param second 秒钟（阳历）
     * @return 农历
     */
    fun fromYmdHms(
        lunarYear: Int,
        lunarMonth: Int,
        lunarDay: Int,
        hour: Int,
        minute: Int,
        second: Int
    ): Lunar {
        return Lunar(lunarYear, lunarMonth, lunarDay, hour, minute, second)
    }

    /**
     * 获取年份的天干（以正月初一作为新年的开始）
     *
     * @return 天干，如辛
     */
    fun getYearGan(): String {
        return LunarUtil.GAN[yearGanIndex + 1]
    }

    /**
     * 获取年份的天干（以立春当天作为新年的开始）
     *
     * @return 天干，如辛
     */
    fun getYearGanByLiChun(): String {
        return LunarUtil.GAN[yearGanIndexByLiChun + 1]
    }

    /**
     * 获取最精确的年份天干（以立春交接的时刻作为新年的开始）
     *
     * @return 天干，如辛
     */
    fun getYearGanExact(): String {
        return LunarUtil.GAN[yearGanIndexExact + 1]
    }

    /**
     * 获取年份的地支（以正月初一作为新年的开始）
     *
     * @return 地支，如亥
     */
    fun getYearZhi(): String {
        return LunarUtil.ZHI[yearZhiIndex + 1]
    }

    /**
     * 获取年份的地支（以立春当天作为新年的开始）
     *
     * @return 地支，如亥
     */
    fun getYearZhiByLiChun(): String {
        return LunarUtil.ZHI[yearZhiIndexByLiChun + 1]
    }

    /**
     * 获取最精确的年份地支（以立春交接的时刻作为新年的开始）
     *
     * @return 地支，如亥
     */
    fun getYearZhiExact(): String {
        return LunarUtil.ZHI[yearZhiIndexExact + 1]
    }

    /**
     * 获取干支纪年（年柱）（以正月初一作为新年的开始）
     * @return 年份的干支（年柱），如辛亥
     */
    fun getYearInGanZhi(): String {
        return getYearGan() + getYearZhi()
    }

    /**
     * 获取干支纪年（年柱）（以立春当天作为新年的开始）
     * @return 年份的干支（年柱），如辛亥
     */
    fun getYearInGanZhiByLiChun(): String {
        return getYearGanByLiChun() + getYearZhiByLiChun()
    }

    /**
     * 获取干支纪年（年柱）（以立春交接的时刻作为新年的开始）
     * @return 年份的干支（年柱），如辛亥
     */
    fun getYearInGanZhiExact(): String {
        return getYearGanExact() + getYearZhiExact()
    }

    /**
     * 获取干支纪月（月柱）（以节交接当天起算）
     *
     * 月天干口诀：甲己丙寅首，乙庚戊寅头。丙辛从庚寅，丁壬壬寅求，戊癸甲寅居，周而复始流。
     *
     * 月地支：正月起寅
     *
     * @return 干支纪月（月柱），如己卯
     */
    fun getMonthInGanZhi(): String {
        return getMonthGan() + getMonthZhi()
    }

    /**
     * 获取精确的干支纪月（月柱）（以节交接时刻起算）
     *
     * 月天干口诀：甲己丙寅首，乙庚戊寅头。丙辛从庚寅，丁壬壬寅求，戊癸甲寅居，周而复始流。
     *
     * 月地支：正月起寅
     *
     * @return 干支纪月（月柱），如己卯
     */
    fun getMonthInGanZhiExact(): String {
        return getMonthGanExact() + getMonthZhiExact()
    }

    /**
     * 获取月天干（以节交接当天起算）
     * @return 月天干，如己
     */
    fun getMonthGan(): String {
        return LunarUtil.GAN[monthGanIndex + 1]
    }

    /**
     * 获取精确的月天干（以节交接时刻起算）
     * @return 月天干，如己
     */
    fun getMonthGanExact(): String {
        return LunarUtil.GAN[monthGanIndexExact + 1]
    }

    /**
     * 获取月地支（以节交接当天起算）
     * @return 月地支，如卯
     */
    fun getMonthZhi(): String {
        return LunarUtil.ZHI[monthZhiIndex + 1]
    }

    /**
     * 获取精确的月地支（以节交接时刻起算）
     * @return 月地支，如卯
     */
    fun getMonthZhiExact(): String {
        return LunarUtil.ZHI[monthZhiIndexExact + 1]
    }

    /**
     * 获取干支纪日（日柱）
     *
     * @return 干支纪日（日柱），如己卯
     */
    fun getDayInGanZhi(): String {
        return getDayGan() + getDayZhi()
    }

    /**
     * 获取干支纪日（日柱，晚子时算第二天）
     *
     * @return 干支纪日（日柱），如己卯
     */
    fun getDayInGanZhiExact(): String {
        return getDayGanExact() + getDayZhiExact()
    }

    /**
     * 获取日天干
     *
     * @return 日天干，如甲
     */
    fun getDayGan(): String {
        return LunarUtil.GAN[dayGanIndex + 1]
    }

    /**
     * 获取日天干（晚子时算第二天）
     *
     * @return 日天干，如甲
     */
    fun getDayGanExact(): String {
        return LunarUtil.GAN[dayGanIndexExact + 1]
    }

    /**
     * 获取日地支
     *
     * @return 日地支，如卯
     */
    fun getDayZhi(): String {
        return LunarUtil.ZHI[dayZhiIndex + 1]
    }

    /**
     * 获取日地支（晚子时算第二天）
     *
     * @return 日地支，如卯
     */
    fun getDayZhiExact(): String {
        return LunarUtil.ZHI[dayZhiIndexExact + 1]
    }

    /**
     * 获取年生肖（以正月初一起算）
     *
     * @return 年生肖，如虎
     */
    fun getYearShengXiao(): String {
        return LunarUtil.SHENGXIAO[yearZhiIndex + 1]
    }

    /**
     * 获取年生肖（以立春当天起算）
     *
     * @return 年生肖，如虎
     */
    fun getYearShengXiaoByLiChun(): String {
        return LunarUtil.SHENGXIAO[yearZhiIndexByLiChun + 1]
    }

    /**
     * 获取精确的年生肖（以立春交接时刻起算）
     *
     * @return 年生肖，如虎
     */
    fun getYearShengXiaoExact(): String {
        return LunarUtil.SHENGXIAO[yearZhiIndexExact + 1]
    }

    /**
     * 获取月生肖
     *
     * @return 月生肖，如虎
     */
    fun getMonthShengXiao(): String {
        return LunarUtil.SHENGXIAO[monthZhiIndex + 1]
    }

    /**
     * 获取日生肖
     *
     * @return 日生肖，如虎
     */
    fun getDayShengXiao(): String {
        return LunarUtil.SHENGXIAO[dayZhiIndex + 1]
    }

    /**
     * 获取时辰生肖
     *
     * @return 时辰生肖，如虎
     */
    fun getTimeShengXiao(): String {
        return LunarUtil.SHENGXIAO[timeZhiIndex + 1]
    }

    /**
     * 获取中文的年
     *
     * @return 中文年，如二零零一
     */
    fun getYearInChinese(): String {
        val y = year.toString() + ""
        val s = StringBuilder()
        var i = 0
        val j = y.length
        while (i < j) {
            s.append(LunarUtil.NUMBER[y[i] - '0'])
            i++
        }
        return s.toString()
    }

    /**
     * 获取中文的月
     *
     * @return 中文月，如正
     */
    fun getMonthInChinese(): String {
        return (if (month < 0) "闰" else "") + LunarUtil.MONTH[abs(month)]
    }

    /**
     * 获取中文日
     *
     * @return 中文日，如初一
     */
    fun getDayInChinese(): String {
        return LunarUtil.DAY[day]
    }

    /**
     * 获取时辰（地支）
     * @return 时辰（地支）
     */
    fun getTimeZhi(): String {
        return LunarUtil.ZHI[timeZhiIndex + 1]
    }

    /**
     * 获取时辰（天干）
     * @return 时辰（天干）
     */
    fun getTimeGan(): String {
        return LunarUtil.GAN[timeGanIndex + 1]
    }

    /**
     * 获取时辰干支（时柱），支持早子时和晚子时
     * @return 时辰干支（时柱）
     */
    fun getTimeInGanZhi(): String {
        return getTimeGan() + getTimeZhi()
    }

    /**
     * 获取季节
     * @return 农历季节
     */
    fun getSeason(): String {
        return LunarUtil.SEASON[abs(month)]
    }

    private fun mrad(rad: Double): Double {
        var rad1 = rad
        val pi2 = 2 * Math.PI
        rad1 %= pi2
        return if (rad1 < 0) rad1 + pi2 else rad1
    }

    private fun gxc(t: Double, pos: DoubleArray) {
        val t1 = t / 36525
        val t2 = t1 * t1
        val t3 = t2 * t1
        val t4 = t3 * t1
        val l =
            GXC_L[0] + GXC_L[1] * t1 + GXC_L[2] * t2 + GXC_L[3] * t3 + GXC_L[4] * t4
        val p = GXC_P[0] + GXC_P[1] * t1 + GXC_P[2] * t2
        val e = GXC_E[0] + GXC_E[1] * t1 + GXC_E[2] * t2
        val dl = l - pos[0]
        val dp = p - pos[0]
        pos[0] -= GXC_K * (cos(dl) - e * cos(dp)) / cos(pos[1])
        pos[1] -= GXC_K * sin(pos[1]) * (sin(dl) - e * sin(dp))
        pos[0] = mrad(pos[0])
    }

    private fun enn(f: DoubleArray, ennt: Double): Double {
        var v = 0.0
        var i = 0
        val j = f.size
        while (i < j) {
            v += f[i] * cos(f[i + 1] + ennt * f[i + 2])
            i += 3
        }
        return v
    }

    /**
     * 计算日心坐标中地球的位置
     * @param t 儒略日
     * @return 地球坐标
     */
    private fun calEarth(t: Double): DoubleArray {
        val t1 = t / 365250
        val r = DoubleArray(2)
        val t2 = t1 * t1
        val t3 = t2 * t1
        val t4 = t3 * t1
        val t5 = t4 * t1
        r[0] = mrad(
            enn(E10, t1) + enn(E11, t1) * t1 + enn(E12, t1) * t2 + enn(
                E13,
                t1
            ) * t3 + enn(E14, t1) * t4 + enn(E15, t1) * t5
        )
        r[1] = enn(E20, t1) + enn(E21, t1) * t1
        return r
    }

    /**
     * 计算黄经章动
     * @param t J2000起算儒略日数
     * @return 黄经章动
     */
    private fun hjzd(t: Double): Double {
        var lon = 0.0
        val t1 = t / 36525
        var c: Double
        val t2 = t1 * t1
        val t3 = t2 * t1
        val t4 = t3 * t1
        var i = 0
        val j = ZD.size
        while (i < j) {
            c =
                ZD[i] + ZD[i + 1] * t1 + ZD[i + 2] * t2 + ZD[i + 3] * t3 + ZD[i + 4] * t4
            lon += (ZD[i + 5] + ZD[i + 6] * t1 / 10) * sin(c)
            i += 9
        }
        lon /= SECOND_PER_RAD * 10000
        return lon
    }

    /**
     * 地心坐标中的日月位置计算（计算t时刻太阳黄经与角度的差）
     * @param t J2000起算儒略日数
     * @param rad 弧度
     * @return 角度差
     */
    private fun calRad(t: Double, rad: Double): Double {
        // 计算太阳真位置(先算出日心坐标中地球的位置)
        val pos = calEarth(t)
        pos[0] += Math.PI
        // 转为地心坐标
        pos[1] = -pos[1]
        // 补周年光行差
        gxc(t, pos)
        // 补黄经章动
        pos[0] += hjzd(t)
        return mrad(rad - pos[0])
    }

    /**
     * 太阳黄经达某角度的时刻计算(用于节气计算)
     * @param t1 J2000起算儒略日数
     * @param degree 角度
     * @return 时刻
     */
    private fun calJieQi(t1: Double, degree: Double): Double {
        // 对于节气计算,应满足t在t1到t1+360天之间,对于Y年第n个节气(n=0是春分),t1可取值Y*365.2422+n*15.2
        var t11 = t1
        var t2 = t11
        var t = 0.0
        var v: Double
        // 在t1到t2范围内求解(范气360天范围),结果置于t
        t2 += 360.0
        // 待搜索目标角
        val rad = degree * RAD_PER_DEGREE
        // 利用截弦法计算
        // v1,v2为t1,t2时对应的黄经
        var v1 = calRad(t11, rad)
        var v2 = calRad(t2, rad)
        // 减2pi作用是将周期性角度转为连续角度
        if (v1 < v2) {
            v2 -= 2 * Math.PI
        }
        // k是截弦的斜率
        var k = 1.0
        var k2: Double
        // 快速截弦求根,通常截弦三四次就已达所需精度
        for (i in 0..9) {
            // 算出斜率
            k2 = (v2 - v1) / (t2 - t11)
            // 差商可能为零,应排除
            if (abs(k2) > 1e-15) {
                k = k2
            }
            t = t11 - v1 / k
            // 直线逼近法求根(直线方程的根)
            v = calRad(t, rad)
            // 一次逼近后,v1就已接近0,如果很大,则应减1周
            if (v > 1) {
                v -= 2 * Math.PI
            }
            // 已达精度
            if (abs(v) < 1e-8) {
                break
            }
            t11 = t2
            v1 = v2
            t2 = t
            // 下一次截弦
            v2 = v
        }
        return t
    }

    /**
     * 获取节
     *
     * @return 节
     */
    fun getJie(): String {
        for (jie in LunarUtil.JIE) {
            val d = jieQi[jie]
            if (d?.getYear() == solar.getYear() && d.getMonth() == solar.getMonth() && d.getDay() == solar.getDay()) {
                return jie
            }
        }
        return ""
    }

    /**
     * 获取气
     *
     * @return 气
     */
    fun getQi(): String {
        for (qi in LunarUtil.QI) {
            val d = jieQi[qi]
            if (d?.getYear() == solar.getYear() && d.getMonth() == solar.getMonth() && d.getDay() == solar.getDay()) {
                return qi
            }
        }
        return ""
    }

    /**
     * 获取星期，0代表周日，1代表周一
     *
     * @return 0123456
     */
    fun getWeek(): Int {
        return weekIndex
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
     * 获取宿
     *
     * @return 宿
     */
    fun getXiu(): String {
        return LunarUtil.XIU[getDayZhi() + getWeek()] ?: ""
    }

    /**
     * 获取宿吉凶
     *
     * @return 吉/凶
     */
    fun getXiuLuck(): String {
        return LunarUtil.XIU_LUCK[getXiu()] ?: ""
    }

    /**
     * 获取宿歌诀
     *
     * @return 宿歌诀
     */
    fun getXiuSong(): String {
        return LunarUtil.XIU_SONG[getXiu()] ?: ""
    }

    /**
     * 获取政
     *
     * @return 政
     */
    fun getZheng(): String {
        return LunarUtil.ZHENG[getXiu()] ?: ""
    }

    /**
     * 获取动物
     * @return 动物
     */
    fun getAnimal(): String {
        return LunarUtil.ANIMAL[getXiu()] ?: ""
    }

    /**
     * 获取宫
     * @return 宫
     */
    fun getGong(): String {
        return LunarUtil.GONG[getXiu()] ?: ""
    }

    /**
     * 获取兽
     * @return 兽
     */
    fun getShou(): String {
        return LunarUtil.SHOU[getGong()] ?: ""
    }

    /**
     * 获取节日，有可能一天会有多个节日
     *
     * @return 节日列表，如春节
     */
    fun getFestivals(): List<String> {
        val l: MutableList<String> = ArrayList()
        val f = LunarUtil.FESTIVAL["$month-$day"]
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
        val fs = LunarUtil.OTHER_FESTIVAL["$month-$day"]
        if (null != fs) {
            l.addAll(fs)
        }
        return l
    }

    /**
     * 转换为阳历日期
     *
     * @return 阳历日期
     */
    private fun toSolar(): Solar {
        val c: Calendar = Calendar.getInstance()
        c.set(
            SolarUtil.BASE_YEAR,
            SolarUtil.BASE_MONTH - 1,
            SolarUtil.BASE_DAY,
            hour,
            minute,
            second
        )
        c.add(Calendar.DATE, dayOffset)
        return Solar(c)
    }

    /**
     * 获取彭祖百忌天干
     * @return 彭祖百忌天干
     */
    fun getPengZuGan(): String {
        return LunarUtil.PENGZU_GAN[dayGanIndex + 1]
    }

    /**
     * 获取彭祖百忌地支
     * @return 彭祖百忌地支
     */
    fun getPengZuZhi(): String {
        return LunarUtil.PENGZU_ZHI[dayZhiIndex + 1]
    }

    /**
     * 获取喜神方位
     * @return 喜神方位，如艮
     */
    fun getPositionXi(): String {
        return LunarUtil.POSITION_XI[dayGanIndex + 1]
    }

    /**
     * 获取喜神方位描述
     * @return 喜神方位描述，如东北
     */
    fun getPositionXiDesc(): String? {
        return LunarUtil.POSITION_DESC[getPositionXi()]
    }

    /**
     * 获取阳贵神方位
     * @return 阳贵神方位，如艮
     */
    fun getPositionYangGui(): String {
        return LunarUtil.POSITION_YANG_GUI[dayGanIndex + 1]
    }

    /**
     * 获取阳贵神方位描述
     * @return 阳贵神方位描述，如东北
     */
    fun getPositionYangGuiDesc(): String? {
        return LunarUtil.POSITION_DESC[getPositionYangGui()]
    }

    /**
     * 获取阴贵神方位
     * @return 阴贵神方位，如艮
     */
    fun getPositionYinGui(): String {
        return LunarUtil.POSITION_YIN_GUI[dayGanIndex + 1]
    }

    /**
     * 获取阴贵神方位描述
     * @return 阴贵神方位描述，如东北
     */
    fun getPositionYinGuiDesc(): String {
        return LunarUtil.POSITION_DESC[getPositionYinGui()] ?: ""
    }

    /**
     * 获取福神方位
     * @return 福神方位，如艮
     */
    fun getPositionFu(): String {
        return LunarUtil.POSITION_FU[dayGanIndex + 1]
    }

    /**
     * 获取福神方位描述
     * @return 福神方位描述，如东北
     */
    fun getPositionFuDesc(): String {
        return LunarUtil.POSITION_DESC[getPositionFu()] ?: ""
    }

    /**
     * 获取财神方位
     * @return 财神方位，如艮
     */
    fun getPositionCai(): String {
        return LunarUtil.POSITION_CAI[dayGanIndex + 1]
    }

    /**
     * 获取财神方位描述
     * @return 财神方位描述，如东北
     */
    fun getPositionCaiDesc(): String {
        return LunarUtil.POSITION_DESC[getPositionCai()] ?: ""
    }

    /**
     * 获取年纳音
     * @return 年纳音，如剑锋金
     */
    fun getYearNaYin(): String {
        return LunarUtil.NAYIN[getYearInGanZhi()] ?: ""
    }

    /**
     * 获取月纳音
     * @return 月纳音，如剑锋金
     */
    fun getMonthNaYin(): String {
        return LunarUtil.NAYIN[getMonthInGanZhi()] ?: ""
    }

    /**
     * 获取日纳音
     * @return 日纳音，如剑锋金
     */
    fun getDayNaYin(): String {
        return LunarUtil.NAYIN[getDayInGanZhi()] ?: ""
    }

    /**
     * 获取时辰纳音
     * @return 时辰纳音，如剑锋金
     */
    fun getTimeNaYin(): String {
        return LunarUtil.NAYIN[getTimeInGanZhi()] ?: ""
    }

    /**
     * 获取八字，男性也称乾造，女性也称坤造（以立春交接时刻作为新年的开始）
     * @return 八字（男性也称乾造，女性也称坤造）
     */
    fun getBaZi(): List<String> {
        val l: MutableList<String> = ArrayList(4)
        l.add(getYearInGanZhiExact())
        l.add(getMonthInGanZhiExact())
        l.add(getDayInGanZhiExact())
        l.add(getTimeInGanZhi())
        return l
    }

    /**
     * 获取八字五行
     * @return 八字五行
     */
    fun getBaZiWuXing(): List<String> {
        val baZi = getBaZi()
        val l: MutableList<String> = ArrayList(baZi.size)
        for (ganZhi in baZi) {
            val gan = ganZhi.substring(0, 1)
            val zhi = ganZhi.substring(1)
            l.add(LunarUtil.WU_XING_GAN[gan] + LunarUtil.WU_XING_ZHI[zhi])
        }
        return l
    }

    /**
     * 获取八字纳音
     * @return 八字纳音
     */
    fun getBaZiNaYin(): List<String> {
        val baZi = getBaZi()
        val l: MutableList<String> = ArrayList(baZi.size)
        for (ganZhi in baZi) {
            l.add(LunarUtil.NAYIN[ganZhi] ?: "")
        }
        return l
    }

    /**
     * 获取八字天干十神，日柱十神为日主，其余三柱根据天干十神表查询
     * @return 八字天干十神
     */
    fun getBaZiShiShenGan(): List<String> {
        val baZi = getBaZi()
        val yearGan = baZi[0].substring(0, 1)
        val monthGan = baZi[1].substring(0, 1)
        val dayGan = baZi[2].substring(0, 1)
        val timeGan = baZi[3].substring(0, 1)
        val l: MutableList<String> = ArrayList(baZi.size)
        l.add(LunarUtil.SHI_SHEN_GAN[dayGan + yearGan] ?: "")
        l.add(LunarUtil.SHI_SHEN_GAN[dayGan + monthGan] ?: "")
        l.add("日主")
        l.add(LunarUtil.SHI_SHEN_GAN[dayGan + timeGan] ?: "")
        return l
    }

    /**
     * 获取八字地支十神，根据地支十神表查询
     * @return 八字地支十神
     */
    fun getBaZiShiShenZhi(): List<String> {
        val baZi = getBaZi()
        val dayGan = baZi[2].substring(0, 1)
        val l: MutableList<String> = ArrayList(baZi.size)
        for (ganZhi in baZi) {
            val zhi = ganZhi.substring(1)
            l.add(
                LunarUtil.SHI_SHEN_ZHI[dayGan + zhi + LunarUtil.ZHI_HIDE_GAN[zhi]?.get(0)]
                    ?: ""
            )
        }
        return l
    }

    /**
     * 获取十二执星：建、除、满、平、定、执、破、危、成、收、开、闭。当月支与日支相同即为建，依次类推
     * @return 执星
     */
    fun getZhiXing(): String {
        var offset = dayZhiIndex - monthZhiIndex
        if (offset < 0) {
            offset += 12
        }
        return LunarUtil.ZHI_XING[offset + 1]
    }

    /**
     * 获取值日天神
     * @return 值日天神
     */
    fun getDayTianShen(): String {
        val monthZhi = getMonthZhi()
        val offset = LunarUtil.ZHI_TIAN_SHEN_OFFSET[monthZhi] ?: 0
        return LunarUtil.TIAN_SHEN[(dayZhiIndex + offset) % 12 + 1]
    }

    /**
     * 获取值时天神
     * @return 值时天神
     */
    fun getTimeTianShen(): String {
        val dayZhi = getDayZhiExact()
        val offset = LunarUtil.ZHI_TIAN_SHEN_OFFSET[dayZhi] ?: 0
        return LunarUtil.TIAN_SHEN[(timeZhiIndex + offset) % 12 + 1]
    }

    /**
     * 获取值日天神类型：黄道/黑道
     * @return 值日天神类型：黄道/黑道
     */
    fun getDayTianShenType(): String {
        return LunarUtil.TIAN_SHEN_TYPE[getDayTianShen()] ?: ""
    }

    /**
     * 获取值时天神类型：黄道/黑道
     * @return 值时天神类型：黄道/黑道
     */
    fun getTimeTianShenType(): String {
        return LunarUtil.TIAN_SHEN_TYPE[getTimeTianShen()] ?: ""
    }

    /**
     * 获取值日天神吉凶
     * @return 吉/凶
     */
    fun getDayTianShenLuck(): String {
        return LunarUtil.TIAN_SHEN_TYPE_LUCK[getDayTianShenType()] ?: ""
    }

    /**
     * 获取值时天神吉凶
     * @return 吉/凶
     */
    fun getTimeTianShenLuck(): String {
        return LunarUtil.TIAN_SHEN_TYPE_LUCK[getTimeTianShenType()] ?: ""
    }

    /**
     * 获取逐日胎神方位
     * @return 逐日胎神方位
     */
    fun getDayPositionTai(): String {
        var offset = dayGanIndex - dayZhiIndex
        if (offset < 0) {
            offset += 12
        }
        return LunarUtil.POSITION_TAI_DAY[offset * 5 + dayGanIndex]
    }

    /**
     * 获取逐月胎神方位，闰月无
     * @return 逐月胎神方位
     */
    fun getMonthPositionTai(): String {
        return if (month < 0) {
            ""
        } else LunarUtil.POSITION_TAI_MONTH[month - 1]
    }

    /**
     * 获取每日宜，如果没有，返回["无"]
     * @return 宜
     */
    fun getDayYi(): List<String> {
        return LunarUtil.getDayYi(getMonthInGanZhiExact(), getDayInGanZhi())
    }

    /**
     * 获取每日忌，如果没有，返回["无"]
     * @return 忌
     */
    fun getDayJi(): List<String> {
        return LunarUtil.getDayJi(getMonthInGanZhiExact(), getDayInGanZhi())
    }

    /**
     * 获取日吉神（宜趋），如果没有，返回["无"]
     * @return 日吉神
     */
    fun getDayJiShen(): List<String> {
        return LunarUtil.getDayJiShen(getMonth(), getDayInGanZhi())
    }

    /**
     * 获取日凶煞（宜忌），如果没有，返回["无"]
     * @return 日凶煞
     */
    fun getDayXiongSha(): List<String> {
        return LunarUtil.getDayXiongSha(getMonth(), getDayInGanZhi())
    }

    /**
     * 获取日冲
     * @return 日冲，如申
     */
    fun getDayChong(): String {
        return LunarUtil.CHONG[dayZhiIndex + 1]
    }

    /**
     * 获取日煞
     * @return 日煞，如北
     */
    fun getDaySha(): String {
        return LunarUtil.SHA[getDayZhi()] ?: ""
    }

    /**
     * 获取日冲描述
     * @return 日冲描述，如(壬申)猴
     */
    fun getDayChongDesc(): String {
        return "(" + getDayChongGan() + getDayChong() + ")" + getDayChongShengXiao()
    }

    /**
     * 获取日冲生肖
     * @return 日冲生肖，如猴
     */
    fun getDayChongShengXiao(): String {
        val chong = getDayChong()
        var i = 0
        val j = LunarUtil.ZHI.size
        while (i < j) {
            if (LunarUtil.ZHI[i] == chong) {
                return LunarUtil.SHENGXIAO[i]
            }
            i++
        }
        return ""
    }

    /**
     * 获取无情之克的日冲天干
     * @return 无情之克的日冲天干，如甲
     */
    fun getDayChongGan(): String {
        return LunarUtil.CHONG_GAN[dayGanIndex + 1]
    }

    /**
     * 获取有情之克的日冲天干
     * @return 有情之克的日冲天干，如甲
     */
    fun getDayChongGanTie(): String {
        return LunarUtil.CHONG_GAN_TIE[dayGanIndex + 1]
    }

    /**
     * 获取时冲
     * @return 时冲，如申
     */
    fun getTimeChong(): String {
        return LunarUtil.CHONG[timeZhiIndex + 1]
    }

    /**
     * 获取时煞
     * @return 时煞，如北
     */
    fun getTimeSha(): String {
        return LunarUtil.SHA[getTimeZhi()] ?: ""
    }

    /**
     * 获取时冲生肖
     * @return 时冲生肖，如猴
     */
    fun getTimeChongShengXiao(): String {
        val chong = getTimeChong()
        var i = 0
        val j = LunarUtil.ZHI.size
        while (i < j) {
            if (LunarUtil.ZHI[i] == chong) {
                return LunarUtil.SHENGXIAO[i]
            }
            i++
        }
        return ""
    }

    /**
     * 获取时冲描述
     * @return 时冲描述，如(壬申)猴
     */
    fun getTimeChongDesc(): String {
        return "(" + getTimeChongGan() + getTimeChong() + ")" + getTimeChongShengXiao()
    }

    /**
     * 获取无情之克的时冲天干
     * @return 无情之克的时冲天干，如甲
     */
    fun getTimeChongGan(): String {
        return LunarUtil.CHONG_GAN[timeGanIndex + 1]
    }

    /**
     * 获取有情之克的时冲天干
     * @return 有情之克的时冲天干，如甲
     */
    fun getTimeChongGanTie(): String {
        return LunarUtil.CHONG_GAN_TIE[timeGanIndex + 1]
    }

    /**
     * 获取时辰宜，如果没有，返回["无"]
     * @return 宜
     */
    fun getTimeYi(): List<String> {
        return LunarUtil.getTimeYi(getDayInGanZhiExact(), getTimeInGanZhi())
    }

    /**
     * 获取时辰忌，如果没有，返回["无"]
     * @return 忌
     */
    fun getTimeJi(): List<String> {
        return LunarUtil.getTimeJi(getDayInGanZhiExact(), getTimeInGanZhi())
    }

    /**
     * 获取节气表（节气名称:阳历），节气交接时刻精确到秒，以冬至开头，按先后顺序排列
     * @return 节气表
     */
    fun getJieQiTable(): Map<String, Solar> {
        return jieQi
    }

    fun toFullString(): String {
        val s = StringBuilder()
        s.append(toString())
        s.append(" ")
        s.append(getYearInGanZhi())
        s.append("(")
        s.append(getYearShengXiao())
        s.append(")年 ")
        s.append(getMonthInGanZhi())
        s.append("(")
        s.append(getMonthShengXiao())
        s.append(")月 ")
        s.append(getDayInGanZhi())
        s.append("(")
        s.append(getDayShengXiao())
        s.append(")日 ")
        s.append(getTimeZhi())
        s.append("(")
        s.append(getTimeShengXiao())
        s.append(")时 纳音[")
        s.append(getYearNaYin())
        s.append(" ")
        s.append(getMonthNaYin())
        s.append(" ")
        s.append(getDayNaYin())
        s.append(" ")
        s.append(getTimeNaYin())
        s.append("] 星期")
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
        val jq = getJie() + getQi()
        if (jq.isNotEmpty()) {
            s.append(" [")
            s.append(jq)
            s.append("]")
        }
        s.append(" ")
        s.append(getGong())
        s.append("方")
        s.append(getShou())
        s.append(" 星宿[")
        s.append(getXiu())
        s.append(getZheng())
        s.append(getAnimal())
        s.append("](")
        s.append(getXiuLuck())
        s.append(") 彭祖百忌[")
        s.append(getPengZuGan())
        s.append(" ")
        s.append(getPengZuZhi())
        s.append("] 喜神方位[")
        s.append(getPositionXi())
        s.append("](")
        s.append(getPositionXiDesc())
        s.append(") 阳贵神方位[")
        s.append(getPositionYangGui())
        s.append("](")
        s.append(getPositionYangGuiDesc())
        s.append(") 阴贵神方位[")
        s.append(getPositionYinGui())
        s.append("](")
        s.append(getPositionYinGuiDesc())
        s.append(") 福神方位[")
        s.append(getPositionFu())
        s.append("](")
        s.append(getPositionFuDesc())
        s.append(") 财神方位[")
        s.append(getPositionCai())
        s.append("](")
        s.append(getPositionCaiDesc())
        s.append(") 冲[")
        s.append(getDayChongDesc())
        s.append("] 煞[")
        s.append(getDaySha())
        s.append("]")
        return s.toString()
    }

    override fun toString(): String {
        return getYearInChinese() + "年" + getMonthInChinese() + "月" + getDayInChinese()
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
     * @return 1到12，负数为闰月
     */
    fun getMonth(): Int {
        return month
    }

    /**
     * 获取日期
     *
     * @return 日期
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

    fun getSolar(): Solar {
        return solar
    }
}
package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil

/**
 * 描述：九星
 * 玄空九星、奇门九星都来源于北斗九星，九数、七色、五行、后天八卦方位都是相通的
 *
 * @author tiamosu
 * @date 2020/7/9.
 */
@Suppress("MemberVisibilityCanBePrivate", "PropertyName")
class NineStar(
    /** 序号，0到8  */
    private val index: Int
) {

    /** 九数  */
    val NUMBER = arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九")

    /** 七色  */
    val COLOR = arrayOf("白", "黒", "碧", "绿", "黄", "白", "赤", "白", "紫")

    /** 五行  */
    val WU_XING = arrayOf("水", "土", "木", "木", "土", "金", "金", "土", "火")

    /** 后天八卦方位  */
    val POSITION = arrayOf("坎", "坤", "震", "巽", "中", "乾", "兑", "艮", "离")

    /** 北斗九星  */
    val NAME_BEI_DOU = arrayOf("天枢", "天璇", "天玑", "天权", "玉衡", "开阳", "摇光", "洞明", "隐元")

    /** 玄空九星（玄空风水）  */
    val NAME_XUAN_KONG = arrayOf("贪狼", "巨门", "禄存", "文曲", "廉贞", "武曲", "破军", "左辅", "右弼")

    /** 奇门九星（奇门遁甲，也称天盘九星）  */
    val NAME_QI_MEN = arrayOf("天蓬", "天芮", "天冲", "天辅", "天禽", "天心", "天柱", "天任", "天英")

    /** 八门（奇门遁甲）  */
    val BA_MEN_QI_MEN = arrayOf("休", "死", "伤", "杜", "", "开", "惊", "生", "景")

    /** 太乙九神（太乙神数）  */
    val NAME_TAI_YI = arrayOf("太乙", "摄提", "轩辕", "招摇", "天符", "青龙", "咸池", "太阴", "天乙")

    /** 太乙九神对应类型  */
    val TYPE_TAI_YI = arrayOf("吉神", "凶神", "安神", "安神", "凶神", "吉神", "凶神", "吉神", "吉神")

    /** 太乙九神歌诀（太乙神数）  */
    val SONG_TAI_YI = arrayOf(
        "门中太乙明，星官号贪狼，赌彩财喜旺，婚姻大吉昌，出入无阻挡，参谒见贤良，此行三五里，黑衣别阴阳。",
        "门前见摄提，百事必忧疑，相生犹自可，相克祸必临，死门并相会，老妇哭悲啼，求谋并吉事，尽皆不相宜，只可藏隐遁，若动伤身疾。",
        "出入会轩辕，凡事必缠牵，相生全不美，相克更忧煎，远行多不利，博彩尽输钱，九天玄女法，句句不虚言。",
        "招摇号木星，当之事莫行，相克行人阻，阴人口舌迎，梦寐多惊惧，屋响斧自鸣，阴阳消息理，万法弗违情。",
        "五鬼为天符，当门阴女谋，相克无好事，行路阻中途，走失难寻觅，道逢有尼姑，此星当门值，万事有灾除。",
        "神光跃青龙，财气喜重重，投入有酒食，赌彩最兴隆，更逢相生旺，休言克破凶，见贵安营寨，万事总吉同。",
        "吾将为咸池，当之尽不宜，出入多不利，相克有灾情，赌彩全输尽，求财空手回，仙人真妙语，愚人莫与知，动用虚惊退，反复逆风吹。",
        "坐临太阴星，百祸不相侵，求谋悉成就，知交有觅寻，回风归来路，恐有殃伏起，密语中记取，慎乎莫轻行。",
        "迎来天乙星，相逢百事兴，运用和合庆，茶酒喜相迎，求谋并嫁娶，好合有天成，祸福如神验，吉凶甚分明。"
    )

    /** 吉凶（玄空风水）  */
    val LUCK_XUAN_KONG = arrayOf("吉", "凶", "凶", "吉", "凶", "吉", "凶", "吉", "吉")

    /** 吉凶（奇门遁甲）  */
    val LUCK_QI_MEN = arrayOf("大凶", "大凶", "小吉", "大吉", "大吉", "大吉", "小凶", "小吉", "小凶")

    /** 阴阳（奇门遁甲）  */
    val YIN_YANG_QI_MEN = arrayOf("阳", "阴", "阳", "阳", "阳", "阴", "阴", "阳", "阴")

    /**
     * 获取九数
     * @return 九数
     */
    fun getNumber(): String {
        return NUMBER[index]
    }

    /**
     * 获取七色
     * @return 七色
     */
    fun getColor(): String {
        return COLOR[index]
    }

    /**
     * 获取五行
     * @return 五行
     */
    fun getWuXing(): String {
        return WU_XING[index]
    }

    /**
     * 获取方位
     * @return 方位
     */
    fun getPosition(): String {
        return POSITION[index]
    }

    /**
     * 获取方位描述
     * @return 方位描述
     */
    fun getPositionDesc(): String {
        return LunarUtil.POSITION_DESC[getPosition()] ?: ""
    }

    /**
     * 获取玄空九星名称
     * @return 玄空九星名称
     */
    fun getNameInXuanKong(): String {
        return NAME_XUAN_KONG[index]
    }

    /**
     * 获取北斗九星名称
     * @return 北斗九星名称
     */
    fun getNameInBeiDou(): String {
        return NAME_BEI_DOU[index]
    }

    /**
     * 获取奇门九星名称
     * @return 奇门九星名称
     */
    fun getNameInQiMen(): String {
        return NAME_QI_MEN[index]
    }

    /**
     * 获取太乙九神名称
     * @return 太乙九神名称
     */
    fun getNameInTaiYi(): String {
        return NAME_TAI_YI[index]
    }

    /**
     * 获取奇门九星吉凶
     * @return 大吉/小吉/大凶/小凶
     */
    fun getLuckInQiMen(): String {
        return LUCK_QI_MEN[index]
    }

    /**
     * 获取玄空九星吉凶
     * @return 吉/凶
     */
    fun getLuckInXuanKong(): String {
        return LUCK_XUAN_KONG[index]
    }

    /**
     * 获取奇门九星阴阳
     * @return 阴/阳
     */
    fun getYinYangInQiMen(): String {
        return YIN_YANG_QI_MEN[index]
    }

    /**
     * 获取太乙九神类型
     * @return 吉神/凶神/安神
     */
    fun getTypeInTaiYi(): String {
        return TYPE_TAI_YI[index]
    }

    /**
     * 获取八门（奇门遁甲）
     * @return 八门
     */
    fun getBaMenInQiMen(): String {
        return BA_MEN_QI_MEN[index]
    }

    /**
     * 获取太乙九神歌诀
     * @return 太乙九神歌诀
     */
    fun getSongInTaiYi(): String {
        return SONG_TAI_YI[index]
    }

    /**
     * 获取九星序号，从0开始
     * @return 序号
     */
    fun getIndex(): Int {
        return index
    }

    /**
     * 获取详细描述
     * @return 详细描述
     */
    fun toFullString(): String {
        val s = StringBuilder()
        s.append(getNumber())
        s.append(getColor())
        s.append(getWuXing())
        s.append(" ")
        s.append(getPosition())
        s.append("(")
        s.append(getPositionDesc())
        s.append(") ")
        s.append(getNameInBeiDou())
        s.append(" 玄空[")
        s.append(getNameInXuanKong())
        s.append(" ")
        s.append(getLuckInXuanKong())
        s.append("] 奇门[")
        s.append(getNameInQiMen())
        s.append(" ")
        s.append(getLuckInQiMen())
        if (getBaMenInQiMen().isNotEmpty()) {
            s.append(" ")
            s.append(getBaMenInQiMen())
            s.append("门")
        }
        s.append(" ")
        s.append(getYinYangInQiMen())
        s.append("] 太乙[")
        s.append(getNameInTaiYi())
        s.append(" ")
        s.append(getTypeInTaiYi())
        s.append("]")
        return s.toString()
    }

    override fun toString(): String {
        return getNumber() + getColor() + getWuXing() + getNameInBeiDou()
    }
}
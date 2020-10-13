package com.tiamosu.lunar

import com.tiamosu.lunar.utils.LunarUtil

/**
 * 描述：节气
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class JieQi(
    name: String?,
    /** 阳历日期  */
    private var solar: Solar
) {
    /** 名称  */
    private var name: String? = null

    /** 是否节令  */
    private var jie = false

    /** 是否气令  */
    private var qi = false

    init {
        setName(name)
    }

    /**
     * 获取名称
     * @return 名称
     */
    fun getName(): String? {
        return name
    }

    /**
     * 设置名称
     * @param name 名称
     */
    fun setName(name: String?) {
        this.name = name
        for (key in LunarUtil.JIE) {
            if (key == name) {
                jie = true
                return
            }
        }
        for (key in LunarUtil.QI) {
            if (key == name) {
                qi = true
                return
            }
        }
    }

    /**
     * 获取阳历日期
     * @return 阳历日期
     */
    fun getSolar(): Solar {
        return solar
    }

    /**
     * 设置阳历日期
     * @param solar 阳历日期
     */
    fun setSolar(solar: Solar) {
        this.solar = solar
    }

    /**
     * 是否节令
     * @return true/false
     */
    fun isJie() = jie

    /**
     * 是否气令
     * @return true/false
     */
    fun isQi() = qi

    override fun toString(): String {
        return name ?: ""
    }
}
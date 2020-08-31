package com.tiamosu.lunar

/**
 * 描述：节气
 *
 * @author tiamosu
 * @date 2020/8/31.
 */
class JieQi(
    /** 名称  */
    private var name: String?,
    /** 阳历日期  */
    private var solar: Solar
) {

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
    fun setName(name: String) {
        this.name = name
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
}
package com.tiamosu.lunar

/**
 * 数九
 *
 * @author tiamosu
 * @date 2021/1/6.
 */
class ShuJiu(
    /**
     * 名称，如一九、二九
     */
    var name: String,
    /**
     * 当前数九第几天，1-9
     */
    var index: Int
) {

    fun toFullString(): String {
        return name + "第" + index + "天"
    }

    override fun toString(): String {
        return name
    }
}
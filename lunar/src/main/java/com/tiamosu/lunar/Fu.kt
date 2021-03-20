package com.tiamosu.lunar

/**
 * 三伏
 * 从夏至后第3个庚日算起，初伏为10天，中伏为10天或20天，末伏为10天。当夏至与立秋之间出现4个庚日时中伏为10天，出现5个庚日则为20天。
 *
 * @author tiamosu
 * @date 2021/1/6.
 */
class Fu(
    /**
     * 名称：初伏、中伏、末伏
     */
    var name: String,
    /**
     * 当前入伏第几天，1-20
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
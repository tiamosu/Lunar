package com.tiamosu.lunar

/**
 * 描述：节假日
 *
 * @author tiamosu
 * @date 2020/6/1.
 */
class Holiday {
    /** 日期，YYYY-MM-DD格式  */
    private var day: String? = null

    /** 名称，如：国庆  */
    private var name: String? = null

    /** 是否调休，即是否要上班  */
    private var work = false

    /** 关联的节日，YYYY-MM-DD格式  */
    private var target: String? = null

    constructor()

    /**
     * 初始化
     * @param day 日期
     * @param name 名称
     * @param work 是否调休
     * @param target 关联的节日
     */
    constructor(
        day: String,
        name: String,
        work: Boolean,
        target: String
    ) {
        if (!day.contains("-")) {
            this.day = day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6)
        } else {
            this.day = day
        }
        this.name = name
        this.work = work
        if (!target.contains("-")) {
            this.target =
                target.substring(0, 4) + "-" + target.substring(4, 6) + "-" + target.substring(6)
        } else {
            this.target = target
        }
    }

    /**
     * 获取日期
     * @return 日期
     */
    fun getDay(): String? {
        return day
    }

    /**
     * 设置日期
     * @param day 日期
     */
    fun setDay(day: String) {
        this.day = day
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
    fun setName(name: String) {
        this.name = name
    }

    /**
     * 是否调休
     * @return true/false
     */
    fun isWork(): Boolean {
        return work
    }

    /**
     * 设置是否调休
     * @param work true/false
     */
    fun setWork(work: Boolean) {
        this.work = work
    }

    /**
     * 获取关联的节日
     * @return 节日
     */
    fun getTarget(): String? {
        return target
    }

    /**
     * 设置关联的节日
     * @param target 节日
     */
    fun setTarget(target: String) {
        this.target = target
    }

    override fun toString(): String {
        return day + " " + name + (if (work) "调休" else "") + " " + target
    }
}
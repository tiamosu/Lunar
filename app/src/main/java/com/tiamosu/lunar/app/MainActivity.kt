package com.tiamosu.lunar.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nlf.calendar.Solar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var solar = Solar()
        Log.e("xia", "solar:" + solar.toFullString())

        val lunar = solar.lunar
        Log.e("xia", "lunar:" + lunar.toFullString())

        Log.e("xia", "eightChar:" + lunar.eightChar)

        solar = Solar.fromYmd(2020, 6, 21)
        Log.e("xia", "${solar.festivals}")

        solar = Solar.fromYmd(2020, 11, 26)
        Log.e("xia", "${solar.festivals}")

        solar = Solar.fromYmd(2021, 5, 9)
        Log.e("xia", "${solar.festivals}")

        val jieQiTable = lunar.jieQiTable
        Log.e("xia", "节气:$jieQiTable")

        val jieQiArray = arrayListOf<String>()
        jieQiTable.forEach { (t, u) ->
            val value = u.toString().replace("-", "")
            if (value.startsWith(solar.year.toString())) {
                val jieQi = "$value$t"
                jieQiArray.add(jieQi)
            }
        }
        Log.e("xia", "节气数组：$jieQiArray")
    }
}
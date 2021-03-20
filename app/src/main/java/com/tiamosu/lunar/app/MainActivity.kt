package com.tiamosu.lunar.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tiamosu.lunar.Lunar
import com.tiamosu.lunar.Solar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var solar = Solar()
        Log.e("xia", "solar:" + solar.toFullString())

        val lunar = solar.getLunar()
        Log.e("xia", "lunar:" + lunar.toFullString())

        Log.e("xia", "eightChar:" + lunar.eightChar)

        solar = Solar.fromYmd(2020, 6, 21)
        Log.e("xia", "${solar.getFestivals()}")

        solar = Solar.fromYmd(2020, 11, 26)
        Log.e("xia", "${solar.getFestivals()}")

        solar = Solar.fromYmd(2021, 5, 9)
        Log.e("xia", "${solar.getFestivals()}")

        val jieQiTable = lunar.getJieQiTable()
        Log.e("xia", "节气:$jieQiTable")

        val jieQiArray = arrayListOf<String>()
        jieQiTable.forEach { (t, u) ->
            var key = t
            if (key == Lunar.JIE_QI_APPEND) {
                key = Lunar.JIE_QI_FIRST
            }
            val value = u.toString().replace("-", "")
            if (value.startsWith(solar.getYear().toString())) {
                val jieQi = "$value$key"
                jieQiArray.add(jieQi)
            }
        }
        Log.e("xia", "节气数组：$jieQiArray")
    }
}
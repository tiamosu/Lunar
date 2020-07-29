package com.tiamosu.lunar.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tiamosu.lunar.Solar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val solar = Solar()
        Log.e("xia", "solar:" + solar.toFullString())

        val lunar = solar.getLunar()
        Log.e("xia", "lunar:" + lunar.toFullString())

        Log.e("xia", "dayGoodTime:" + lunar.getDayTimesAndTianShen())

        Log.e("xia", "eightChar:" + lunar.eightChar)
    }
}
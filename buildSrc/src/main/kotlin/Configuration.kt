@file:Suppress("unused", "SpellCheckingInspection")

object Android {
    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 32

    const val versionName = "1.0.0"
    const val versionCode = 10000
}

object Versions {
    const val kotlin = "1.7.10"
    const val lunar = "1.2.26"
}

object Deps {
    const val appcompat = "androidx.appcompat:appcompat:1.5.1"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val lunar = "cn.6tail:lunar:${Versions.lunar}"
}
@file:Suppress("unused", "SpellCheckingInspection")

object Android {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 30

    const val versionName = "1.0"
    const val versionCode = 1
}

object Versions {
    const val kotlin = "1.6.21"
    const val lunar = "1.2.22"
}

object Deps {
    const val appcompat = "androidx.appcompat:appcompat:1.4.1"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val lunar = "cn.6tail:lunar:${Versions.lunar}"
}
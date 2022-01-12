@file:Suppress("unused", "SpellCheckingInspection")

object Android {
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 30

    const val versionName = "1.0"
    const val versionCode = 1
}

object Versions {
    const val kotlin = "1.6.10"
    const val appcompat = "1.4.0"
    const val constraintlayout = "2.1.2"
    const val lunar = "1.2.18"
}

object Deps {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val lunar = "cn.6tail:lunar:${Versions.lunar}"
}
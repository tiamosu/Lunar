@file:Suppress("unused", "SpellCheckingInspection")

object Android {
    const val compileSdkVersion = 30
    const val minSdkVersion = 15
    const val targetSdkVersion = 30

    const val versionName = "1.0"
    const val versionCode = 1
}

object Versions {
    const val kotlin = "1.4.32"
    const val appcompat = "1.3.1"
    const val constraintlayout = "2.1.1"
    const val lunar = "1.2.7"
}

object Publish {
    const val groupId = "com.github.tiamosu"
}

object Deps {
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidx_constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val lunar = "cn.6tail:lunar:${Versions.lunar}"
}
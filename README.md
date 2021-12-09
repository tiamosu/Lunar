# Lunar

## Download

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()

        //或者 sonatype
        maven { url "https://s01.oss.sonatype.org/content/groups/public" }
        maven { url "https://s01.oss.sonatype.org/content/repositories/releases" }
    }
}
```

``` groovy
implementation 'com.gitee.tiamosu:lunar:2.0.0'
```

## *特别感谢*

[lunar-java](https://github.com/6tail/lunar-java)

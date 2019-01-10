# android-utils
Handy classes and functions used by the Android team @ PK

<a href=https://jitpack.io/#paperkite/android-utils>Latest release</a> 

![Release](https://jitpack.io/v/paperkite/android-utils.svg)

**project/build.gradle**
```
repositories {
  jcenter()
  maven { url "https://jitpack.io" }
}
```

**app/build.gradle**
```
dependencies {
  // if you want everything together:
  implementation "com.github.paperkite:android-utils:$android_utils_version"
  
  // pk generic utils
  implementation "com.github.paperkite.android-utils:pktx:$android_utils_version"
  
  // rx utils
  implementation "com.github.paperkite.android-utils:rx:$android_utils_version"

  // arch utils
  implementation "com.github.paperkite.android-utils:arch:$android_utils_version"

  // handy custom views
  implementation "com.github.paperkite.android-utils:views:$android_utils_version"
}
```

# android-utils
Handy utility classes and functions used by the Android team @ PK

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
  implementation 'com.github.paperkite:android-utils:v0.0.3'
  
  // core utils
  implementation 'com.github.paperkite.android-utils:core:v0.0.3'
  
  // rx utils
  implementation 'com.github.paperkite.android-utils:rx:v0.0.3'
}
```

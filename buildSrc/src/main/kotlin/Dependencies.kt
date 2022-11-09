object Dependencies {

    //Classpath
    const val AGP = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_AGP}"

    //Hilt
    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"

    // AndroidX
    const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    // UI
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

    //ViewBindingPropertyDelegates
    const val VIEW_BINDING_PROPERTY_DELEGATE =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.VIEW_BINDING_PROPERTY_DELEGATE}"

}
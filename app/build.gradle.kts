plugins {
    kotlin(Plugins.KAPT)
    id(Plugins.ANDROID)
    id(Plugins.KOTLIN)
    id(Plugins.HILT)
}

android {
    compileSdk = AndroidConfiguration.COMPILE_SDK_VERSION
    namespace = AndroidConfiguration.APP_ID

    defaultConfig {
        applicationId = AndroidConfiguration.APP_ID
        minSdk = AndroidConfiguration.MIN_SDK_VERSION
        targetSdk = AndroidConfiguration.TARGET_SDK_VERSION
        versionCode = AndroidConfiguration.VERSION_CODE
        versionName = AndroidConfiguration.VERSION_NAME
        buildToolsVersion = AndroidConfiguration.BUILD_TOOLS_VERSION
        multiDexEnabled = true
        renderscriptTargetApi = 19
        renderscriptSupportModeEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }

    kapt {
        correctErrorTypes = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Dependencies.CORE)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.RECYCLER_VIEW)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.VIEW_BINDING_PROPERTY_DELEGATE)
    implementation(Dependencies.FRAGMENT)
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.HILT)
    kapt(Dependencies.HILT_COMPILER)
}
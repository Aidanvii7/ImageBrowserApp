@file:Suppress("ClassName")

object projectConfig {
    const val applicationName = "com.imagebrowser.app"
    const val versionCode = 1
    const val versionName = "1.0"
    const val defaultProguardRulesFileName = "proguard-android-optimize.txt"
    const val proguardRulesFileName = "proguard-rules.pro"
    const val androidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"

    object buildTypeConfigs {

        val debug = BuildTypeConfig(
            name = "debug",
            minifyEnabled = false
        )

        val release = BuildTypeConfig(
            name = "release",
            minifyEnabled = true
        )

        val all = listOf(debug, release)
    }
}

object kotlinSdk {

    object versions {
        const val base = "1.3.71"
        const val coroutines = "1.3.5"
    }

    private const val kotlinGroup = "org.jetbrains.kotlin"

    object stdlib {
        const val common = "$kotlinGroup:kotlin-stdlib-common:${versions.base}"
        const val android = "$kotlinGroup:kotlin-stdlib:${versions.base}"
    }

    const val junit = "$kotlinGroup:kotlin-test-junit:${versions.base}"

    object extensions {
        private const val kotlinXGroup = "org.jetbrains.kotlinx"

        object coroutines {
            private const val coroutinesPrefix = "$kotlinXGroup:kotlinx-coroutines"
            const val common = "$coroutinesPrefix-core-common:${versions.coroutines}"
            const val android = "$coroutinesPrefix-android:${versions.coroutines}"
            const val test = "$coroutinesPrefix-test:${versions.coroutines}"
        }
    }
}

object buildPlugins {

    private object versions {
        const val gradle = "3.5.3"
        const val junit5 = "1.5.1.0"
        const val navigation = "2.1.0"
    }

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val android = "android"
    const val kotlinAndroidExtensions = "android.extensions"
    const val androidJUnit5 = "de.mannodermaus.android-junit5"
    const val androidGradle = "com.android.tools.build:gradle:${versions.gradle}"
    const val androidJupiter = "de.mannodermaus.gradle.plugins:android-junit5:${versions.junit5}"
    const val androidxNavigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.navigation}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinSdk.versions.base}"
    const val kapt = "kapt"
}

object androidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
    const val buildTools = "29.0.1"
}

object libraries {

    object androidX {
        private object versions {
            const val core = "1.2.0"
            const val annotation = "1.1.0"
            const val appCompat = "1.1.0"
            const val cardView = "1.0.0"
            const val legacy = "1.0.0"
            const val constraintLayout = "1.1.3"
            const val navigation = "2.2.2"
            const val lifecycle = "2.2.0"
            const val paging = "3.0.0-alpha01"
        }

        const val annotation = "androidx.annotation:annotation:${versions.annotation}"
        const val appCompat = "androidx.appcompat:appcompat:${versions.appCompat}"
        const val cardView = "androidx.cardview:cardview:${versions.cardView}"
        const val legacy = "androidx.legacy:legacy-support-v4:${versions.legacy}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${versions.constraintLayout}"

        object navigation {
            const val fragment = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
            const val ui = "androidx.navigation:navigation-ui-ktx:${versions.navigation}"
        }

        object paging {
            const val common = "androidx.paging:paging-common:${versions.paging}"
            const val runtime = "androidx.paging:paging-runtime:${versions.paging}"
        }

        object ktx {
            const val core = "androidx.core:core-ktx:${versions.core}"
            const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycle}"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${versions.lifecycle}"
        }
    }

    object google {
        private object versions {
            const val material = "1.2.0-alpha05"
        }

        const val material = "com.google.android.material:material:${versions.material}"
    }

    object square {
        private object versions {
            const val okHttp3LoggingInterceptor = "4.5.0"
            const val leakCanary = "2.2"
            const val retrofit2 = "2.8.1"
            const val retrofit2Moshi = "2.4.0"
            const val moshiKotlin = "1.9.2"
        }

        private const val squareGroupPrefix = "com.squareup"
        const val okHttp3LoggingInterceptor = "$squareGroupPrefix.okhttp3:logging-interceptor:${versions.okHttp3LoggingInterceptor}"
        const val leakCanary = "$squareGroupPrefix.leakcanary:leakcanary-android:${versions.leakCanary}"
        const val retrofit2 = "$squareGroupPrefix.retrofit2:retrofit:${versions.retrofit2}"
        const val retrofit2Moshi = "$squareGroupPrefix.retrofit2:converter-moshi:${versions.retrofit2Moshi}"
        const val moshiKotlin = "$squareGroupPrefix.moshi:moshi-kotlin:${versions.moshiKotlin}"
        const val moshiKotlinCodegen = "$squareGroupPrefix.moshi-kotlin-codegen:${versions.moshiKotlin}"
    }

    object koin {

        private object versions {
            const val koin = "2.1.5"
        }

        private const val koinGroup = "org.koin"
        const val core = "$koinGroup:koin-core:${versions.koin}"
        const val viewModel = "$koinGroup:koin-android-viewmodel:${versions.koin}"
        const val test = "$koinGroup:koin-test:${versions.koin}"
    }

    object junit5 {

        private object versions {
            const val junit5 = "5.6.1"
        }

        private const val junit5Group = "org.junit.jupiter"
        const val api = "$junit5Group:junit-jupiter-api:${versions.junit5}"
        const val params = "$junit5Group:junit-jupiter-params:${versions.junit5}"
        const val engine = "$junit5Group:junit-jupiter-engine:${versions.junit5}"
    }

    object other {
        object versions {
            const val shimmer = "0.5.0"
            const val glide = "4.11.0"
            const val mockitoKotlin = "2.2.0"
            const val kluent = "1.60"
            const val ioUtils = "2.6.0"
            const val kuperReflect = "0.2.1"
        }

        const val shimmer = "com.facebook.shimmer:shimmer:${versions.shimmer}"
        const val glide = "com.github.bumptech.glide:glide:${versions.glide}"
        const val mockitoKotlin2 = "com.nhaarman.mockitokotlin2:mockito-kotlin:${versions.mockitoKotlin}"
        const val kluentJvm = "org.amshove.kluent:kluent:${versions.kluent}"
        const val kluentAndroid = "org.amshove.kluent:kluent-android:${versions.kluent}"
        const val ioUtils = "org.lucee:commons-io:${versions.ioUtils}"
        const val kuperReflect = "com.github.dpreussler:KuperReflect:${versions.kuperReflect}"
    }
}

object projectModules {
    const val app = ":app"
    const val common = ":Common"

    object feature {
        const val common = ":FeatureCommon"

        object browseImages {
            const val presentation = ":FeatureImagesPresentation"
            const val coreBase = ":FeatureImagesCoreBase"
            const val coreImpl = ":FeatureImagesCoreImplementation"
        }
    }

    val all = listOf(
        app,
        common,
        feature.common,
        feature.browseImages.presentation,
        feature.browseImages.coreBase,
        feature.browseImages.coreImpl
    )
}
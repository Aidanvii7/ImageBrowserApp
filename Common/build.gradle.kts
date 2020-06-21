plugins {
    id(buildPlugins.androidLibrary)
    id(buildPlugins.androidJUnit5)
    kotlin(buildPlugins.android)
    kotlin(buildPlugins.kapt)
    kotlin(buildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(androidSdk.compile)
    buildToolsVersion(androidSdk.buildTools)
    defaultConfig {
        minSdkVersion(androidSdk.min)
        targetSdkVersion(androidSdk.target)
        versionCode = projectConfig.versionCode
        versionName = projectConfig.versionName
        testInstrumentationRunner = projectConfig.androidJUnitRunner
    }
    buildTypes {
        projectConfig.buildTypeConfigs.all.forEach { buildTypeConfig ->
            getByName(buildTypeConfig.name) {
                if (buildTypeConfig.minifyEnabled) {
                    isMinifyEnabled = true
                    proguardFiles(getDefaultProguardFile(projectConfig.defaultProguardRulesFileName), projectConfig.proguardRulesFileName)
                }
            }
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
    api(kotlinSdk.stdlib.common)
    api(kotlinSdk.extensions.coroutines.common)
    api(kotlinSdk.stdlib.android)
    api(kotlinSdk.extensions.coroutines.android)
    api(libraries.androidX.annotation)
    api(libraries.androidX.ktx.core)
    api(libraries.koin.core)
}

androidExtensions {
    isExperimental = true
}
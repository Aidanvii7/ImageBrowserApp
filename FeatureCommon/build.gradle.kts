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
    dataBinding.isEnabled = true
}

dependencies {
    implementation(project(projectModules.common))
    api(libraries.androidX.appCompat)
    api(libraries.androidX.cardView)
    api(libraries.androidX.legacy)
    api(libraries.androidX.constraintLayout)
    api(libraries.androidX.ktx.lifecycleViewModel)
    api(libraries.google.material)
    api(libraries.other.shimmer)
    api(libraries.androidX.paging.runtime)
}

androidExtensions {
    isExperimental = true
}
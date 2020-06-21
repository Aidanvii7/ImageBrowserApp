plugins {
    id(buildPlugins.androidApplication)
    id(buildPlugins.androidJUnit5)
    kotlin(buildPlugins.android)
    kotlin(buildPlugins.kapt)
    kotlin(buildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(androidSdk.compile)
    buildToolsVersion(androidSdk.buildTools)
    defaultConfig {
        applicationId = projectConfig.applicationName
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
    implementation(project(projectModules.feature.browseImages.presentation))
    implementation(project(projectModules.feature.browseImages.coreBase))
    implementation(project(projectModules.feature.browseImages.coreImpl))
    implementation(libraries.androidX.appCompat)
    implementation(libraries.androidX.navigation.fragment)
    implementation(libraries.androidX.navigation.ui)
    implementation(libraries.koin.viewModel)
    implementation(libraries.other.glide)
    implementation(libraries.square.okHttp3LoggingInterceptor)
}

androidExtensions {
    isExperimental = true
}
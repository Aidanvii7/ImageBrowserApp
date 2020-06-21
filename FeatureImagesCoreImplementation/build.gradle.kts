plugins {
    id(buildPlugins.androidLibrary)
    id(buildPlugins.androidJUnit5)
    kotlin(buildPlugins.android)
    kotlin(buildPlugins.kapt)
    kotlin(buildPlugins.kotlinAndroidExtensions)
    id("androidx.navigation.safeargs")
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
        val fields = listOf(
            BuildConfigField.StringType(
                name = "PIXABAY_BASE_URL",
                value = "https://pixabay.com/"
            ),
            BuildConfigField.StringType(
                name = "PIXABAY_API_KEY",
                value = "17133203-bcb4e540a06cd98efda4bada7"
            ),
            BuildConfigField.StringType(
                name = "PIXABAY_SEARCH_TERM",
                value = "cats"
            ),
            BuildConfigField.IntType(
                name = "PIXABAY_PAGE_SIZE",
                value = 10
            )
        )
        projectConfig.buildTypeConfigs.all.forEach { buildTypeConfig ->
            getByName(buildTypeConfig.name) {
                fields.forEach { field ->
                    field.apply { buildConfigField(type, name, formattedValue) }
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
    api(project(projectModules.feature.browseImages.coreBase))
    implementation(project(projectModules.common))

    implementation(libraries.square.retrofit2)
    implementation(libraries.square.moshiKotlin)
    implementation(libraries.square.retrofit2Moshi)
    kapt(libraries.square.retrofit2Moshi)
    implementation(libraries.square.okHttp3LoggingInterceptor)
    implementation(libraries.androidX.room.runtime)
    implementation(libraries.koin.core)
    testImplementation(project(projectModules.testUtils))
    testImplementation(libraries.androidX.ktx.liveData)
    testRuntimeOnly(libraries.junit5.engine)
    kapt(libraries.androidX.room.compiler)
}

androidExtensions {
    isExperimental = true
}
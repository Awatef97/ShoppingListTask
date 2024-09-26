plugins {
    id ("com.android.application")
    id("kotlin-android")
    kotlin("android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.shoppinglisttask"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

       // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.shoppinglisttask.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    javaVersions()
}

fun com.android.build.gradle.internal.dsl.BaseAppModuleExtension.javaVersions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation("androidx.test.ext:junit-ktx:1.2.1")
    implementation("androidx.navigation:navigation-testing:2.8.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // android ui
    implementation(AndroidUI.appCompact)
    implementation(AndroidUI.materialDesign)
    implementation(AndroidUI.constraintLayout)
    implementation(AndroidUI.recycleView)
    implementation(Utils.timber)

    // Coroutines
    implementation(Coroutines.coroutines)

    // Hilt
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    //Navigation
    implementation(NavigationComponent.navigationFragmentKTX)
    implementation(NavigationComponent.navigationUIKTX)

    //Lifecycle
    implementation(Lifecycle.coreLifeCycle)
    implementation(Lifecycle.fragmentLifeCycle)
    implementation(Lifecycle.activityLifeCycle)
    implementation(Lifecycle.runtimeLifeCycle)
    implementation(Lifecycle.livedataLifeCycle)
    implementation(Lifecycle.viewModelLifeCycle)

    // Room
    implementation(Room.roomRunTime)
    kapt(Room.roomCompiler)
    implementation(Room.roomKTX)

    //Test
    testImplementation(Test.testMockk)
    testImplementation(Test.testCoroutines)
    androidTestImplementation(Test.androidTestMockk)
    androidTestImplementation(Test.hiltTest)
    testImplementation(Test.hiltTest)
    testImplementation(Test.testCore)
    androidTestImplementation(Test.testCore)
    testImplementation(Test.testJetbrains)
    testImplementation (Test.testAndroidJetbrains)
    debugImplementation (Test.testAndroidFragment)

}
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isIncludeCompileClasspath

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt) // Add this line
    alias(libs.plugins.kotlin.kapt) // Add this line
}

android {
    namespace = "com.zybooks.practicepals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zybooks.practicepals"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("boolean", "LOG_SQL_STATEMENTS", "true")

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Change to VERSION_11
        targetCompatibility = JavaVersion.VERSION_11 // Change to VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11" // Change to "11"
    }

    // Required for Jetpack Compose
    buildFeatures {
        compose = true
        viewBinding = false  // Disable viewBinding since we’re switching to Compose
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"  // Use the version in your Compose dependencies
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    val room_version = "2.6.1"
    // Core Android and Jetpack libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Jetpack Compose dependencies
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.material3.android)
//    implementation(libs.androidx.room.common)
//    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.runtime.livedata)

    // Hilt dependencies
    implementation(libs.hilt.android) // Add this line
    kapt(libs.hilt.compiler)          // Add this line
    implementation(libs.hilt.navigation.compose) // Add this line

//    implementation(libs.room.runtime)          // Room runtime
//    kapt(libs.room.compiler)                   // Room compiler for annotation processing
//    implementation(libs.room.ktx)              // Room Kotlin extensions

    // Room!
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")
    // End of room

    // Compose tooling (optional, for previews and testing)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.junit4)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


}

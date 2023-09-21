plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.ik.movverexample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ik.movverexample"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.ik.movverexample.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

// Versions
val COMPOSE_VERSION = "2023.03.00"
val KOTLINX_COROUTINES_VERSION = "1.7.3"
val MOSHI_VERSION = "1.15.0"
val RETROFIT_VERSION = "2.9.0"
val ROOM_VERSION = "2.4.0"
val DAGGER_HILT_VERSION = "2.48"
val OKHTTP_VERSION = "4.11.0"
val JUNIT_VERSION = "4.13.2"
val TRUTH_VERSION = "1.1.5"

dependencies {
    // AndroidX Libraries
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.room:room-ktx:$ROOM_VERSION")
    implementation("androidx.room:room-runtime:$ROOM_VERSION")

    // Compose
    implementation(platform("androidx.compose:compose-bom:$COMPOSE_VERSION"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Networking
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:converter-moshi:$RETROFIT_VERSION")
    implementation("com.squareup.okhttp3:okhttp:$OKHTTP_VERSION")
    implementation("com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION")

    // Json Parsing
    implementation("com.squareup.moshi:moshi-kotlin:$MOSHI_VERSION")

    // Google Services & Maps
    implementation("com.google.maps.android:maps-compose:2.14.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // Dependency Injection - Hilt
    implementation("com.google.dagger:hilt-android:$DAGGER_HILT_VERSION")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLINX_COROUTINES_VERSION")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLINX_COROUTINES_VERSION")

    // Testing
    testImplementation("androidx.room:room-testing:$ROOM_VERSION")
    testImplementation("junit:junit:$JUNIT_VERSION")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$KOTLINX_COROUTINES_VERSION")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("com.google.truth:truth:$TRUTH_VERSION")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:$COMPOSE_VERSION"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.activity:activity-compose")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$DAGGER_HILT_VERSION")
    androidTestImplementation("com.google.truth:truth:$TRUTH_VERSION")

    // Annotations & Kapt
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$MOSHI_VERSION")
    kapt("com.google.dagger:hilt-compiler:$DAGGER_HILT_VERSION")
    kapt("androidx.room:room-compiler:$ROOM_VERSION")
    kaptAndroidTest("com.google.dagger:hilt-compiler:$DAGGER_HILT_VERSION")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

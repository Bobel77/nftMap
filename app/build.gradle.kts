import java.net.URL

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
}


android {
    namespace = "com.example.nftmap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nftmap"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
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

}

dependencies {
    val ktor_version = "2.3.8"
    val nav_version = "2.7.7"
    configurations.all {
        exclude(module = "bcprov-jdk15on")
    }
    //Compose
    implementation("androidx.compose.foundation:foundation:1.6.2")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.google.gms:google-services:4.4.1")

  //qr scan
    implementation ("com.github.yuriy-budiyev:code-scanner:2.3.0")/*
    implementation("com.github.skydoves:landscapist-glide:1.3.7")*/
/*    implementation ("com.github.bumptech.glide:glide:4.12.0")*/
    implementation( "com.github.bumptech.glide:compose:1.0.0-beta01")
    /// Crypto
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(platform("com.walletconnect:android-bom:1.23.0"))
    implementation("com.walletconnect:android-core")
    implementation("com.walletconnect:web3modal")


    //Ktor
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-json:$ktor_version")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
/*    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")*/

    //Map and Location
    implementation ("com.google.android.gms:play-services-location:21.1.0")
    implementation ("org.osmdroid:osmdroid-android:6.1.14")

/*    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")*/
}

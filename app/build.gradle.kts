plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "smt3.assignme_11"
    compileSdk = 34

    defaultConfig {
        applicationId = "smt3.assignme_11"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    viewBinding {
        enable = true
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
    //untuk mengambil gambar dari berbagai sumber
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.google.android.material:material:1.11.0-beta01")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //pin otp dependency.
    implementation("com.github.GoodieBag:Pinview:v1.5")
    //bottom navigation
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    implementation("com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")

}
plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("koin")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "stickearn.movie.stickearnmovieapps"
        minSdkVersion (21)
        targetSdkVersion (29)
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField ("String", "BASE_SERVER_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField ("String", "TMDB_API_KEY", "\"a35068e5335660fe0117dfa9f02011cc\"")
        buildConfigField ("String", "BASE_TMDB_IMAGE_URL", "\"https://image.tmdb.org/\"")
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled= false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
        }
    }

    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    compileOptions {
        //sourceCompatibility(1.8)
        //targetCompatibility(1.8)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*jar"))))

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.4.0")

    implementation ("androidx.core:core-ktx:1.3.1")
    implementation ("com.google.android.material:material:1.2.1")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation ("androidx.paging:paging-runtime:2.1.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation ("androidx.room:room-runtime:2.2.6")
    implementation ("androidx.room:room-ktx:2.2.6")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    kapt("androidx.room:room-compiler:2.2.6")

    implementation ("org.koin:koin-core:2.1.6")
    implementation ("org.koin:koin-androidx-viewmodel:2.1.6")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")

    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.jakewharton.threetenabp:threetenabp:1.2.4")

    testImplementation ("junit:junit:4.12")
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")

}
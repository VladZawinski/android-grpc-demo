import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.proto

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.protobuf)
}

android {
    namespace = "com.vlad.grpcdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vlad.grpcdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("build/generated/source/proto/main/java")
            }
            kotlin {
                srcDirs("build/generated/source/proto/main/kotlin")
            }
            proto {
                srcDir("src/main/proto")
            }
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.29.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.70.0"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                // Configure the built-in plugins
                id("java") {
                    option("lite")
                }
            }
            task.plugins {
                id("grpc") {
                    option("lite")
                }
            }
        }
    }
}

//protobuf {
//    protoc {
//        // Specify the Protocol Buffer compiler version
//        artifact = "com.google.protobuf:protoc:4.29.0" // Use the latest version
//    }
//    plugins {
//        // Optional: If you want to use a specific plugin for generating code
//        // Example for gRPC
//        id("grpc") {
//            artifact = "io.grpc:protoc-gen-grpc-java:1.70.0" // Use the latest version
//        }
//    }
//    generateProtoTasks {
//        all().forEach { task ->
//            task.builtins {
//                // Configure the built-in plugins
//                id("java") {}
//                // If you're using gRPC
//                id("grpc") {}
//            }
//        }
//    }
//}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.io.grpc.okttp)
    implementation(libs.io.grpc.protobuf)
    implementation(libs.io.grpc.stub)
    implementation(libs.javax.annotation)
//    implementation(libs.google.protobuf.java)
//    implementation(libs.google.protobuf.kotlin)
//    implementation(libs)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
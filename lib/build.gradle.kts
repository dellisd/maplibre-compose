plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlinAndroid)
}

android {
  namespace = "ca.derekellis.maplibre"
  compileSdk = 34

  defaultConfig {
    minSdk = 24
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
  }
}

kotlin {
  explicitApi()
}

dependencies {
  api(libs.maplibre)

  implementation(libs.lifecycle.runtime)
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.foundation)
  implementation(libs.compose.runtime)
}

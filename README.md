<h1 align="center">Crypto Tracker</h1>
<p align="center">  
"Crypto Tracker" is an Android app for cryptocurrency enthusiasts. It features a user-friendly search function on the main page for users to quickly find detailed information about various cryptocurrencies. Users can access detailed page with comprehensive data, and favorite their preferred cryptocurrencies for easy access. This tool is ideal for efficiently tracking and analyzing cryptocurrency investments.
  </p>
</br>

## Screenshots
<table>
    <tr>
    <td>Splash</td>
    <td>Login</td>
    <td>Register</td>
    <td>Home</td>
   </tr> 
  <tr>
    <td><img src="https://github.com/user-attachments/assets/c054cb40-c1fd-4d00-bdd1-edcb98c738cf" width="100%"></td>
    <td><img src="https://github.com/user-attachments/assets/884b19bb-ec0e-467f-b0a9-02046fd36ab8" width="100%"></td>
    <td><img src="https://github.com/user-attachments/assets/7883978d-1d83-4e56-a051-f3fb31ae2252" width="100%"></td>
    <td><img src="https://github.com/user-attachments/assets/634ca322-bc72-4cf9-9f67-c45bf5cb764d" width="100%"></td>


<table>
        <tr>
    <td>Search</td>
    <td>Favorite</td>
    <td>Profile</td>
    <td>Detail</td>
   </tr> 
  <tr>
    <td><img src="https://github.com/user-attachments/assets/47bfce84-e76b-4596-9c8a-584775daf527" width="100%"></td>
    <td><img src="https://github.com/user-attachments/assets/e400e67d-f7d7-4849-8738-61611619fe82" width="100%"></td>
    <td><img src="https://github.com/user-attachments/assets/f81617d3-7fee-48f6-9851-16b8bae86c8e" width="100%"></td>
    <td><img src="https://github.com/user-attachments/assets/9a883691-e55f-4041-b359-4609cc8415c0" width="100%"></td>
   </tr>  
  </tr>
  </table>

## Live Tracking Features
&emsp;&emsp;<b>Register Loop</b>  &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <b>Search Loop</b>  &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <b>Favorite Loop</b>
<p float="left">
  <img src="https://github.com/user-attachments/assets/cce459e7-7721-42a8-8300-7f8004785c07" width="200"/>&emsp;&emsp;&emsp;
  <img src="https://github.com/user-attachments/assets/79ed661a-99f6-4986-bb27-42d929c55328" width="200" />&emsp;&emsp;&emsp;
  <img src="https://github.com/user-attachments/assets/d2742122-afe5-4c98-8b5c-024eaf85cc5b" width="200" />&emsp;&emsp;&emsp;
</p>

# Development Environment

**Crypto Tracker** uses the Gradle build system and can be imported directly into Android Studio (make sure you are using the latest stable version available [here](https://developer.android.com/studio)). 

The `debug` and `release` build variants can be built and run.

Once you're up and running, you can refer to the learning journeys below to get a better
understanding of which libraries and tools are being used, the reasoning behind the approaches to
UI, architecture and more, and how all of these different pieces of the project fit
together to create a complete app.

# Architecture

The **Crypto Tracker** app follows the **MVVM** (Model-View-ViewModel) architectural design pattern and **Clean Architecture** design pattern. 

![Architecture Diagram](https://github.com/user-attachments/assets/0a3f39cf-01c8-40d1-80dd-222056a3d742 "Architecture Diagram")

# Build

The app contains the usual `debug` and `release` build variants. 

# Package Structure
Package structure of the project designed in the light of MVVM with Clean Architecture.
- Common
- Data
- Dependency Injection
- Domain
- UI
- Util

![Package Structure](https://github.com/user-attachments/assets/70362dc1-00d3-485b-b7ea-2fab9020705a "Package Structure")

## Tech stack & Open-source libraries


- Minimum SDK level 26
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) and [Flow](https://developer.android.com/kotlin/flow)
- [MVVM Architecture](https://developer.android.com/jetpack/guide) - Modern, maintainable, and Google suggested app architecture
- [Material Design 3](https://m3.material.io/) - The latest version of Google’s open-source design system.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern UI toolkit for declarative UI development.

### **🛠️ Architecture & Patterns**
- **MVVM Architecture** - ViewModel-based, lifecycle-aware architecture
- **Repository Pattern** - Single source of truth for data handling
- **Clean Architecture** - Separation of concerns for maintainable code

### **📌 JetPack Components**
- [Navigation Component](https://developer.android.com/guide/navigation) - Single activity, multiple fragments & composables
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observing state updates
- [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Lifecycle-aware data handling
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - UI-related data holder
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - Type-safe View reference binding
- [Fragment-ktx](https://developer.android.com/kotlin/ktx#fragment) - Kotlin extensions for Fragments
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Background task scheduling API for periodic updates

### **🔥 Jetpack Compose**
- [Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - Modern UI toolkit
- [Compose BOM (Bill of Materials)](https://developer.android.com/jetpack/compose/bom) - Simplified version management.

### **📡 Networking & Data**
- [Retrofit](https://square.github.io/retrofit/) - Type-safe HTTP client
- [Gson](https://github.com/google/gson) - JSON parser for Kotlin

### **🔐 Dependency Injection**
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency injection for Android

### **🔥 Firebase Services**
- [Firebase Authentication](https://firebase.google.com/docs/auth) - User authentication via Email, Google, Facebook, etc.
- [Firebase Firestore](https://firebase.google.com/docs/firestore) - NoSQL cloud database

### **📷 Image Loading & UI Enhancements**
- [Glide](https://github.com/bumptech/glide) - Image loading library

## 📦 Dependencies

This project uses **Version Catalog (`libs.versions.toml`)** to manage dependencies efficiently.  
You can find the dependencies inside the [`gradle/libs.versions.toml`](./gradle/libs.versions.toml) file.

### **🛠️ Dependency Management**
Instead of defining dependencies inside `build.gradle.kts`, they are managed via **Version Catalog**.

📍  `build.gradle.kts`:
```kotlin
dependencies {

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.compose)
    implementation(libs.androidx.ui.graphics.compose)
    implementation(libs.androidx.ui.tooling.preview.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Lifecycle
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.extensions)

    //Fragment & Activity KTX
    implementation(libs.fragment.ktx)
    implementation(libs.activity.ktx)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Gson
    implementation(libs.gson)

    // DI
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    //Worker
    implementation(libs.work.runtime)
    implementation(libs.androidx.hilt.work)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Glide
    implementation(libs.glide)
}
```
## 🌍 Open API  
This project uses the **[CoinGecko API](https://docs.coingecko.com/v3.0.1/reference/introduction)** to fetch real-time cryptocurrency data.


## Connect with me :love_letter:
 
[![Linkedin Badge](https://img.shields.io/badge/-Linkedin-6B84BB?style=quare&labelColor=6B84BB&logo=Linkedin&logoColor=white&link=link)](https://www.linkedin.com/in/hilal-kurnaz/) 

📧 **Email:** [hilalkaradev@gmail.com](mailto:hilalkaradev@gmail.com)

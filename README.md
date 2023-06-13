# PlaygroundX JetCompose (Experimental App)

This repository contains a personal experimental app built using Jetpack Compose. The main purpose of this project is to explore and study various architectural patterns and libraries in Android development, with a focus on Jetpack Compose.

## Features

- MVVM Architecture (Model-View-ViewModel)
- Coroutines and Flow for asynchronous programming
- Dependency Injection using Dagger Hilt
- Caching data with Room
- Networking capabilities using Retrofit and Moshi
- Logging with Timber
- Animated SplashScreen (utilizing the SplashScreen API)

## Project Overview

The app primarily focuses on performing network calls, caching the retrieved data, and displaying it in the UI. It follows a structured architecture with a use case layer in addition to the MVVM pattern. The use case layer encapsulates the business logic and orchestrates interactions between the UI, data sources, and other components.

The project utilizes flavors and build types to provide different configurations and behavior based on the build variant.

### Flavors

The app has two flavors: development and production.

- **development:** This flavor is used for development purposes. It has a different application ID suffix and version name suffix, which allows it to be installed alongside the production flavor or other variants.

- **production:** This flavor represents the production-ready version of the app. It uses the default application ID and version name.

### Build Types

The app has two build types: debug and release.

- **debug:** The debug build type is intended for development and debugging purposes. It enables debugging tools, sets the app as debuggable, and adds a debug-specific application ID suffix and version name suffix.

- **release:** The release build type is used for generating the final release version of the app. It enables minification and enables ProGuard rules for code optimization.

## Getting Started

To get started with the project, follow the steps below:

1. Clone the repository to your local machine using the following command:

2. Open the project using Android Studio or your preferred IDE.

3. Build the project to ensure that all dependencies are resolved correctly.

4. Select the desired flavor and build variant (debug or release) to compile and run the app.

5. Run the app on an emulator or a physical device to see it in action.

## Contributing

As this project is primarily a personal experiment, contributions are not expected. However, you are welcome to fork the repository and explore the codebase or experiment with additional features on your own.

## License

This project is licensed under the MIT License. Feel free to modify, distribute, and use the code in accordance with the terms of the license.

## Acknowledgements

The development of this project has been possible thanks to the following open-source libraries:

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Retrofit](https://square.github.io/retrofit/)
- [Moshi](https://github.com/square/moshi)
- [Timber](https://github.com/JakeWharton/timber)

Special thanks to the Android development community for their invaluable resources, tutorials, and discussions that have aided in the exploration and learning process.

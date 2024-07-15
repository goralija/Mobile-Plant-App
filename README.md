Mobile Plant App

Mobile Plant App is an educational tool designed to provide comprehensive information about a wide variety of plants. Leveraging the Trefle.io API, the app grants access to a vast database of plant species, allowing users to explore detailed descriptions, images, and care tips. This app is perfect for plant enthusiasts, gardeners, and anyone interested in botany.

This project is developed as part of the undergraduate studies in Computer Science, specifically for the Mobile App Development course.


Features

    Browse and search for plants
    View detailed information about each plant
    Learn about plant care and maintenance

Prerequisites

    Android Studio
    Android device or emulator
    Internet connection for API access

Getting Started
Clone the Repository

git clone https://github.com/goralija/Mobile-Plant-App.git
cd Mobile-Plant-App

Set Up the Project

    Open Android Studio.
    Select "Open an existing project" and navigate to the cloned repository folder.
    Wait for Android Studio to set up the project and download necessary dependencies.

Configuration

    Obtain an API key from Trefle.io.

    Create a local.properties file in the root directory of the project and add your API key:

    TREFLE_API_KEY=your_api_key_here

Building and Running the App

    Connect your Android device or start an emulator.

    Click on the "Run" button in Android Studio or use the following command:

    bash

    ./gradlew installDebug

Project Structure

    app/: Contains the main application code.
    gradle/: Contains Gradle configuration files.
    .idea/: Contains project-specific settings for Android Studio.

Dependencies

    Retrofit for network requests
    Gson for JSON parsing
    ViewModel and LiveData for MVVM architecture
    Glide for image loading
    Room library for database management

For questions or feedback, please contact the project owner at goralija@gmail.com.
* Repo owner or admin
* Other community or team contact

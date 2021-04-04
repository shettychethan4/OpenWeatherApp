# OpenWeatherApp
Open Weather App in clean architecture built with Jetpack components.

# Technical Stack:
- Retrofit : For Network calls
- Architecture : MVVM
- Coroutines for asynchronous operations
- Room : For offline persistence 
- Work Manager : Fetches weather data every 2 hour (Periodic Request)
- Live Data : To notify view for data change
- Hilt : For dependency injection
- Language : Kotlin
- Testcases : Junit and Room Database test cases

You need an API Key to use the OpenWeatherMap API. Head on over to their https://openweathermap.org/ if you don't already have one. Follow instructions in build-gradle (app) to integrate API key to build the project.


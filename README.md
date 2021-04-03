# OpenWeatherApp
Open Weather App in clean architecture built with Jetpack components.

# Technical Stack:
- Retrofit : For Network calls
- Architecture : MVVM
- Coroutines for asyncrnous operations
- Room : For offline persistence and Paging Library
- Live Data : To notify view for data change
- Hilt : For dependency injection
- Work Manager : Fetches weather data every 2 hour (Periodic Request)
- Language : Kotlin

You need an API Key to use the OpenWeatherMap API. Head on over to their https://openweathermap.org/ if you don't already have one. Follow instructions in build-gradle (app) to integrate API key to build the project.

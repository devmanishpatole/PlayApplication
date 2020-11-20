# Play Application

**PlayApplication** is a sample Android application 📱 built to demonstrate use of *Modern Android development* tools.


## About

Application simply loads **Hacker news top stories** data from API and displays detail, additionally user can switch the application theme.
User has to login using credentials
username = test@worldofplay.in and Password = Worldofplay@2020

Authentication API is hosted on [Heroku](https://www.heroku.com/about) cloud platform, API is created using [Python3](https://www.python.org/about/) with [Flask](https://flask.palletsprojects.com/en/1.1.x/)


## Built With 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying data changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data and bridges the gap between view and model.
- [Dagger 2](https://dagger.dev/) - Dependency Injection Framework
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

**Implemented Lifecycle aware view holder which fetches and loads the information on each row respecting the lifecycle of row's view components.**

# Package Structure


    .
    |── application         # Application class
    |
    ├── base                # Base classes
    |
    ├── di                  # Dependency Injection
    │   ├── component       # DI Components
    │   └── module          # DI Modules
    |
    |── login
    │   ├── model           # Data classes
    |   ├── repository      # Login network repository class
    │   ├── service         # Login Retrofit API for remote end point.
    |   │── ui              # Login Activity
    │   └── viewmodel       # Login view models
    |
    ├── network             # Network class
    |
    |── splash              # Splash Activity and Login manager view model
    |
    ├── story               # Story
    │   ├── adapter         # List adapter
    │   ├── model           # Data classes
    |   │── repository      # Story network repository
    |   │── service         # Story Retrofit API for remote end point.
    |   │── ui              # Activities and Fragment
    |   │── viewholder      # Story list view holder
    │   └── viewmodel       # Story view models
    │
    ├── theme               # Theme manager
    │
    └── utils               # Utility Classes
        └── extension       # Extension classes

## Architecture

This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

## Contact

If you need any help, you can connect with me.

Connect:- manish.patole.mp@gmail.com

## License

```
MIT License

Copyright (c) 2020 Manish Patole

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

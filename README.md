# PagingListMovies
PagingListMovies is a sample app to showcase a simple endless list of [Popular TV Shows](https://developers.themoviedb.org/3/tv/get-popular-tv-shows) powered by [The Movie Database API](https://developers.themoviedb.org) using the [Android Paging Library](https://developer.android.com/topic/libraries/architecture/paging).

## Features
* Provides an endless list of Popular TV Shows
* Rotation handling
* Paging Loading state 
* Error handling with Retry functionality if an error occurs while fetching a page
* Swipe to refresh functionality to refresh the whole list
* Supporting any screen size and any device with Android 4.x+ (API 14 and above)

## Installation
Clone this project from GitHub and import it in Android Studio as an existing Android Gradle Project using the gradle-wrapper option. You might be requested to update your android build-tools or Gradle version.

## Development
* The project is entirely written in Kotlin.
* Based on MVVM Architecture
* Use of Architecture Components (LiveData + ViewModel) and other Jetpack modules
* Use of RxJava2 for asynchronous operations
* Use of Retrofit for Networking
* Use of Dependency-Injection (no library)
* Unit tests with Mockito

### Architecture and Implementation
The project is based on MVVM for the presentation layer + a data layer (the network module)
*  **View** - An Activity for user interaction handling and rendering. Delegates actions to the ViewModel.
*  **ViewModel** - exposes the view states and operates on the DataSource to get data for the View.
*  **Model** - this is where the business logic happens, data fetching, data mapping and error handling. In a more complex scenario could be integrated with a Repository pattern for introducing local storage. At the moment it fetches data only from the network repository.

## Design UI/UX
The design is based on CardViews for a single item of the list (Title, Overview, Image and average rating).

![CardView](/screenshot/card.png)

Swipe to Refresh for letting the user refresh data as well as providing ad initial loading state on an empty list.

![Swipe to Refresh](/screenshot/swipe_to_refresh.png)

Loading State for next page loading. This should be shown only on slow connections since there is a pre-fetching mechanism.

![Loading](/screenshot/loading.png)

Snackbar for error state with a Retry button for re-fetching the latest page.

![Snackbar](/screenshot/snackbar.png)

## License
```
Copyright 2019 Paolo Brandi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
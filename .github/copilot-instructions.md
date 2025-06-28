# Chat AI

We are developing an application to list real estate funds and calculate their ceiling price

To do this, the user will initially have a screen for listing real estate funds along with a search box for filtering by specific funds.

The user will be able to access the details of the real estate fund, showing the name, current price, dividend yield, type of fund and last dividends paid.

## Technologies

These are the technologies that we are going to use for each of the functionalities
- Jetpack Compose as an interface system.
- Material 3 as design system.
- Kotlin as programming language.
- Architecture Components ViewModels for the communication between the UI and the data layer.
- Hilt as dependency injector.
- Mockk as a mocking library for unit tests.
- For persistence of conversations, we will use Room. Remember to use KSP (current version is 2.1.20-1.0.32) and not KAPT for dependencies that generate code, such as Room's compiler.

## Architecture
We are going to use a simple architecture, where we will have:
- The UI in Compose
- Communication with the data layer via MVVM
- The data layer will consist of repositories, which will hide which specific libraries are being used.

## Extra rules

- Whenever you finish generating a code, compile it to see that there are no problems. To do this, use compileDebugKotlin.
- Even if you think the build.gradle.kts are wrong, the ones you have right now in the context are valid. If you have to modify the libs.versions or the gradle files, just add what you need to add, and don't modify what already exists.
- Do not include comments just to explain what the code already does. Just in case there is some part that stays like that because in the future you need to add new functionality.
- After executing compileDebugKotlin, if you see that there are no problems, you can create unit tests for the code you just generated.
- Method names for unit tests do not need to be longer than 120 characters.
- Use Given-When-Then for unit tests.
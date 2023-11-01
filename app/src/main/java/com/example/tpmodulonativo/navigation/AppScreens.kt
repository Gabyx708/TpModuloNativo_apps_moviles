package com.example.tpmodulonativo.navigation

//definir aca las pantallas que se usen

sealed class AppScreens(val route: String){
    object AuthScreen : AppScreens("Auth")
    object HomeScreen : AppScreens("Home")
    object RegisterFormScreen : AppScreens("Register")
}

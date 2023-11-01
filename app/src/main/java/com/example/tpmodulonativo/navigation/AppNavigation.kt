package com.example.tpmodulonativo.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpmodulonativo.Controllers.AuthActivity
import com.example.tpmodulonativo.Controllers.RegisterActivity
import com.example.tpmodulonativo.screens.AuthScreen
import com.example.tpmodulonativo.screens.RegisterScreen

/*elemento que orquesta la navigacion , amo y señor de
las pantallas*/

@Composable
fun AppNavigation(){
    val navController = rememberNavController() //gestiona el estado de navegacion entre pantallas
    
    NavHost(navController = navController,startDestination = AppScreens.AuthScreen.route){
        composable(route = AppScreens.AuthScreen.route){
            AuthScreen(AuthActivity(navController),navController)
        }

        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(route = AppScreens.RegisterFormScreen.route){

            val register = RegisterActivity()
            RegisterScreen(navController,register,register)
        }
    }
}
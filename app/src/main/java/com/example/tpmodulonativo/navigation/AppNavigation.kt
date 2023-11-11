package com.example.tpmodulonativo.navigation

import HomeScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpmodulonativo.Controllers.AuthActivity
import com.example.tpmodulonativo.Controllers.MakeDonationActivity
import com.example.tpmodulonativo.Controllers.RegisterActivity
import com.example.tpmodulonativo.screens.AuthScreen
import com.example.tpmodulonativo.screens.MakeDonationScreen
import com.example.tpmodulonativo.screens.RegisterScreen

/*elemento que orquesta la navigacion , amo y señor de
las pantallas*/


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavigation(){
    val navController = rememberNavController() //gestiona el estado de navegacion entre pantallas

    val authActivity =AuthActivity(navController, LocalContext.current)

    NavHost(navController = navController,startDestination = AppScreens.AuthScreen.route){
        composable(route = AppScreens.AuthScreen.route){
            AuthScreen(authActivity,navController)
        }

        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(route = AppScreens.RegisterFormScreen.route){

            val register = RegisterActivity(navController)
            RegisterScreen(navController,register,register)
        }
        composable(route = AppScreens.MakeDonationScreen.route){
            MakeDonationScreen(navController,MakeDonationActivity())
        }
    }
}
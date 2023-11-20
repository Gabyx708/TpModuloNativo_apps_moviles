package com.example.tpmodulonativo.Controllers

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.tpmodulonativo.interfaces.ISessionUserHandler
import com.example.tpmodulonativo.navigation.AppScreens
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.auth.FirebaseAuth

class AuthActivity(private val navController: NavController,context: Context): ComponentActivity() , ISessionUserHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TpModuloNativoTheme {
            }
        }

    }

    override fun signUp(user: String, passw: String, context: Context) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user, passw)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    navController.navigate(route = AppScreens.HomeScreen.route);
                    Toast.makeText(context,"Sesión iniciada correctamente",Toast.LENGTH_SHORT).show()
                } else {
                    SignUpFail("Usuario o password incorrecto",context)
                }
            }
    }

    override fun SignUpFail(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



}



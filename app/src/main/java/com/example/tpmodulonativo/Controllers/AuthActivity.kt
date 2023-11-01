package com.example.tpmodulonativo.Controllers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.tpmodulonativo.interfaces.ISessionUserHandler
import com.example.tpmodulonativo.navigation.AppScreens
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.auth.FirebaseAuth

class AuthActivity(private val navController: NavController): ComponentActivity() , ISessionUserHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TpModuloNativoTheme {
            }
        }

    }

    override fun signUp(user:String,passw:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user,passw)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    navController.navigate(route = AppScreens.HomeScreen.route);
                }else{
                    navController.navigate(route= AppScreens.AuthScreen.route)
                }
            }
    }




    private fun showToast(message: String) {
        val context = this // El contexto de la actividad
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showHome(email: String,provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
            startActivity(homeIntent)
        }
    }



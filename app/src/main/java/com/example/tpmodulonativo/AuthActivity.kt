package com.example.tpmodulonativo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tpmodulonativo.interfaces.ISessionUserHandler
import com.example.tpmodulonativo.screens.AuthScreen
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : ComponentActivity() , ISessionUserHandler {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TpModuloNativoTheme {
               AuthScreen(this)
            }
        }

    }

    override fun SignUp(user:String,passw:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user,passw)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    showAlert("ocurrio un problema","Ups!!!")
                }
            }
    }



    private fun showAlert(message:String,title:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String,provider: ProviderType){
        val homeIntent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
            startActivity(homeIntent)
        }
    }



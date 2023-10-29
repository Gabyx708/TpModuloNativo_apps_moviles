package com.example.tpmodulonativo

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Paint.Style
import android.graphics.fonts.Font
import android.graphics.fonts.FontStyle
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.MagnifierStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tpmodulonativo.interfaces.SessionUserHandler
import com.example.tpmodulonativo.screens.AuthScreen
import com.example.tpmodulonativo.screens.RegistrationForm
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : ComponentActivity() , SessionUserHandler {


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



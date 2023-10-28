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
import com.example.tpmodulonativo.screens.AuthScreen
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : ComponentActivity() {

    var btnSignUp: Button? = null
    var btnLogin : Button? = null
    var emailText: EditText? = null
    var passwordText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TpModuloNativoTheme {
               AuthScreen()
            }
        }

        // Inicializa las vistas
        btnSignUp = findViewById(R.id.singUpBtn)
        emailText = findViewById(R.id.EmailText)
        passwordText = findViewById(R.id.providerText)
        btnLogin = findViewById(R.id.loginBtn)

        //setup
        setup()
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
    @Composable
    fun PrevieUi(){
        Column {
            MyComponent()
            MyComponent()
            MyComponent()
            MyComponent()
            MyComponent()
            MyComponent()
        }
    }

    @Composable
    fun MyComponent(){
        Row(modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)){
            MyImage()
            MyTexts()
        }
    }

    @Composable
    fun MyImage(){
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            "Mi imagen",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.Green)
            )
    }

@Composable
fun MyTexts(){

    //habilita el scroll de la pantalla
    val scrollState = rememberScrollState()
    Column (modifier = Modifier.verticalScroll(scrollState)){
        MyText("vieja",MaterialTheme.colorScheme.onBackground,
            MaterialTheme.typography.titleSmall)
        MyText("chota",MaterialTheme.colorScheme.primary,
            MaterialTheme.typography.titleLarge)
    }
}
    @Composable
    fun MyText(text:String,color:Color,style: TextStyle){
        Text(text,color = color, style = style)
    }
    private fun setup() {
        title = "Autenticación"

        btnSignUp?.setOnClickListener {
            if (emailText != null && passwordText != null) {
                if (emailText!!.text.isNotEmpty() && passwordText!!.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText!!.text.toString(),
                        passwordText!!.text.toString()).addOnCompleteListener{
                            if(it.isSuccessful){
                                Log.d("login success",emailText!!.text.toString())
                                showHome(it.result?.user?.email?: "",ProviderType.BASIC)
                            }else{
                                Log.d("login error",emailText!!.text.toString()+passwordText!!.text.toString())
                                showAlert()
                            }
                    }

                }
            }
        }

        btnLogin?.setOnClickListener {
            if (emailText != null && passwordText != null) {
                if (emailText!!.text.isNotEmpty() && passwordText!!.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText!!.text.toString(),
                        passwordText!!.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email?: "",ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }

                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("ocurrio una catastrofe")
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



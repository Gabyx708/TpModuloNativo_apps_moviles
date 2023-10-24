package com.example.tpmodulonativo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : ComponentActivity() {

    var btnSignUp: Button? = null
    var btnLogin : Button? = null
    var emailText: EditText? = null
    var passwordText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Inicializa las vistas
        btnSignUp = findViewById(R.id.singUpBtn)
        emailText = findViewById(R.id.EmailText)
        passwordText = findViewById(R.id.providerText)
        btnLogin = findViewById(R.id.loginBtn)

        //setup
        setup()
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



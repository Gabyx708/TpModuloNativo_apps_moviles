package com.example.tpmodulonativo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class  ProviderType{
    BASIC
}
class HomeActivity : AppCompatActivity() {

    var EmailText : TextView? = null
    var providerText : TextView? = null
    var logOut : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        EmailText = findViewById(R.id.EmailText)
        providerText = findViewById(R.id.providerText)
        logOut = findViewById(R.id.logOutBtn)
        //setup
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String?= bundle?.getString("provider")
        setup(email?: "",provider?:"")
    }

    private fun setup(email: String?, provider: String?){
        title = "Inicio"

        EmailText?.text = email
        providerText?.text = provider

        logOut?.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }
}
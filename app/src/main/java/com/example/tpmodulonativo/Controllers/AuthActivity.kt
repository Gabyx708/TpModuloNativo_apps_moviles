package com.example.tpmodulonativo.Controllers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.example.tpmodulonativo.Repositories.Preferences
import com.example.tpmodulonativo.Repositories.UserRepository
import com.example.tpmodulonativo.interfaces.ISessionUserHandler
import com.example.tpmodulonativo.navigation.AppScreens
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class AuthActivity(private val navController: NavController,context: Context): ComponentActivity() , ISessionUserHandler {

    var db = Firebase.firestore
    var repository = UserRepository(db)
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
                    var preferences = Preferences(context)

                    val task = repository.GetUserByEmail(user)

                    task.addOnSuccessListener { result ->
                        if (result != null) {
                            preferences.saveName(result.name)
                            Log.d("GEOPOINT",result.ubication.toString())
                            Log.d("USUARIO_NO_NULO",result.toString())
                        } else {
                            preferences.saveName("algo fallo")
                            Log.d("USUARIO_RECUPERADO",task.toString())
                        }
                    }.addOnFailureListener { exception ->
                        // Manejar errores aquí
                        preferences.saveName("algo fallo: $exception")
                    }



                    Toast.makeText(context,"usuario creado exitosamente",Toast.LENGTH_SHORT).show()
                } else {
                    SignUpFail("usuario o password incorrecto",context)
                }
            }
    }

    override fun SignUpFail(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



}



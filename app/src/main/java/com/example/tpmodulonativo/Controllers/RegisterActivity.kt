package com.example.tpmodulonativo.Controllers

import GeoPoint
import LocationActivity
import android.content.ContentValues.TAG
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.User
import com.example.tpmodulonativo.Repositories.UserRepository
import com.example.tpmodulonativo.interfaces.ICreateUserHandler
import com.example.tpmodulonativo.interfaces.IGeoManager
import com.example.tpmodulonativo.interfaces.IUserRepository
import com.example.tpmodulonativo.navigation.AppScreens
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class RegisterActivity (private val navController: NavController): AppCompatActivity() , IGeoManager,ICreateUserHandler{

    private var locationManager : LocationManager? = null
    private var authService = Firebase.auth
    private val db = Firebase.firestore
    private val userRepository : IUserRepository = UserRepository(db)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TpModuloNativoTheme {

            }
        }

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    override fun registerUser(newUser : User){
        Log.d(TAG, "Antes de insertar en Firestore")
        try {
            userRepository.InsertUser(newUser)
            Log.d(TAG, "Después de insertar en Firestore")
            createAccount(newUser.email,newUser.password)
        }catch(e:Exception){

            return
        }

    }

    override fun getUserLocation(context: Context): GeoPoint {
        Log.d("GEOMANAGER", "EJECUCION IN PROGRESS..")

        var geoPoint: GeoPoint? = null

        val locationActivity = LocationActivity()
        locationActivity.conseguirUbicacion(context) { geoPointObtenido ->
            geoPoint = geoPointObtenido
        }

        return geoPoint!!
    }



    private fun createAccount(email: String, password: String) {
        authService.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        Log.d(TAG, "createUserWithEmail:success, userId: ${user.uid}")
                        navController.navigate(route = AppScreens.AuthScreen.route)
                    } else {
                        Log.w(TAG, "createUserWithEmail:success, pero el usuario es nulo")
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

}
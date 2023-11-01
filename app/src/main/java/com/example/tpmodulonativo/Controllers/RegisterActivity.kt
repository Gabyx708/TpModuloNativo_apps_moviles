package com.example.tpmodulonativo.Controllers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tpmodulonativo.Models.User
import com.example.tpmodulonativo.Repositories.UserRepository
import com.example.tpmodulonativo.interfaces.ICreateUserHandler
import com.example.tpmodulonativo.interfaces.IGeoManager
import com.example.tpmodulonativo.interfaces.IUserRepository
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() , IGeoManager,ICreateUserHandler{

    private var locationManager : LocationManager? = null
    private val REQUEST_LOCATION_PERMISSION = 1

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
        userRepository.InsertUser(newUser)
    }
    override fun getUserLocation(): GeoPoint? {
        Log.d("GEOMANAGER","EJECUCION IN PROGRESS..")
        if (locationManager != null) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
                val locationProvider = LocationManager.GPS_PROVIDER
                val lastKnownLocation = locationManager?.getLastKnownLocation(locationProvider)

                if (lastKnownLocation != null) {
                    val latitude = lastKnownLocation.latitude
                    val longitude = lastKnownLocation.longitude
                    Log.d("LATITUD",latitude.toString())
                    return GeoPoint(latitude, longitude)
                } else {
                    return null // No se pudo obtener la ubicación
                }
            } else {
                // No se tienen permisos de ubicación, manejar el caso
                return null
            }
        } else {
            // El servicio de ubicación no está disponible, manejar el caso
            return null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // si tengo el permiso
                getUserLocation()

            } else {
                // si no tengo el permiso la aplicacion se cierra
                Log.d("FALLO CRITICO","AAAYAYAYAY")
            }
        }
    }
}
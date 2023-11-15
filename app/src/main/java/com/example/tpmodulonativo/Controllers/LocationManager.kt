
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.firestore.GeoPoint

object LocationManagerUtil {

    private var locationListener: LocationListener? = null
    private  var locationManager : LocationManager? = null
    private var lastKnownLocation: Location? = null

    @SuppressLint("MissingPermission")
    fun provideLocationUpdates(
        context: Context,
        onLocationChanged: (Location) -> Unit
    ): GeoPoint? {
        // Verificar y solicitar permisos de ubicación
        if (checkAndRequestLocationPermissions(context)) {
            val locationManager = ContextCompat.getSystemService(
                context,
                LocationManager::class.java
            )

            locationListener = object : LocationListener {
                override fun onLocationChanged(newLocation: Location) {
                    lastKnownLocation = newLocation
                    onLocationChanged(newLocation)
                    Log.d("GEOMANAGER", "Location Updated: ${newLocation.latitude}, ${newLocation.longitude}")
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000, // 10 seconds
                0f,    // 0 meters
                locationListener!!,
                Looper.getMainLooper()
            )

            // Devuelve la ubicación como GeoPoint
            return lastKnownLocation?.let { GeoPoint(it.latitude, it.longitude) }
        } else {
            // Permiso de ubicación denegado, puedes manejarlo de acuerdo a tus necesidades
            // Por ejemplo, mostrar un mensaje al usuario o realizar alguna acción alternativa.
            return null
        }
    }

    fun getLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    }


    private fun checkLocationPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}




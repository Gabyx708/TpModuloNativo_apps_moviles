import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class LocationActivity : AppCompatActivity() {

    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    // Función para obtener la ubicación en tiempo real
    fun conseguirUbicacion(context: Context, callback: (GeoPoint?) -> Unit) {
        Log.d("FUNCION", "UBICACION")

        // Checa que los permisos estén garantizados
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1000
            )
        } else {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        // Cuando la ubicación cambia, se llama a este método
                        val geoPoint = GeoPoint(location.latitude, location.longitude)
                        callback(geoPoint)
                        // Detener las actualizaciones después de obtener la ubicación
                        locationManager?.removeUpdates(this)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String) {}

                    override fun onProviderDisabled(provider: String) {}
                }
            )
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
                conseguirUbicacion(this) { geoPoint ->
                    if (geoPoint != null) {
                        Log.d("LATITUD", geoPoint.latitude.toString())
                        Log.d("LONGITUD", geoPoint.longitude.toString())
                    } else {
                        Log.d("NULO", "ADVERTENCIA ES NULOO")
                    }
                }
            } else {
                finish()
            }
        }
    }
}

data class GeoPoint(val latitude: Double, val longitude: Double)

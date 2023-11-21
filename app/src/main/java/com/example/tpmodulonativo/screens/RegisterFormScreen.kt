package com.example.tpmodulonativo.screens

import GeoPoint
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.User
import com.example.tpmodulonativo.interfaces.ICreateUserHandler
import com.example.tpmodulonativo.interfaces.IGeoManager
import java.text.SimpleDateFormat
import java.util.Calendar


@Composable
fun RegisterScreen(navController: NavController,createUserHandler: ICreateUserHandler,geoManager: IGeoManager){
        RegistrationForm(createUserHandler,geoManager, LocalContext.current )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm(createUserHandler: ICreateUserHandler,geoManager:IGeoManager,context:Context) {
    var firstName by remember { mutableStateOf(TextFieldValue()) }
    var lastName by remember { mutableStateOf(TextFieldValue()) }
    var birthDate by remember { mutableStateOf(TextFieldValue()) }
    var nickname by remember { mutableStateOf(TextFieldValue()) }
    var Email by remember{ mutableStateOf(TextFieldValue())}
    var password by remember{ mutableStateOf(TextFieldValue())}
    var passwordRepeat by remember{ mutableStateOf(TextFieldValue())}
    var location by remember{ mutableStateOf(GeoPoint(0.0,0.0))}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            shape = RoundedCornerShape(10.dp),
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = Email,
            onValueChange = { Email = it },
            label = { Text("Correo electrónico") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        var dateStr = DatePicker()

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Apodo") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        TextField(
            shape = RoundedCornerShape(10.dp),
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = { Text("Repetir contraseña") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                if(nickname.text == "" || firstName.text ==""){
                    Toast.makeText(context,"usuario o apodo vacios",Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(passwordRepeat != password){
                    Toast.makeText(context,"ambas contraseñas deben ser iguales",Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(passwordRepeat.text.length < 8){
                    Toast.makeText(context,"la contraseña deben tener al menos 8 caracteres",Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(dateStr == ""){
                    Toast.makeText(context,"ingresa una fecha",Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(Email.text == ""){
                    Toast.makeText(context,"ingresa un mail valido",Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val ubicacionUsuario = obtenerUbicacionActual(context)
                val user = User(
                    nickName = nickname.text,
                    name = firstName.text,
                    lastName = lastName.text,
                    email = Email.text,
                    birthday = SimpleDateFormat("dd/MM/yyyy").parse(dateStr),
                    geoPoint = ubicacionUsuario,
                    password = password.text
                )

                registerUser(user,createUserHandler)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}

@SuppressLint("MissingPermission")
fun obtenerUbicacionActual(context: Context): GeoPoint {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (location != null) {
            return GeoPoint(location.latitude, location.longitude)
        } else {
            Log.e("Ubicacion", "No se pudo obtener la ubicación actual.")
        }
    } else {
        Log.e("Ubicacion", "El GPS no está habilitado.")
    }

    Log.w("Ubicacion", "No se pudo obtener la ubicación actual. Se devuelve el valor por defecto.")
    return GeoPoint(0.0, 0.0)
}

private fun registerUser(user:User,createUserHandler: ICreateUserHandler){
        Log.d("TEST USER",user.toString())
        createUserHandler.registerUser(user)
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DatePicker() : String{
    var date by rememberSaveable{ mutableStateOf("")}
    val year:Int
    val month:Int
    val day:Int
    val calendar : Calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _Datepicker,year:Int,month:Int,day:Int ->
            date = "$day/${month+1}/$year"
        },year,month,day
    )

    Box(modifier = Modifier
        .fillMaxWidth()){
            Row(modifier = Modifier.align(Alignment.Center).padding(19.dp)){
                    OutlinedTextField(
                        modifier = Modifier.width(150.dp),
                        value = date
                        , onValueChange = {date = it}
                        , readOnly = true,
                        label = { Text(text = "select date")}
                    )
                    Icon(
                        imageVector = Icons.Filled.DateRange ,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(4.dp)
                            .clickable {
                                mDatePickerDialog.show()
                            })
            }
    }
        return  date
}


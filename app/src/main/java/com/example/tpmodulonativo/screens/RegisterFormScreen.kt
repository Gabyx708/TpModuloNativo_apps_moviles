package com.example.tpmodulonativo.screens

import android.app.DatePickerDialog
import android.content.Context
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
import com.google.firebase.firestore.GeoPoint
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
    var location by remember { mutableStateOf(TextFieldValue()) }
    var Email by remember{ mutableStateOf(TextFieldValue())}
    var password by remember{ mutableStateOf(TextFieldValue())}
    var passwordRepeat by remember{ mutableStateOf(TextFieldValue())}


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
            label = { Text("correo electrinico") }
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
            label = { Text("password") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        TextField(
            shape = RoundedCornerShape(10.dp),
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = { Text("repetir password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { geoManager.getUserLocation()},
            modifier = Modifier.width(300.dp)
        ) {

            Text("tomar ubicacion")
        }

        Spacer(modifier = Modifier.height(16.dp))

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

                val user = User(
                    nickName = nickname.text,
                    name = firstName.text,
                    lastName = lastName.text,
                    email = Email.text,
                    birthday = SimpleDateFormat("dd/MM/yyyy").parse(dateStr),
                    geoPoint = GeoPoint(34.34343434,32.34343434),
                    password = password.text
                )

                registerUser(user,createUserHandler)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
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


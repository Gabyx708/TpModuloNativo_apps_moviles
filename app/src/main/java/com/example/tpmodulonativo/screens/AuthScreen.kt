@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tpmodulonativo.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tpmodulonativo.interfaces.ISessionUserHandler
import com.example.tpmodulonativo.navigation.AppScreens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(ButtonHandler : ISessionUserHandler,navController: NavController) {

    val usuarioState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold() {
        BodyContent(ButtonHandler,usuarioState,passwordState,navController)
    }
}


@Composable
fun BodyContent(ButtonHandler : ISessionUserHandler, usuarioState: MutableState<TextFieldValue>, passwordState: MutableState<TextFieldValue>,navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        InputUserField(usuarioState)
        Spacer(modifier = Modifier.height(8.dp))
        InputPasswordField(passwordState)
        Spacer(modifier = Modifier.height(8.dp))
        ButtonsGroup(ButtonHandler,usuarioState.value.text,passwordState.value.text, navController)
    }
}

@Composable
fun InputUserField(usuarioState: MutableState<TextFieldValue>){

    TextField(
        value = usuarioState.value,
        onValueChange = {usuarioState.value = it},
        label = { Text("usuario")}
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun InputPasswordField(passwordState: MutableState<TextFieldValue>) {

    var passVisible by remember{ mutableStateOf(false)}

    val icon = if(passVisible)
                    painterResource(id = com.google.android.material.R.drawable.design_ic_visibility)
                else
                    painterResource(id = com.google.android.material.R.drawable.design_ic_visibility_off)

        OutlinedTextField(value = passwordState.value, onValueChange ={passwordState.value = it},
            label = { Text("password")},
            placeholder = {Text(text="password aqui")},
            trailingIcon = {
                IconButton(onClick = { passVisible = !passVisible }) {
                    Icon(painter =icon, contentDescription = "eye")
                    }
                },
                visualTransformation = if(passVisible) VisualTransformation.None
                            else PasswordVisualTransformation()
            )
}

@Composable
fun ButtonsGroup(ButtonHandler : ISessionUserHandler, usuario:String, password:String,navController: NavController){



    Row {

        Button(onClick ={ButtonHandler.signUp(usuario,password)}) {
            Text(text = "inciar sesion")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { navController.navigate(AppScreens.RegisterFormScreen.route) }) {
            Text(text = "registrame")
        }
    }
}



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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen() {
    Scaffold() {
        BodyContent()
    }
}


@Composable
fun BodyContent(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        InputUserField()
        Spacer(modifier = Modifier.height(8.dp))
        InputPasswordField()
        Spacer(modifier = Modifier.height(8.dp))
        ButtonsGroup()
    }
}

@Composable
fun InputUserField(){

    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("utiliza tu correo electronico", TextRange(0, 10)))
    }

    TextField(
        value = text,
        onValueChange = {text = it},
        label = { Text("usuario")}
    )
}

@Composable
fun InputPasswordField(){

    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("tu password aqui", TextRange(0, 10)))
    }

    TextField(
        value = text,
        onValueChange = {text = it},
        label = { Text("password")}
    )
}


@Composable
fun ButtonsGroup(){

    Row {

        Button(onClick = { /*TODO*/ }) {
            Text(text = "inciar sesion")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "registrame")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview(){
    AuthScreen()
}
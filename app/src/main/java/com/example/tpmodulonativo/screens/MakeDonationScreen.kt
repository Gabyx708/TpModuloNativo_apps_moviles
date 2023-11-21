package com.example.tpmodulonativo.screens

import DecorativeBar
import UserProfile
import android.app.NotificationManager
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.Repositories.Preferences
import com.example.tpmodulonativo.interfaces.ICreateDonations
import com.example.tpmodulonativo.navigation.AppScreens

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MakeDonationScreen(navController: NavController, createDonations: ICreateDonations) {
    var donationName by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var observations by remember { mutableStateOf(TextFieldValue()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current

    val activity = (navController.context as AppCompatActivity)
    var preferences = Preferences(context)
    val NOMBRE_USUARIO = preferences.getName()

    val galleryLauncher: ActivityResultLauncher<Array<String>> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            activity.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            val selectedImageBitmap = loadBitmapFromUri(activity, uri)
            imageBitmap = selectedImageBitmap
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = {
                Text(text = "Hacer donación")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserProfile(NOMBRE_USUARIO)

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = donationName,
            onValueChange = { donationName = it },
            label = { Text("Nombre de la donación") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción de la donación") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            shape = RoundedCornerShape(10.dp),
            value = observations,
            onValueChange = { observations = it },
            label = { Text("Observaciones") }
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        galleryLauncher.launch(arrayOf("image/*"))
                    }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.subir),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        galleryLauncher.launch(arrayOf("image/*"))
                    }
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                // Abrir la galería para seleccionar una imagen
                galleryLauncher.launch(arrayOf("image/*"))
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text("Subir imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        var preferences = Preferences(context)
        val UBICACION_USUARIO = preferences.getLocation()
        val MAIL_USUARIO = preferences.getMail()
        Button(
            onClick = {
                // Lógica para publicar la donación
                val donation = Donation(
                    name = donationName.text,
                    description = description.text,
                    observations = observations.text,
                    imageUri = imageUri.toString(), //? revisar
                    estado = true,
                    ubication = UBICACION_USUARIO,
                    user = MAIL_USUARIO
                )

                createDonations.createDonation(donation)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationManager = context.getSystemService(NotificationManager::class.java)
                    if (notificationManager?.getNotificationChannel("channelId") == null) {
                        createNotificationChannel(context, "channelId")
                    }
                }
                sendNotification(context, donation?.name ?: "", "¡Tu donación ha sido publicada!","Ya está disponible la donación: ")

                navController.navigate(AppScreens.HomeScreen.route)
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text("Publicar donación")
        }
        Spacer(modifier = Modifier.weight(1f))
        DecorativeBar()
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun loadBitmapFromUri(activity: AppCompatActivity, uri: Uri): ImageBitmap? {
    return try {
        // Solicita permisos para acceder a la URI
        activity.contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )

        val source = ImageDecoder.createSource(activity.contentResolver, uri)
        ImageDecoder.decodeBitmap(source).asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


package com.example.tpmodulonativo.screens

import DecorativeBar
import GeoPoint
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.Repositories.DonationsRepository
import com.example.tpmodulonativo.Repositories.Preferences
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun DonationDetailScreen(navController: NavController, donationId: String) {
    val donationsRepository = DonationsRepository(FirebaseFirestore.getInstance())
    var donation by remember { mutableStateOf<Donation?>(null) }
    val context = LocalContext.current

    LaunchedEffect(donationId) {
        try {
            val result = donationsRepository.GetDonation(donationId).await()
            donation = result
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (donation != null) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopAppBar(
                title = {
                    Text(text = "Donación")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                bitmap = loadImageFromUri(LocalContext.current, Uri.parse(donation!!.imageUri))
                    ?: ImageBitmap.imageResource(R.drawable.donation_item),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Nombre: ${donation!!.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp))

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Descripción: ${donation!!.description}",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp))

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Observaciones: ${donation!!.observations}",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp))

            Spacer(modifier = Modifier.height(10.dp))

            donation?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                ) {
                    LocationView(geoPoint = donation!!.ubication)
                }
            }

            Spacer(modifier = Modifier.weight(1f),)

            var preferences = Preferences(context)
            val MAIL_USUARIO = preferences.getMail()

            if (donation!!.user == MAIL_USUARIO) {
                Button(
                    onClick = {
                        if (donation!!.estado) {
                            donation!!.estado = false
                            try {
                                donationsRepository.UpdateDonationState(donationId, false)
                                sendNotification(context, donation?.name ?: "", "¡Tu publicación ha sido retirada!","Se retiró del listado la donación: ")
                                navController.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            donation!!.estado = true
                            try {
                                donationsRepository.UpdateDonationState(donationId, true)
                                sendNotification(context, donation?.name ?: "", "¡Tu publicación volvió a estar activa!","Se agregó al listado la donación: ")
                                navController.popBackStack()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(if (donation!!.estado) "Retirar publicación" else "Volver a publicar")
                }
            } else {
                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val notificationManager =
                                context.getSystemService(NotificationManager::class.java)
                            if (notificationManager?.getNotificationChannel("channelId") == null) {
                                createNotificationChannel(context, "channelId")
                            }
                        }
                        sendNotification(
                            context,
                            donation?.name ?: "",
                            "¡Alguien se quiere poner en contacto contigo!",
                            "Interesado en la donación: "
                        )

                        val emailAddress = donation?.user ?: ""

                        val intent = Intent(Intent.ACTION_SEND)
                            .setData(Uri.parse("mailto:$emailAddress"))
                            .setType("text/plain")
                            .putExtra(
                                Intent.EXTRA_TEXT,
                                "Hola, estoy interesado en la donación: ${donation!!.name}. ¡Me sería de gran ayuda!"
                            )
                            .putExtra(
                                Intent.EXTRA_SUBJECT,
                                "Interés en la donación: ${donation!!.name}"
                            )
                            .putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))

                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(
                                context,
                                "No hay aplicación de mensajería disponible",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text("Ponte en contacto")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            DecorativeBar()
        }
    } else {
        Text("Error al cargar los detalles de la donación.")
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun loadImageFromUri(context: Context, uri: Uri): ImageBitmap? {
    return try {
        val contentResolver = context.contentResolver
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source).asImageBitmap()
    } catch (e: Exception) {
        Log.e("DonationCard", "Error loading image from URI: $uri", e)
        null
    }
}

@Composable
fun LocationView(geoPoint: GeoPoint) {
    val mapUrl = "https://www.openstreetmap.org/export/embed.html?bbox=${geoPoint.longitude - 0.005},${geoPoint.latitude - 0.005},${geoPoint.longitude + 0.005},${geoPoint.latitude + 0.005}&layer=mapnik&marker=${geoPoint.latitude},${geoPoint.longitude}"
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                setBackgroundColor(0)
                loadUrl(mapUrl)
            }
        },
        update = { webView ->
            webView.loadUrl(mapUrl)
        }
    )
}

@SuppressLint("MissingPermission")
fun sendNotification(context: Context, donationName: String, title: String, text: String) {
    try {
        val channelId = "channelId"
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            if (notificationManager?.getNotificationChannel(channelId) == null) {
                createNotificationChannel(context, channelId)
            }
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText("$text $donationName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    } catch (e: Exception) {
        Log.e("NotificationError", "Error al enviar la notificación: ${e.message}", e)
    }
}

fun createNotificationChannel(context: Context, channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Channel Name",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel Description"
            enableLights(true)
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
package com.example.tpmodulonativo.screens

import DecorativeBar
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
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
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.Repositories.DonationsRepository
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
                    ?: ImageBitmap.imageResource(R.drawable.donation_item), // Usa un recurso predeterminado si la imagen es nula
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Nombre: ${donation!!.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Descripción: ${donation!!.description}",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Observaciones: ${donation!!.observations}",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp))

            Spacer(modifier = Modifier.weight(1f),)

            Button(
                onClick = {
                    val emailAddress = "vnyedro@gmail.com"

                    val intent = Intent(Intent.ACTION_SEND)
                        .setData(Uri.parse("mailto:$emailAddress"))
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, "Hola, estoy interesado en la donación: ${donation!!.name}. ¡Me sería de gran ayuda!")
                        .putExtra(Intent.EXTRA_SUBJECT, "Interés en la donación: ${donation!!.name}")
                        .putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))

                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "No hay aplicación de mensajería disponible", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text("Ponte en contacto")
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
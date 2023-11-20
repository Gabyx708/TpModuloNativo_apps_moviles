package com.example.tpmodulonativo.screens

import DecorativeBar
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.Repositories.DonationsRepository
import com.example.tpmodulonativo.navigation.AppScreens
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SearchDonationScreen(navController: NavController) {
    val donationsRepository = DonationsRepository(FirebaseFirestore.getInstance())
    var donationsList by remember { mutableStateOf<List<Donation>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val result = donationsRepository.GetAllDonations().await()
            donationsList = result
        } catch (e: Exception) {
            e.printStackTrace()
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
                Text(text = "Donaciones")
            }
        )
        LazyColumn {
            items(donationsList) { donation ->
                DonationCard(donation = donation) {
                    navController.navigate("${AppScreens.DonationDetailScreen.route}/${donation.id}")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DecorativeBar()
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun DonationCard(donation: Donation, onCardClick: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onCardClick() }
        ) {
            val imageBitmap = loadImageFromUri(context, Uri.parse(donation.imageUri))
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            } else {
                Image(
                    painter = painterResource(id = R.drawable.donation_item),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Nombre: ${donation.name}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Descripción: ${donation.description}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Observaciones: ${donation.observations}")
            }
        }
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
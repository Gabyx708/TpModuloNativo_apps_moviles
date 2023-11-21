package com.example.tpmodulonativo.screens

import DecorativeBar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.Repositories.DonationsRepository
import com.example.tpmodulonativo.Repositories.Preferences
import com.example.tpmodulonativo.navigation.AppScreens
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MyDonationsScreen(navController: NavController) {
    val donationsRepository = DonationsRepository(FirebaseFirestore.getInstance())
    var donationsList by remember { mutableStateOf<List<Donation>>(emptyList()) }
    val context = LocalContext.current

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
        var preferences = Preferences(context)
        val MAIL_USUARIO = preferences.getMail()
        LazyColumn {
            val userDonations = donationsList.filter { it.user == MAIL_USUARIO }
            if (userDonations.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Todavía no hiciste donaciones",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(userDonations) { donation ->
                    DonationCard(donation = donation) {
                        navController.navigate("${AppScreens.DonationDetailScreen.route}/${donation.id}")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DecorativeBar()
    }
}
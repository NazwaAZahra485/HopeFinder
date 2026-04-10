package com.nazwa.hopefinder.ui.pages

import android.R.attr.onClick
import android.widget.Button
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nazwa.hopefinder.viewmodel.SosViewModel
import java.lang.reflect.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSosScreen(sosViewModel: SosViewModel, navController: NavController){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "HopeFinder") }
            )
            // TODO: Add top bar for notification
            // Notification: notif ada bencana di mana, tgl jam, SRnya (gempa)
        }
        HomeSosContent(sosViewModel, navController, Modifier.padding(innerPadding))
    )
}

@Composable
fun HomeSosContent(sosViewModel: SosViewModel, navController: NavController, modifier: Modifier){
    // Tombol SOS disimpan di tengah
    Button(
        onClick = onClick,// Konekin ke nomor emergency + nyalain gps
        modifier = modifier.size(64.dp),
        shape = CircleShape
    ) {
        Text(text = "SOS")
    }
}
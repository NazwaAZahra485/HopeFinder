package com.nazwa.hopefinder.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

private const val EMERGENCY_NUMBER = "*888#"

@Composable
fun rememberEmergencyCallLauncher(): () -> Unit {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            makeCall(context)
        } else {
            Toast.makeText(context, "Izin panggilan ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    return remember {
        {
            val alreadyGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED

            if (alreadyGranted) {
                makeCall(context)
            } else {
                permissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }
}

private fun makeCall(context: Context) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = "tel:$EMERGENCY_NUMBER".toUri()
    }
    context.startActivity(intent)
}

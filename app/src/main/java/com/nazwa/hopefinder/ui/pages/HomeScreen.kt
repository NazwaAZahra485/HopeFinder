package com.nazwa.hopefinder.ui.pages

// TODO: Tambahin API map

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import com.nazwa.hopefinder.viewmodel.HomeViewModel

// ── Brand colors ──────────────────────────────────────────────────────────────
val RedPrimary   = Color(0xFFE21919)
val BrownNeutral = Color(0xFFC1B4AE)
val SurfaceDark  = Color(0xFF353238)
val OnSurfaceLight = Color(0xFFECE6E0)
val SubtleGray   = Color(0xFF7A6B65)

// ── Entry point ───────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceDark)
    ) {
        // ── Map layer — replace with your MapsSDK composable ─────────────────
        MapLayer(modifier = Modifier.fillMaxSize())

        // ── Overlay column ────────────────────────────────────────────────────
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTopBar(
                onMenuClick   = { viewModel.onMenuClick() },
                onBellClick   = { viewModel.onBellClick() },
                hasUnread     = uiState.hasUnreadNotification,
            )

            if (uiState.earthquakeDetected) {
                EarthquakeAlertBanner(
                    location  = uiState.earthquakeLocation,
                    depthKm   = uiState.earthquakeDepthKm,
                    magnitude = uiState.earthquakeMagnitude,
                    modifier  = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                )
            }

            DirectionCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp),
                onAlreadyAtPointClick = { viewModel.onAlreadyAtAssemblyPoint() },
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        // ── Map controls ──────────────────────────────────────────────────────
        MapFabColumn(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            onZoomIn      = { viewModel.onZoomIn() },
            onZoomOut     = { viewModel.onZoomOut() },
            onSavePin     = { viewModel.onSavePin() },
            onMyLocation  = { viewModel.onMyLocation() },
        )

        // ── SOS button ────────────────────────────────────────────────────────
        SosFab(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { viewModel.onSosClick() },
        )
    }
}

// ── Top bar ───────────────────────────────────────────────────────────────────
@Composable
private fun HomeTopBar(
    onMenuClick: () -> Unit,
    onBellClick: () -> Unit,
    hasUnread: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(Icons.Outlined.Menu, contentDescription = "Menu", tint = OnSurfaceLight)
        }

        Text(
            text = "HopeFinder",
            color = RedPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
        )

        Box {
            IconButton(onClick = onBellClick) {
                Icon(Icons.Outlined.Notifications, contentDescription = "Notifikasi", tint = OnSurfaceLight)
            }
            if (hasUnread) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(RedPrimary)
                        .border(1.5.dp, SurfaceDark, CircleShape)
                        .align(Alignment.TopEnd)
                        .offset(x = (-10).dp, y = 10.dp)
                )
            }
        }
    }
}

// ── Earthquake alert banner ───────────────────────────────────────────────────
@Composable
private fun EarthquakeAlertBanner(
    location: String,
    depthKm: Int,
    magnitude: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(2.dp, RedPrimary, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(RedPrimary),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Outlined.Warning,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Gempa Terdeteksi!",
                color = RedPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "$location  ·  $depthKm KM",
                color = SubtleGray,
                fontSize = 12.sp,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = magnitude.toString(),
                color = SurfaceDark,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Magnitudo SR",
                color = SubtleGray,
                fontSize = 10.sp,
            )
        }
    }
}

// ── Direction card ────────────────────────────────────────────────────────────
@Composable
private fun DirectionCard(
    modifier: Modifier = Modifier,
    onAlreadyAtPointClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceDark)
            .border(1.dp, BrownNeutral.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Pergi menuju titik kumpul",
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = onAlreadyAtPointClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Icon(
                Icons.Outlined.Navigation,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Color.White,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sudah di Titik Kumpul",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            )
        }
    }
}

// ── Map FAB column ────────────────────────────────────────────────────────────
@Composable
private fun MapFabColumn(
    modifier: Modifier = Modifier,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onSavePin: () -> Unit,
    onMyLocation: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MapIconButton(Icons.Outlined.Add,"Zoom in",onZoomIn)
        MapIconButton(Icons.Outlined.Remove,"Zoom out",onZoomOut)
        MapIconButton(Icons.Outlined.Place,"Simpan lokasi",onSavePin)
        MapIconButton(
            icon        = Icons.Outlined.MyLocation,
            description = "Lokasi saya",
            onClick     = onMyLocation,
            tint        = RedPrimary,
            background  = RedPrimary.copy(alpha = 0.12f),
        )
    }
}

@Composable
private fun MapIconButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    tint: Color = SurfaceDark,
    background: Color = Color.White,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .border(1.dp, BrownNeutral.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
            Icon(icon, contentDescription = description, tint = tint, modifier = Modifier.size(18.dp))
        }
    }
}

// ── SOS FAB ───────────────────────────────────────────────────────────────────
@Composable
private fun SosFab(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(RedPrimary),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
            Text(
                text = "SOS",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp,
            )
        }
    }
}

// ── Map layer placeholder ─────────────────────────────────────────────────────
// Replace with your MapsSDK composable from util/MapsSDK.kt
@Composable
private fun MapLayer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color(0xFFE8F2E8)),
        contentAlignment = Alignment.Center,
    ) {
        Text("Map View", color = Color(0xFF4A6B4A), fontSize = 14.sp)
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

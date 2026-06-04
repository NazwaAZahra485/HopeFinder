package com.nazwa.hopefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nazwa.hopefinder.navigation.Route
import com.nazwa.hopefinder.util.rememberEmergencyCallLauncher

private val DrawerBg = Color(0xFF353238)
private val SectionHeader = Color(0xFFFFFFFF)
private val MenuItemColor = Color(0xFFCBD5E1)
private val DividerColor = Color(0xFF2D2D4E)
private val LogoRed = Color(0xFFE21919)
private val GpsGreen = Color(0xFF22C55E)
private val GpsOff = Color(0xFF757575)

@Composable
fun BurgerMenuDrawer(
    navController: NavController,
    isGpsEnabled: Boolean,
    onGpsToggle: (Boolean) -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val callEmergency = rememberEmergencyCallLauncher()
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(DrawerBg)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, color = LogoRed.copy(alpha = 0.3f))
                ) {
                    onCloseDrawer()
                    navController.navigate(Route.HOME) {
                        popUpTo(Route.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                .padding(bottom = 20.dp)
        ) {
            HamburgerIcon(color = LogoRed)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "HopeFinder",
                color = LogoRed,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 0.5.sp,
                fontFamily = FontFamily.Default
            )
        }

        HorizontalDivider(thickness = 1.dp, color = DividerColor)
        Spacer(modifier = Modifier.height(16.dp))

        // Nomor Darurat
        SectionHeader(title = "Nomor Darurat")
        Spacer(modifier = Modifier.height(8.dp))

        val emergencyItems = listOf(
            "Nomor Panggilan Darurat",
            "Tim SAR",
            "Ambulans",
            "Pemadam Kebakaran",
            "Polisi",
            "BNPB"
        )
        emergencyItems.forEach { label ->
            DrawerMenuItem(label = label, onClick = {
                onCloseDrawer()
                callEmergency()
            })
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 1.dp, color = DividerColor)
        Spacer(modifier = Modifier.height(12.dp))

        // Booklet Evakuasi
        SectionHeader(title = "Petunjuk Evakuasi")
        Spacer(modifier = Modifier.height(8.dp))

        val evacuationItems = listOf(
            "Banjir" to Route.BANJIR,
            "Banjir Bandang" to Route.BANJIR_BANDANG,
            "Gempa Bumi" to Route.GEMPA_BUMI,
            "Longsor" to Route.LONGSOR,
            "Tsunami" to Route.TSUNAMI,
            "Gunung Meletus" to Route.GUNUNG_MELETUS
        )
        evacuationItems.forEach { (label, route) ->
            DrawerMenuItem(label = label, onClick = {
                onCloseDrawer()
                navController.navigate(route)
            })
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 1.dp, color = DividerColor)
        Spacer(modifier = Modifier.height(16.dp))

        // toggle gps
        GpsToggleRow(isEnabled = isGpsEnabled, onToggle = onGpsToggle)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun HamburgerIcon(color: Color) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.width(20.dp)
    ) {
        repeat(3) {
            Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(color))
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        color = SectionHeader,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.3.sp
    )
}

@Composable
private fun DrawerMenuItem(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = Color.White.copy(alpha = 0.1f))
            ) { onClick() }
            .padding(start = 12.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = label,
            color = MenuItemColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.2.sp
        )
    }
}

@Composable
private fun GpsToggleRow(isEnabled: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "GPS", color = SectionHeader, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor   = Color.White,
                checkedTrackColor   = GpsGreen,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = GpsOff
            )
        )
    }
}

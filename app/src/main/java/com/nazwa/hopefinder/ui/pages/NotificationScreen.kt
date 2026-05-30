package com.nazwa.hopefinder.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nazwa.hopefinder.entity.Banjir
import com.nazwa.hopefinder.entity.GempaBumi
import com.nazwa.hopefinder.entity.JenisBencana
import com.nazwa.hopefinder.entity.Longsor
import com.nazwa.hopefinder.ui.theme.AppRed
import com.nazwa.hopefinder.ui.theme.CardBorder
import com.nazwa.hopefinder.ui.theme.DepthBlue
import com.nazwa.hopefinder.ui.theme.MagRed



@Composable
fun NotificationScreen(onBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf(JenisBencana.GEMPA) }

    val gempaItems = List(5) {
        GempaBumi(
            id = "1",
            title = "Pusat gempa berada di 27 km Tenggara HALMAHERA BARAT-MALUT",
            magnitude = 5.2,
            depth = "10KM",
            place = "Halmahera",
            time = "2/04/2026 15:51:53"
        )
    }
    val banjirItems = List(3) {
        Banjir(
            id = "1",
            title = "Banjir terdeteksi di wilayah JAKARTA UTARA",
            place = "Jakarta Utara",
            time = "2/04/2026 14:30:00"
        )
    }
    val longsorItems = List(2) {
        Longsor(
            id ="1",
            title = "Potensi longsor di kawasan PUNCAK BOGOR",
            place = "Puncak, Bogor",
            time = "2/04/2026 13:15:22"
        )
    }

    val currentItems = when (selectedTab) {
        JenisBencana.GEMPA -> gempaItems
        JenisBencana.BANJIR -> banjirItems
        JenisBencana.LONGSOR -> longsorItems
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppRed)
                .padding(horizontal = 8.dp, vertical = 14.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Back", tint = Color.White)
                    Text("Back", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            JenisBencana.entries.forEach { tab ->
                AlertTabChip(
                    label = when (tab) {
                        JenisBencana.GEMPA -> "Gempa"
                        JenisBencana.BANJIR -> "Banjir"
                        JenisBencana.LONGSOR -> "Longsor"
                    },
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab }
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(currentItems) { item ->
                AlertCard(item = item, tab = selectedTab)
            }
        }
    }
}

@Composable
fun AlertTabChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor = if (selected) AppRed else Color.Transparent
    val textColor = if (selected) Color.White else Color.Black
    val borderColor = if (selected) AppRed else Color.Gray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(label, color = textColor, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

@Composable
fun AlertCard(item: GempaBumi, tab: JenisBencana) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.5.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF8B6914), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (tab) {
                        JenisBencana.GEMPA -> Icons.Default.Apartment
                        JenisBencana.BANJIR -> Icons.Default.Water
                        JenisBencana.LONGSOR -> Icons.Default.Landscape
                    },
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Waves,
                            contentDescription = null,
                            tint = DepthBlue,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            "Depth ${item.depth}",
                            color = DepthBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ShowChart,
                            contentDescription = null,
                            tint = MagRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            item.magnitude,
                            color = MagRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.timestamp,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


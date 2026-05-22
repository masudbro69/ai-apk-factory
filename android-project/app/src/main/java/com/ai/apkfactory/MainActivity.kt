package com.ai.apkfactory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DarkBg = Color(0xFF0D1117)
val CardBg = Color(0xFF161B22)
val Accent = Color(0xFF58A6FF)
val Green = Color(0xFF3FB950)
val Red = Color(0xFFF85149)
val TextPrimary = Color(0xFFC9D1D9)
val TextSecondary = Color(0xFF8B949E)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                AppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Dashboard", "Logs", "Analytics", "Settings")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI APK Factory", color = Accent, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = CardBg) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = {},
                        label = { Text(title, fontSize = 11.sp) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Accent,
                            selectedTextColor = Accent,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = CardBg
                        )
                    )
                }
            }
        },
        containerColor = DarkBg
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> DashboardTab()
                1 -> LogsTab()
                2 -> AnalyticsTab()
                3 -> SettingsTab()
            }
        }
    }
}

@Composable
fun DashboardTab() {
    var isRunning by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Automation", color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text(if (isRunning) "Running" else "Stopped", color = if (isRunning) Green else Red, fontSize = 12.sp)
                    }
                    Switch(checked = isRunning, onCheckedChange = { isRunning = it }, colors = SwitchDefaults.colors(checkedTrackColor = Green))
                }
            }
        }
        item { StatusCard("YouTube", "Active", "Auto-upload enabled", Green) }
        item { StatusCard("Facebook", "Active", "Auto-post enabled", Green) }
        item { StatusCard("AI Provider", "OpenRouter", "Fallback: Groq", Accent) }
        item { StatusCard("Cloud", "GitHub Actions", "Cron: every 12h", Accent) }
    }
}

@Composable
fun StatusCard(name: String, status: String, details: String, statusColor: Color) {
    Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(name, color = TextPrimary, fontWeight = FontWeight.Medium)
                Text(details, color = TextSecondary, fontSize = 11.sp)
            }
            Surface(color = statusColor.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)) {
                Text(status, color = statusColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun LogsTab() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(8) { i ->
            val msgs = listOf("Automation started", "Finding topics...", "Script generated", "TTS generated", "Images generated", "Video compiled", "Uploaded to YouTube", "Pipeline complete")
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("09:${String.format("%02d", i)}", color = TextSecondary, fontSize = 11.sp)
                    Text("INFO", color = Green, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(msgs.getOrElse(i) { "..." }, color = TextPrimary, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AnalyticsTab() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("YouTube Analytics", color = Accent, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(12.dp))
                    AnalyticsRow("Total Views", "12,450")
                    AnalyticsRow("Subscribers", "234")
                    AnalyticsRow("Avg CTR", "4.2%")
                    AnalyticsRow("Videos Published", "28")
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Facebook Analytics", color = Accent, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(12.dp))
                    AnalyticsRow("Total Reach", "8,320")
                    AnalyticsRow("Engagement", "3.1%")
                    AnalyticsRow("Followers", "156")
                }
            }
        }
    }
}

@Composable
fun AnalyticsRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextSecondary)
        Text(value, color = TextPrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SettingsTab() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Configuration", color = Accent, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("AI Provider: OpenRouter then Groq", color = TextPrimary, fontSize = 13.sp)
                    Text("TTS: edge-tts (Hindi)", color = TextPrimary, fontSize = 13.sp)
                    Text("Image: Pollinations.ai", color = TextPrimary, fontSize = 13.sp)
                    Text("Upload: YouTube + Facebook", color = TextPrimary, fontSize = 13.sp)
                    Text("Schedule: Every 12 hours", color = TextPrimary, fontSize = 13.sp)
                    Text("Version: 1.0.0", color = TextSecondary, fontSize = 13.sp)
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("About", color = Accent, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("AI APK Factory v1.0.0", color = TextPrimary, fontSize = 13.sp)
                    Text("Control panel for cloud automation.", color = TextSecondary, fontSize = 12.sp)
                    Text("All heavy work runs on GitHub Actions.", color = TextSecondary, fontSize = 12.sp)
                }
            }
        }
    }
}

package com.nyinyi.notemark.features.auth

import MPLog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import notemark.composeapp.generated.resources.Res
import notemark.composeapp.generated.resources.landing_bg
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview()
@Composable
fun LandingScreenPreview() {
    LandingScreen()
}

@Composable
fun LandingScreen(windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass) {
    val showLandscapeScreen =
        windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT

    MPLog
        .tag("LandingScreen")
        .v("showLandscapeScreen: $showLandscapeScreen : ${windowSizeClass.windowHeightSizeClass} : ${windowSizeClass.windowWidthSizeClass}")
    if (showLandscapeScreen) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0EAFF)),
        ) {
            BackgroundSection(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(0.50f),
            )
            BottomCard(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(0.50f)
                        .padding(vertical = 24.dp, horizontal = 24.dp),
                isLandscape = true,
            )
        }
    } else {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0EAFF)),
        ) {
            BackgroundSection(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.65f)
                        .background(Color(0xFFE0EFFF)),
            )
            BottomCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.40f)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun BackgroundSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(Res.drawable.landing_bg),
            contentDescription = "NoteMark",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun BottomCard(
    modifier: Modifier,
    isLandscape: Boolean = false,
) {
    val carRoundedCornerShape =
        if (isLandscape) {
            RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
        } else {
            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        }
    Card(
        shape = carRoundedCornerShape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            TextContent()
            ButtonSection()
        }
    }
}

@Composable
private fun TextContent() {
    Column {
        Text(
            text = "Your Own Collection\nof Notes",
            style =
                MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                ),
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Capture your thoughts and ideas.",
            style =
                MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
        )
    }
}

@Composable
private fun ButtonSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = { /* TODO: Handle Get Started click */ },
            shape = RoundedCornerShape(12.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp),
        ) {
            Text("Get Started", style = MaterialTheme.typography.titleMedium)
        }

        OutlinedButton(
            onClick = { /* TODO: Handle Log In click */ },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            colors =
                ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
        ) {
            Text("Log In", style = MaterialTheme.typography.titleMedium)
        }
    }
}

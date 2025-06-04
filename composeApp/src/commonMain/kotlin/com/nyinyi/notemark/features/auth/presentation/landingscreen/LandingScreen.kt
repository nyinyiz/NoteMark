package com.nyinyi.notemark.features.auth.presentation.landingscreen

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
import androidx.compose.foundation.layout.widthIn
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
import androidx.window.core.layout.WindowSizeClass
import com.nyinyi.notemark.core.utils.ScreenConfiguration
import com.nyinyi.notemark.core.utils.determineScreenConfiguration
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
fun LandingScreen(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    val configuration = determineScreenConfiguration(windowSizeClass)

    MPLog
        .tag("LandingScreen")
        .v("Configuration: $configuration (H: ${windowSizeClass.windowHeightSizeClass}, W: ${windowSizeClass.windowWidthSizeClass})")

    when (configuration) {
        is ScreenConfiguration.MobilePortrait ->
            LandingScreenMobilePortrait(
                onSignUpClick = onSignUpClick,
                onLoginClick = onLoginClick,
            )

        is ScreenConfiguration.MobileLandscape ->
            LandingScreenMobileLandscape(
                onSignUpClick = onSignUpClick,
                onLoginClick = onLoginClick,
            )

        is ScreenConfiguration.TabletPortrait ->
            LandingScreenTabletPortrait(
                onSignUpClick = onSignUpClick,
                onLoginClick = onLoginClick,
            )

        is ScreenConfiguration.TabletLandscape ->
            LandingScreenTabletLandscape(
                onSignUpClick = onSignUpClick,
                onLoginClick = onLoginClick,
            )

        is ScreenConfiguration.Undefined -> {
            MPLog.tag("LandingScreen").w("Undefined configuration, defaulting to MobilePortrait.")
            LandingScreenMobilePortrait(
                onSignUpClick = onSignUpClick,
                onLoginClick = onLoginClick,
            )
        }
    }
}

@Composable
private fun LandingScreenMobilePortrait(
    onSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
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
                    .fillMaxHeight(0.65f),
        )
        BottomCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.40f)
                    .align(Alignment.BottomCenter),
            isLandscape = false,
            onSignUpClick = onSignUpClick,
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
private fun LandingScreenMobileLandscape(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
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
                    .weight(0.40f),
        )
        BottomCard(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .weight(0.60f)
                    .padding(vertical = 24.dp),
            isLandscape = true,
            onSignUpClick = onSignUpClick,
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
private fun LandingScreenTabletPortrait(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
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
                    .fillMaxHeight(0.75f),
        )
        BottomCard(
            modifier =
                Modifier
                    .widthIn(max = 680.dp)
                    .fillMaxHeight(0.30f)
                    .align(Alignment.BottomCenter),
            isLandscape = false,
            onSignUpClick = onSignUpClick,
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
private fun LandingScreenTabletLandscape(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
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
                    .weight(0.40f),
        )
        BottomCard(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .weight(0.60f)
                    .padding(vertical = 24.dp, horizontal = 24.dp),
            isLandscape = true,
            onSignUpClick = onSignUpClick,
            onLoginClick = onLoginClick,
        )
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
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
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
            ButtonSection(
                onSignUpClick = onSignUpClick,
                onLoginClick = onLoginClick,
            )
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
private fun ButtonSection(
    onSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = {
                onSignUpClick()
            },
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
            onClick = {
                onLoginClick()
            },
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

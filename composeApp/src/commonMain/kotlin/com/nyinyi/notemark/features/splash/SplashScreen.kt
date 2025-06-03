package com.nyinyi.notemark.features.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import notemark.composeapp.generated.resources.Res
import notemark.composeapp.generated.resources.app_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    val scale = remember { Animatable(10f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000),
        )
        delay(1000)
        onAnimationFinished()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center,
        ) {
            val imagePainter = painterResource(Res.drawable.app_icon)

            Image(
                painter = imagePainter,
                contentDescription = "NoteMark",
                modifier =
                    Modifier
                        .size(
                            width = imagePainter.intrinsicSize.width.dp,
                            height = imagePainter.intrinsicSize.height.dp,
                        ).scale(scale.value),
            )
        }
    }
}

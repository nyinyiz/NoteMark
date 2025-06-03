package com.nyinyi.notemark.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val onSurface = Color(0xFF1B1B1C)
private val onSurfaceVariant = Color(0xFF535364)
private val surface = Color(0xFFEFEFF2)
private val surfaceLowest = Color(0xFFFFFFFF)
private val error = Color(0xFFE1234B)
private val primary = Color(0xFF5977F7)
private val onPrimary = Color(0xFFFFFFFF)

private val LightColorScheme =
    lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
        surface = surface,
        error = error,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        onSurface = surfaceLowest,
        onSurfaceVariant = surface,
        surface = onSurfaceVariant,
        error = error,
    )

@Composable
fun NoteMarkTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}

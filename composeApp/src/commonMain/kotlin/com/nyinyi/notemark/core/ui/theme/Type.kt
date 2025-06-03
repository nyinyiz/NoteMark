package com.nyinyi.notemark.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Title Typography
val TitleTypography =
    Typography(
        headlineLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 40.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 36.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp,
                lineHeight = 24.sp,
            ),
    )

// Body Typography
val BodyTypography =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 24.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 20.sp,
            ),
    )

@Composable
fun getNoteMarkTypography(): Typography =
    Typography(
        headlineLarge = TitleTypography.headlineLarge,
        headlineMedium = TitleTypography.headlineMedium,
        titleSmall = TitleTypography.titleSmall,
        bodyLarge = BodyTypography.bodyLarge,
        bodyMedium = BodyTypography.bodyMedium,
        bodySmall = BodyTypography.bodySmall,
    )

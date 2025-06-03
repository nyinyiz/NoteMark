package com.nyinyi.notemark.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

@Immutable
sealed class ScreenConfiguration {
    data object MobilePortrait : ScreenConfiguration()

    data object MobileLandscape : ScreenConfiguration()

    data object TabletPortrait : ScreenConfiguration()

    data object TabletLandscape : ScreenConfiguration()

    data object Undefined : ScreenConfiguration() // Fallback
}

@Composable
fun determineScreenConfiguration(windowSizeClass: WindowSizeClass): ScreenConfiguration {
    val heightSizeClass = windowSizeClass.windowHeightSizeClass
    val widthSizeClass = windowSizeClass.windowWidthSizeClass

    return when {
        // Mobile Portrait
        widthSizeClass == WindowWidthSizeClass.COMPACT &&
            (heightSizeClass == WindowHeightSizeClass.MEDIUM || heightSizeClass == WindowHeightSizeClass.EXPANDED) ->
            ScreenConfiguration.MobilePortrait
        // Mobile Landscape
        heightSizeClass == WindowHeightSizeClass.COMPACT &&
            (widthSizeClass == WindowWidthSizeClass.MEDIUM || widthSizeClass == WindowWidthSizeClass.EXPANDED) ->
            ScreenConfiguration.MobileLandscape
        // Tablet Portrait
        widthSizeClass == WindowWidthSizeClass.MEDIUM &&
            heightSizeClass == WindowHeightSizeClass.EXPANDED ->
            ScreenConfiguration.TabletPortrait
        // Tablet Landscape
        (
            widthSizeClass == WindowWidthSizeClass.EXPANDED &&
                (heightSizeClass == WindowHeightSizeClass.MEDIUM || heightSizeClass == WindowHeightSizeClass.EXPANDED)
        ) ||
            (widthSizeClass == WindowWidthSizeClass.MEDIUM && heightSizeClass == WindowHeightSizeClass.MEDIUM) ->
            ScreenConfiguration.TabletLandscape

        else -> ScreenConfiguration.Undefined
    }
}

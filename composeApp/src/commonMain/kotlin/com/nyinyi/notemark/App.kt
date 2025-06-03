package com.nyinyi.notemark

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.nyinyi.notemark.core.navigation.NavGraph
import com.nyinyi.notemark.core.ui.theme.NoteMarkTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    NoteMarkTheme {
        NavGraph(
            navController = navController,
        )
    }
}

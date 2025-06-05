package com.nyinyi.notemark.features.auth.presentation.login.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.nyinyi.notemark.features.auth.domain.model.AuthResult

@Composable
fun LoginSuccessDialog(
    authResult: AuthResult,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Login Successful!")
        },
        text = {
            Text(
                text =
                    "You have successfully logged in. \n\nWelcome back! " +
                        "\nYour accessToken is: ${authResult.accessToken} \n\n " +
                        "Your refreshToken is: ${authResult.refreshToken}",
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
            ) {
                Text("OK")
            }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    )
}

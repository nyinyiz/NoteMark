package com.nyinyi.notemark.features.auth.presentation.register.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun RegistrationSuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Registration Successful!") },
        text = { Text(text = "Your account has been created successfully. You can now log in.") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    )
}

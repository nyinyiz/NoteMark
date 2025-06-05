package com.nyinyi.notemark.features.auth.presentation.register.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nyinyi.notemark.core.ui.components.LabeledOutlinedTextField

@Composable
fun EmailInputField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    focusManager: FocusManager,
) {
    LabeledOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Email",
        placeholder = "john.doe@example.com",
        error = error,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
        keyboardActions =
            KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
        showLabelAboveField = true,
    )
}

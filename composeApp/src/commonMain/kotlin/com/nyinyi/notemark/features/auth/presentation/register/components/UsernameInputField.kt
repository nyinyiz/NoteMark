package com.nyinyi.notemark.features.auth.presentation.register.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nyinyi.notemark.core.ui.components.LabeledOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameInputField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    focusManager: FocusManager,
    fieldBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.surface,
) {
    LabeledOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Username",
        placeholder = "John.doe",
        error = error,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
        keyboardActions =
            KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
        fieldBackgroundColor = fieldBackgroundColor,
        unfocusedIndicatorColor = borderColor,
        showLabelAboveField = true,
    )
}

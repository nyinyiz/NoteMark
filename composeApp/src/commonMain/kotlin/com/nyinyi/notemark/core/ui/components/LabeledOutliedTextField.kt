package com.nyinyi.notemark.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String? = null,
    error: String? = null,
    supportingText: @Composable (() -> Unit)? = {
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = error != null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    fieldBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    focusedIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.surface,
    errorIndicatorColor: Color = MaterialTheme.colorScheme.error,
    labelStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    showLabelAboveField: Boolean = true,
) {
    if (showLabelAboveField) {
        Text(
            text = label,
            style = labelStyle,
            modifier = Modifier.padding(bottom = 4.dp),
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        shape = shape,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = fieldBackgroundColor,
                unfocusedContainerColor = fieldBackgroundColor,
                disabledContainerColor = fieldBackgroundColor,
                focusedIndicatorColor = focusedIndicatorColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                errorIndicatorColor = errorIndicatorColor,
            ),
        supportingText = supportingText,
    )
}

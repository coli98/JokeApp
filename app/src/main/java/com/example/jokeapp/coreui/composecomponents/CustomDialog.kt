package com.example.jokeapp.coreui.composecomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

/**
 * Custom Dialog with two Buttons (separate Actions) with Title and description
 */
@Composable
fun <T> CustomDialog(
    title: String? = null,
    onAction: (T) -> Unit,
    dialogMsg: String? = null,
    icon: @Composable (() -> Unit)? = { Icon(Icons.Filled.Warning, contentDescription = null) },
    confirmBtnText: String,
    confirmAction: T,
    dismissAction: T? = null,
    dismissBtnText: String? = null
) {

    AlertDialog(
        onDismissRequest = {
            if (dismissAction != null) {
                onAction(dismissAction)
            }
        },
        icon = icon,
        title = {
            if (title != null) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                )
            }
        },
        text = {
            if (dialogMsg != null) {
                Text(
                    text = dialogMsg,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAction(confirmAction)
                }
            ) {
                Text(confirmBtnText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    if (dismissAction != null) {
                        onAction(dismissAction)
                    }
                },
            ) {
                if (dismissBtnText != null) {
                    Text(dismissBtnText)
                }
            }
        }
    )
}



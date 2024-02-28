package com.evilstan.mornhousetest.ui.dialog

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evilstan.mornhousetest.R

@Composable
fun WarningDialog(
    title: String,
    body: String,
    onConfirm: () -> Unit
) {
    WarningDialog(
        title = title,
        body = { Text(text = body, style = MaterialTheme.typography.bodyLarge) },
        onConfirm = onConfirm
    )
}

@Composable
fun WarningDialog(
    title: String,
    body: @Composable () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        tonalElevation = 0.dp,
        onDismissRequest = onConfirm,
        shape = RoundedCornerShape(12.dp),
        title = { Text(text = title, style = MaterialTheme.typography.titleSmall) },
        text = body,
        confirmButton = {
            Button(
                shape = RoundedCornerShape(6.dp),
                onClick = onConfirm,
                modifier = Modifier.height(46.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    )
}
package com.axelestrada.dinaco.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.core.designsystem.theme.Typography

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    showNotifications: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    style = Typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (showNotifications) NotificationsButton()
    }
}
package com.axelestrada.dinaco.core.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.R

@Composable
fun NotificationsButton() {
    SurfaceIconButton(
        onClick = { /*TODO: navegar a a la pantalla de notificaciones*/ },
    ) {
        BadgedIcon(
            hasBadge = true, badgeOffset = DpOffset(
                x = 0.dp, y = (-4).dp
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_lucide_bell),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(name = "NotificationsButtonPreview", showBackground = true)
@Composable
fun NotificationsButtonPreview() {
    NotificationsButton()
}
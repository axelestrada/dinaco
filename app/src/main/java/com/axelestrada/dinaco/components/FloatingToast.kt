package com.axelestrada.dinaco.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axelestrada.dinaco.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun FloatingToast(
    message: String, visible: Boolean, onDismiss: () -> Unit, modifier: Modifier = Modifier
) {
    val glassShape = RoundedCornerShape(50.dp)

    val exitOffset = with(LocalDensity.current) {
        56.dp.roundToPx()
    }

    LaunchedEffect(visible) {
        if (visible) {
            delay(3.seconds)
            onDismiss()
        }
    }

    AnimatedVisibility(
        modifier = modifier
            .statusBarsPadding()
            .padding(top = 14.dp),
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -it }, animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it - exitOffset }, animationSpec = spring(
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = Color(0xFF0F0F13), shape = glassShape
                )
                .border(
                    width = 1.dp, color = Color(0x1AFFFFFF), shape = glassShape
                )
                .padding(
                    horizontal = 16.dp, vertical = 10.dp
                ), verticalAlignment = Alignment.CenterVertically
        ) {


            Image(
                painter = painterResource(R.drawable.ic_circle_off),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )


            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = message,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

        }
    }
}
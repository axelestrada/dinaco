package com.axelestrada.dinaco.features.home.presentation.screen

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axelestrada.dinaco.R
import com.axelestrada.dinaco.core.designsystem.theme.interFamily
import com.axelestrada.dinaco.core.designsystem.utils.glassBlur
import com.axelestrada.dinaco.core.designsystem.utils.glowShadow

@Composable
fun WaterTankGauge(
    modifier: Modifier = Modifier,
    percentage: Float,
    volumeText: String = "250 L",
    statText: String = "-12% vs ayer",

    ) {

    val color = when (percentage) {
        in 0.4f..1f -> MaterialTheme.colorScheme.primary
        in 0.2f..0.4f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    val animatedPercentage by animateFloatAsState(
        targetValue = percentage.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 600, easing = EaseOut),
        label = "waterLevelAnimation"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "waveTransition")

    val waveRotation1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "waveRotation1"
    )

    val density = LocalDensity.current
    val floatOffsetPx = with(density) { (-8).dp.toPx() }

    val badgeOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = floatOffsetPx, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "badgeLevitate1"
    )

    val badgeOffset2 by infiniteTransition.animateFloat(
        initialValue = floatOffsetPx, targetValue = 0f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "badgeLevitate2"
    )

    val waveRotation2 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "waveRotation2"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                .border(9.dp, MaterialTheme.colorScheme.background, CircleShape)
                .glowShadow(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    blurRadius = 40.dp,
                    spread = (-10).dp,
                    isCircle = true
                )
                .padding(9.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface, shape = CircleShape
                )
                .clip(CircleShape)
                .drawBehind {
                    val strokeWidthPx = 2.dp.toPx()

                    val containerSize = size.width
                    val liquidHeight = containerSize * animatedPercentage
                    val waterLevelY = containerSize - liquidHeight

                    drawRect(
                        color = color,
                        topLeft = Offset(0f, waterLevelY),
                        size = Size(containerSize, liquidHeight)
                    )

                    if (animatedPercentage > 0f) {
                        drawLine(
                            color = color,
                            start = Offset(0f, waterLevelY),
                            end = Offset(containerSize, waterLevelY),
                            strokeWidth = strokeWidthPx
                        )
                    }

                    withTransform({

                        clipRect(
                            left = 0f,
                            top = waterLevelY,
                            right = containerSize,
                            bottom = containerSize
                        )
                    }) {


                        val waveSize2 = containerSize * 2.1f
                        val cornerRadius2 = waveSize2 * 0.38f

                        withTransform({
                            translate(
                                left = -containerSize * 0.55f,
                                top = waterLevelY - waveSize2 + (containerSize * 0.10f)
                            )
                            rotate(
                                degrees = waveRotation2,
                                pivot = Offset(waveSize2 / 2, waveSize2 / 2)
                            )
                        }) {
                            drawRoundRect(
                                color = Color(0x26FFFFFF),
                                size = Size(waveSize2, waveSize2),
                                cornerRadius = CornerRadius(cornerRadius2, cornerRadius2)
                            )
                        }

                        val waveSize1 = containerSize * 2.0f
                        val cornerRadius1 = waveSize1 * 0.40f

                        withTransform({
                            translate(
                                left = -containerSize * 0.50f,
                                top = waterLevelY - waveSize1 + (containerSize * 0.08f)
                            )
                            rotate(
                                degrees = waveRotation1,
                                pivot = Offset(waveSize1 / 2, waveSize1 / 2)
                            )
                        }) {
                            drawRoundRect(
                                color = Color(0x26050507),
                                size = Size(waveSize1, waveSize1),
                                cornerRadius = CornerRadius(cornerRadius1, cornerRadius1)
                            )
                        }
                    }


                    val dotRadius = 1.dp.toPx()
                    val spacing = 20.dp.toPx()
                    val dotColor = Color.White.copy(alpha = 0.1f)

                    val columns = (size.width / spacing).toInt() + 1
                    val rows = (size.height / spacing).toInt() + 1

                    val startX = (size.width % spacing) / 2
                    val startY = (size.height % spacing) / 2

                    for (i in 0 until columns) {
                        for (j in 0 until rows) {
                            drawCircle(
                                color = dotColor,
                                radius = dotRadius,
                                center = Offset(startX + i * spacing, startY + j * spacing)
                            )
                        }
                    }
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    append("${(animatedPercentage * 100).toInt()}")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 30.sp,
                            fontFamily = interFamily,
                            fontWeight = FontWeight.Light,
                            color = Color.White.copy(alpha = 0.7f),
                            baselineShift = BaselineShift(0.4f),
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(0f, 4f),
                                blurRadius = 8f
                            )
                        )
                    ) {
                        append("%")
                    }
                },
                color = Color.White,
                fontSize = 72.sp,
                fontWeight = FontWeight.Light,
                fontFamily = interFamily,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(2f, 6f),
                        blurRadius = 12f
                    )
                )
            )
        }

        val tankRadiusPx = with(density) { 125.dp.toPx() }
        val outerBorderHalfPx = with(density) { 4.5.dp.toPx() }

        GlassBadge(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationX = -(tankRadiusPx - outerBorderHalfPx)
                    translationY = badgeOffset1
                }) {

            Text(
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 9.sp,
                    letterSpacing = 0.9.sp

                ), text = "HOY"
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFamily,
                    fontSize = 12.sp,
                    color = Color.White
                ), text = volumeText
            )


        }

        GlassBadge(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationX = (tankRadiusPx - outerBorderHalfPx)
                    translationY = badgeOffset2
                }) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(10.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = statText,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = interFamily
            )
        }

    }
}

@Composable
private fun GlassBadge(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
            .height(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(1.dp)
                .clip(RoundedCornerShape(50))
                .matchParentSize()
                .glassBlur(blurRadius = 12.dp)
                .background(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.85f), RoundedCornerShape(50)
                )
        )

        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

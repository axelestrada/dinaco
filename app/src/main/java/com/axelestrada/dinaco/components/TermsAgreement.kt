package com.axelestrada.dinaco.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axelestrada.dinaco.R

@Composable
fun TermsAgreement(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showError: Boolean = false,
) {
    val text = buildAnnotatedString {
        append("Acepto los ")

        withLink(
            LinkAnnotation.Url(
                "https://dinaco.app/terms-and-conditions", TextLinkStyles(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.None
                    )
                )
            )
        ) {

            append("Términos y Condiciones")

        }

        append(" y la ")

        withLink(
            LinkAnnotation.Url(
                "https://dinaco.app/privacy-policy", TextLinkStyles(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        textDecoration = TextDecoration.None
                    )
                )
            )
        ) {

            append("Política de Privacidad")
        }

        append(" de Dinaco")
    }

    Row(
        verticalAlignment = Alignment.Top, modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color(0xFF0F0F13))
                .border(
                    width = 1.dp, color = when {
                        showError -> MaterialTheme.colorScheme.error
                        checked -> MaterialTheme.colorScheme.primary
                        else -> Color(0x33FFFFFF)
                    }, shape = RoundedCornerShape(6.dp)
                )
                .clickable {
                    onCheckedChange(!checked)
                }, contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Image(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
        }


        Text(
            text = text,
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable {
                    onCheckedChange(!checked)
                },
            style = TextStyle(
                color = Color(0xFF8A8A93), fontSize = 14.sp, lineHeight = 18.sp
            ),

            )


    }
}
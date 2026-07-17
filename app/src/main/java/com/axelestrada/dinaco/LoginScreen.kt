package com.axelestrada.dinaco

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.axelestrada.dinaco.components.FloatingToast
import com.axelestrada.dinaco.components.TermsAgreement

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var acceptedTerms by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 28.dp, vertical = 40.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                MaterialTheme.colorScheme.primary, CircleShape
                            )
                    )
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    text = "El agua de tu hogar,\ninteligente.",
                    color = Color.White,
                    fontSize = 38.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Monitoreo premium en tiempo real.",
                    color = Color(0xFF8B8B8B),
                    fontSize = 18.sp
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {

                TermsAgreement(
                    checked = acceptedTerms, onCheckedChange = {
                        acceptedTerms = it
                        showToast = false

                    }, modifier = Modifier.padding(bottom = 18.dp), showError = showToast
                )


                Button(
                    onClick = {

                        if (!acceptedTerms) {
                            showToast = true

                            return@Button
                        }

                        // Aquí continúa login con Google

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x08FFFFFF)
                    ),
                    border = BorderStroke(
                        width = 1.dp, color = Color(0x0DFFFFFF)
                    )
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Continuar con Google",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
        }

        FloatingToast(
            message = "Debes aceptar la política de privacidad", visible = showToast, onDismiss = {
                showToast = false
            }, modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(10f)
        )
    }


}
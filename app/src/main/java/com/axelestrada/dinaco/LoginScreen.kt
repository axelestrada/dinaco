package com.axelestrada.dinaco

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.zIndex
import com.axelestrada.dinaco.components.FloatingToast
import com.axelestrada.dinaco.components.SurfaceButton
import com.axelestrada.dinaco.components.TermsAgreement
import com.axelestrada.dinaco.ui.theme.Typography

@Composable
fun LoginScreen() {
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

                Image(
                    painter = painterResource(R.drawable.ic_dino),
                    contentDescription = null,
                    modifier = Modifier.width(36.dp)
                )

                Spacer(Modifier.height(28.dp))

                Text(
                    text = "El agua de tu hogar, inteligente.",
                    style = Typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Monitorea tus tinacos desde cualquier lugar.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Typography.bodyLarge
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


                SurfaceButton(
                    text = "Continuar con Google",
                    icon = R.drawable.ic_google,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                ) {

                    if (!acceptedTerms) {
                        showToast = true
                        return@SurfaceButton
                    }

                    // Login Google
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
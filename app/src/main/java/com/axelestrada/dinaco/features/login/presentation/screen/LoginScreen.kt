package com.axelestrada.dinaco.features.login.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.R
import com.axelestrada.dinaco.core.common.snackbar.SnackbarManager
import com.axelestrada.dinaco.core.designsystem.components.SurfaceButton
import com.axelestrada.dinaco.core.designsystem.components.TermsAgreement
import com.axelestrada.dinaco.core.designsystem.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var acceptedTerms by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 28.dp, end = 28.dp, top = 40.dp, bottom = 28.dp)
                .navigationBarsPadding()
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
                    style = Typography.bodyMedium
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
                        showError = false

                    }, modifier = Modifier.padding(bottom = 18.dp), showError = showError
                )


                SurfaceButton(
                    text = "Continuar con Google",
                    icon = R.drawable.ic_google,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                ) {

                    if (!acceptedTerms) {
                        showError = true
                        scope.launch {
                            SnackbarManager.showMessage("Debes aceptar la política de privacidad")
                        }
                        return@SurfaceButton
                    }

                    onLoginSuccess()
                }
            }
        }
    }
}

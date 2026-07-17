package com.axelestrada.dinaco

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.animation.doOnEnd
import androidx.core.view.WindowCompat
import com.axelestrada.dinaco.ui.theme.DinacoTheme
import java.time.Duration
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val animationDuration = splashScreenView.iconAnimationDuration
                val animationStart = splashScreenView.iconAnimationStart

                val remainingDuration = if (animationDuration != null && animationStart != null) {
                    (animationDuration - Duration.between(animationStart, Instant.now())).toMillis()
                        .coerceAtLeast(0L)
                } else {
                    0L
                }

                splashScreenView.postDelayed({

                    val slideUp = ObjectAnimator.ofFloat(
                        splashScreenView, View.TRANSLATION_Y, 0f, splashScreenView.height.toFloat()
                    ).apply {
                        interpolator = AnticipateInterpolator()
                        duration = 250L

                        doOnEnd {
                            splashScreenView.remove()
                        }
                    }

                    slideUp.start()

                }, remainingDuration)
            }
        }

        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            false
        setContent {
            DinacoTheme {
                LoginScreen()
            }
        }

    }
}

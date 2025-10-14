package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodrigovalverde.tinta_y_papel_android.R
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

// CAMBIO AQUÍ: Se añade el parámetro 'loginViewModel' a la función.
@Composable
fun SplashScreen(navController: NavController, loginViewModel: LoginViewModel) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navController.popBackStack()

        // Esta lógica ya usa el viewModel, por eso es necesario recibirlo.
        if (loginViewModel.loggedInUser.value != null) {
            navController.navigate(AppScreens.HomeScreen.route)
        } else {
            navController.navigate(AppScreens.WelcomeScreen.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de la App",
                modifier = Modifier.size(150.dp).alpha(alphaAnim.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tinta y Papel",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.alpha(alphaAnim.value)
            )
        }
    }
}
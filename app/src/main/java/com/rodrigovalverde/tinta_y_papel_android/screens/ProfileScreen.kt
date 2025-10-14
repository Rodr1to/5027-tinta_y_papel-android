package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodrigovalverde.tinta_y_papel_android.R
import com.rodrigovalverde.tinta_y_papel_android.data.User
import com.rodrigovalverde.tinta_y_papel_android.navigation.AuthNav
import com.rodrigovalverde.tinta_y_papel_android.viewmodels.LoginViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: LoginViewModel) { // Recibe el ViewModel
    val loggedInUser by viewModel.loggedInUser.collectAsState()

    if (loggedInUser != null) {
        LoggedInProfileView(user = loggedInUser!!, onLogout = { viewModel.logout() })
    } else {
        LoggedOutProfileView(navController = navController)
    }
}

@Composable
fun LoggedOutProfileView(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tu Espacio Exclusivo", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Para acceder a tu biblioteca personal, debes iniciar sesión. Si no tienes una cuenta, regístrate.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Button(onClick = { navController.navigate(AuthNav.login) }) { // Navega a la ruta de login
                Text("Ingresar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedButton(onClick = { /* Lógica de registro futura */ }) {
                Text("Registrarse")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoggedInProfileView(user: User, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tu Espacio Personal") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Icono de Perfil",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "¡Hola, ${user.nombres} ${user.apellidos}!",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                "Gestiona aquí tus preferencias y descubre tus próximas lecturas.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
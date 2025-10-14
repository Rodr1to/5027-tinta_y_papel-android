package com.rodrigovalverde.tinta_y_papel_android.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens

@Composable
fun WelcomeScreen(navController: NavController) {
    var isChecked by remember { mutableStateOf(false) }
    // Estado para controlar la visibilidad del diálogo
    var showTermsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Bienvenido a Tinta y Papel",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tu universo literario personal. Descubre, organiza y sumérgete en miles de historias.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TermsAndConditionsText { showTermsDialog = true }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                enabled = isChecked,
                onClick = {
                    navController.navigate(AppScreens.HomeScreen.route) {
                        popUpTo(AppScreens.WelcomeScreen.route) { inclusive = true }
                    }
                }
            ) {
                Text("Ingresar")
            }
        }
    }

    // Si showTermsDialog es true, muestra el diálogo
    if (showTermsDialog) {
        TermsDialog(onDismiss = { showTermsDialog = false })
    }
}

@Composable
fun TermsAndConditionsText(onTextClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        append("Acepto los ")
        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        // --- 👇 CAMBIO CRÍTICO AQUÍ 👇 ---
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append("Términos de Uso y Política de Privacidad")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                .firstOrNull()?.let {
                    onTextClick()
                }
        }
    )
}


@Composable
fun TermsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Términos de Uso") },
        text = {
            Text(
                text = "Este es un texto genérico de ejemplo para los términos de uso y la política de privacidad. " +
                        "Al usar esta aplicación, usted acepta que toda la información es para fines demostrativos " +
                        "y que no se recopila ni almacena información personal real. Este proyecto es puramente académico."
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Entendido")
            }
        }
    )
}
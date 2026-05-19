package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.ui.components.*
import com.example.artlery.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileCompactScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val appTheme by viewModel.appTheme.collectAsState()
    val isLogged = userName.isNotBlank()

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    var editNameInput by remember { mutableStateOf("") }
    LaunchedEffect(userName) { editNameInput = userName }

    val onAuthClick: () -> Unit = {
        if (isLogged) {
            viewModel.logout()
        } else {
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                val newName = emailInput.substringBefore("@").replaceFirstChar { it.uppercase() }
                viewModel.login(newName)
                navController.navigate("piece_list") {
                    popUpTo("profile") { inclusive = true }
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.profile_title))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLogged) {
                StandardTextComp(
                    text = stringResource(R.string.welcome_user, userName),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                StandardInputTextComp(
                    label = "Cambiar nombre de usuario",
                    value = editNameInput
                ) {
                    editNameInput = it
                    if (it.isNotBlank()) viewModel.login(it)
                }
                Spacer(modifier = Modifier.height(16.dp))

                StandardTextComp(text = "Tema de la aplicación:", style = MaterialTheme.typography.bodyLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    listOf("Claro", "Oscuro", "Sistema").forEach { themeOption ->
                        RadioButton(
                            selected = (appTheme == themeOption),
                            onClick = { viewModel.updateTheme(themeOption) }
                        )
                        StandardTextComp(text = themeOption, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                ImageComp(
                    drawable = R.drawable.a_icono,
                    contentDesc = stringResource(R.string.profile_image_desc),
                    modifier = Modifier.clip(CircleShape),
                    height = 120,
                    width = 120
                )
                Spacer(modifier = Modifier.height(16.dp))

            } else {
                ImageComp(
                    drawable = R.drawable.a_icono,
                    contentDesc = stringResource(R.string.app_logo_desc),
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                StandardTextComp(
                    text = stringResource(R.string.login_prompt),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                StandardInputTextComp(
                    label = stringResource(R.string.email_label),
                    value = emailInput,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) { emailInput = it }

                StandardInputTextComp(
                    label = stringResource(R.string.password_label),
                    value = passwordInput,
                    isPasswordField = true
                ) { passwordInput = it }
                Spacer(modifier = Modifier.height(16.dp))
            }

            StandardButtonComp(
                label = if (isLogged) stringResource(R.string.logout) else stringResource(R.string.login),
                onClick = onAuthClick
            )
        }
    }
}
package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.StandardButtonComp
import com.example.artlery.ui.components.StandardInputTextComp
import com.example.artlery.ui.components.StandardTextComp

@Composable
fun ProfileCompactScreen(
    isLogged: Boolean,
    userName: String,
    onLogin: (String) -> Unit,
    onLogout: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }


    val onAuthClick: () -> Unit = {
        if (isLogged) {
            onLogout()
        } else {
            if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                val newName = emailInput.substringBefore("@").replaceFirstChar { it.uppercase() }
                onLogin(newName)

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
                Spacer(modifier = Modifier.height(30.dp))


                ImageComp(
                    drawable = R.drawable.a_icono,
                    contentDesc = stringResource(R.string.profile_image_desc),
                    modifier = Modifier.clip(CircleShape),
                    height = 200,
                    width = 200
                )
                Spacer(modifier = Modifier.height(30.dp))

            } else {
                ImageComp(
                    drawable = R.drawable.a_icono,
                    contentDesc = stringResource(R.string.app_logo_desc),
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                StandardTextComp(
                    text = stringResource(R.string.login_prompt),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(30.dp))

                StandardInputTextComp(
                    label = stringResource(R.string.email_label),
                    value = emailInput,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    emailInput = it
                }
                StandardInputTextComp(
                    label = stringResource(R.string.password_label),
                    value = passwordInput,
                    isPasswordField = true
                ) {
                    passwordInput = it
                }
                Spacer(modifier = Modifier.height(30.dp))
            }

            StandardButtonComp(
                label = if (isLogged) stringResource(R.string.logout) else stringResource(R.string.login),
                onClick = onAuthClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCompactScreenPreview() {
    ProfileCompactScreen(
        isLogged = false,
        userName = "Visitante",
        onLogin = {},
        onLogout = {},
        navController = NavController(LocalContext.current)
    )
}
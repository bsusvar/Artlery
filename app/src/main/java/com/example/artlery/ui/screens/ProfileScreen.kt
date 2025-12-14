package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.artlery.ui.components.StandardInputTextComp
import com.example.artlery.R
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.ui.components.StandardButtonComp


// ProfileScreen: Pantalla con información del usuario con un botón para hacer login/logout.
// Si iniciar sesión: nombre, imagen de perfil y "cerrar sesión"
// Si no iniciar sesión: "iniciar sesión"

@Composable
fun ProfileCompactScreen(modifier: Modifier = Modifier) {
    var profileName by remember { mutableStateOf("") }
    var isLogged by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StandardInputTextComp(
            stringResource(R.string.profile_name),
            profileName,
            Modifier.padding(10.dp)
        ) {
            profileName = it
        }
        Spacer(modifier = Modifier.height(15.dp))
        ImageComp(
            drawable = R.drawable.a_icono,
            modifier = Modifier.clip(CircleShape),
            height = 300,
            width = 300
        )
        Spacer(modifier = Modifier.height(15.dp))
        StandardButtonComp(
            if (isLogged) stringResource(R.string.logout) else stringResource(R.string.login),

            )
    }

}

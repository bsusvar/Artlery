package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.model.Datasource
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.R
import com.example.artlery.ui.components.StandardButtonComp
import com.example.artlery.ui.components.StandardTextComp


// Incluir botón para favs

@Composable
fun PieceDetailCompactScreen(
    pieceName: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val piece = if (pieceName != null) Datasource.getPieceByName(pieceName) else null
    androidx.compose.material3.Surface(

        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Usa el color de fondo del tema
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            piece?.let {
                // Imagen
                ImageComp(
                    drawable = Datasource.getDrawableIdByName(it.photo),
                    contentDesc = stringResource(R.string.piece_image_desc),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(20.dp)
                        .widthIn(200.dp, 300.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Nombre
                StandardTextComp(
                    text = piece.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Atributos
                    StandardTextComp(text = "${stringResource(R.string.author)}: ${piece.author}")
                    StandardTextComp(text = "${stringResource(R.string.year)}: ${piece.year}")
                    StandardTextComp(text = "${stringResource(R.string.style)}: ${piece.style}")
                    StandardTextComp(text = "${stringResource(R.string.location)}: ${piece.location}")
                    Spacer(modifier = Modifier.height(15.dp))
                    StandardTextComp(
                        text = piece.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } ?: StandardTextComp(
                text = stringResource(R.string.piece_not_found),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(35.dp))
            StandardButtonComp(
                label = stringResource(R.string.back_button),
                onClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview
@Composable
fun PieceDetailCompactScreenPreview() {
    PieceDetailCompactScreen("La Anunciación", NavController(LocalContext.current))
}
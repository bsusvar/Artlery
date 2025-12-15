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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.model.Datasource
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.R
import com.example.artlery.ui.components.StandardTextComp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieceDetailCompactScreen(
    pieceName: String?,
    navController: NavController,
    onFavToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val piece = if (pieceName != null) Datasource.getPieceByName(pieceName) else null

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(piece?.name ?: stringResource(R.string.piece_not_found)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // Aplicamos el padding del Scaffold aquí
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            piece?.let { pieceData ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { onFavToggle(pieceData.name) }) {
                            Icon(
                                imageVector = if (pieceData.isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = stringResource(R.string.favorite_button),
                                tint = if (pieceData.isFav) Color.Red else Color.Gray
                            )
                        }
                    }
                }

                item {
                    // Imagen
                    ImageComp(
                        drawable = Datasource.getDrawableIdByName(pieceData.photo),
                        contentDesc = stringResource(R.string.piece_image_desc),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .padding(20.dp)
                            .widthIn(200.dp, 300.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    // Atributos y descripción
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StandardTextComp(
                            text = "${stringResource(R.string.author)}: ${pieceData.author}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        StandardTextComp(
                            text = "${stringResource(R.string.year)}: ${pieceData.year}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        StandardTextComp(
                            text = "${stringResource(R.string.style)}: ${pieceData.style}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        StandardTextComp(
                            text = "${stringResource(R.string.location)}: ${pieceData.location}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        StandardTextComp(
                            text = pieceData.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(35.dp))
                }
            } ?: item {
                StandardTextComp(
                    text = stringResource(R.string.piece_not_found),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}
package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.model.Datasource
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.ui.components.StandardTextComp
import com.example.artlery.ui.components.CommentCard


data class Comment(
    val id: Int,
    val author: String,
    val text: String,
    val timestamp: String
)

private val sampleComments = listOf(
    Comment(1, "Ana Pérez.", "¡Me encanta la luz y la composición!", "hace 2 días"),
    Comment(2, "Luis G.", "Muy interesante, la descripción me ayudó a entender el contexto.", "hace 1 día"),
    Comment(3, "Laura P.", "Una de mis piezas favoritas de este período.", "hace 5 horas")
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailFavScreen(
    pieceName: String?,
    navController: NavController,
    onFavToggle: (String) -> Unit, // Mantenemos el toggle por consistencia
    comments: List<Comment> = sampleComments, // Lista de comentarios
    modifier: Modifier = Modifier
) {
    val piece = if (pieceName != null) Datasource.getPieceByName(pieceName) else null

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.AddComment, contentDescription = stringResource(R.string.add_comment_desc))
            }
        },

        topBar = {
            TopAppBar(
                title = { Text(piece?.name ?: stringResource(R.string.piece_not_found)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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

                    // Título de la barra superior
//                    StandardTextComp(
//                        text = pieceData.name,
//                        style = MaterialTheme.typography.headlineMedium
//                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StandardTextComp(text = "${stringResource(R.string.author)}: ${pieceData.author}")
                        StandardTextComp(text = "${stringResource(R.string.year)}: ${pieceData.year}")
                        StandardTextComp(text = "${stringResource(R.string.style)}: ${pieceData.style}")
                        StandardTextComp(text = "${stringResource(R.string.location)}: ${pieceData.location}")
                        Spacer(modifier = Modifier.height(15.dp))
                        StandardTextComp(
                            text = pieceData.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        StandardTextComp(
                            text = stringResource(R.string.comments_title),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                        )
                    }
                }

                items(comments) { comment ->
                    CommentCard(comment = comment)
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }

            } ?: item {
                StandardTextComp(
                    text = stringResource(R.string.piece_not_found),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailFavScreenPreview() {
    val emptyToggle: (String) -> Unit = {}

    Datasource.getPieceByName("La Anunciación")?.isFav = true

    DetailFavScreen(
        pieceName = "La Anunciación",
        navController = NavController(LocalContext.current),
        onFavToggle = emptyToggle
    )
}
package com.example.artlery.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.CommentCard
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.ui.components.StandardTextComp
import com.example.artlery.ui.viewmodels.ArtworkDetailViewModel
import com.example.artlery.ui.viewmodels.DetailUiState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieceDetailCompactScreen(
    artworkId: Int?,
    navController: NavController,
    userName: String,
    modifier: Modifier = Modifier,
    viewModel: ArtworkDetailViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(artworkId) {
        viewModel.loadArtwork(artworkId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    var showRemoveDialog by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }
    var newCommentText by remember { mutableStateOf("") }

    when (val state = uiState) {
        is DetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                StandardTextComp(text = stringResource(R.string.piece_not_found))
            }
        }

        is DetailUiState.Success -> {
            val pieceData = state.piece

            Scaffold(
                modifier = modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { showCommentDialog = true },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            Icons.Filled.AddComment,
                            contentDescription = stringResource(R.string.add_comment_desc)
                        )
                    }

                },
                topBar = {
                    TopAppBar(
                        title = { Text(pieceData.name) },
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
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                viewModel.toggleFavorite(onDuplicateAlert = {
                                    Toast.makeText(
                                        context,
                                        "El elemento ya está guardado como favorito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                            }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = stringResource(R.string.favorite_button),
                                    tint = if (isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    }

                    item {
                        ImageComp(
                            photoUrl = pieceData.photo,
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
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            StandardTextComp(
                                text = "${stringResource(R.string.author)}: ${pieceData.author}",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            StandardTextComp(
                                text = "${stringResource(R.string.year)}: ${pieceData.year}",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            StandardTextComp(
                                text = "${stringResource(R.string.style)}: ${pieceData.style}",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            StandardTextComp(
                                text = "${stringResource(R.string.location)}: ${pieceData.location}",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            StandardTextComp(
                                text = pieceData.description,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )

                            if (isFavorite) {
                                Spacer(modifier = Modifier.height(30.dp))
                                StandardTextComp(
                                    text = stringResource(R.string.comments_title),
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 10.dp)
                                )
                            }
                        }
                    }

                    items(state.comments) { comment ->
                        val legacyComment = com.example.artlery.ui.screens.Comment(
                            id = comment.id,
                            author = comment.author,
                            text = comment.text,
                            timestamp = comment.timestamp
                        )
                        CommentCard(comment = legacyComment)
                    }


                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }

    if (showCommentDialog) {
        AlertDialog(
            onDismissRequest = { showCommentDialog = false },
            title = { Text(text = stringResource(R.string.add_comment_title)) },
            text = {
                Column {
                    StandardTextComp(
                        text = stringResource(R.string.commenting_as, userName),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newCommentText,
                        onValueChange = { newCommentText = it },
                        label = { Text(stringResource(R.string.comment_input_label)) },
                        singleLine = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newCommentText.isNotBlank()) {
                            val formatter =
                                DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
                            val time = LocalDateTime.now().format(formatter)
                            viewModel.addComment(
                                author = userName,
                                text = newCommentText,
                                timestamp = "${context.getString(R.string.timestamp_today)}, $time"
                            )
                            newCommentText = ""
                            showCommentDialog = false
                        }
                    },
                    enabled = newCommentText.isNotBlank()
                ) {
                    Text(stringResource(R.string.send_button))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    newCommentText = ""
                    showCommentDialog = false
                }) {
                    Text(stringResource(R.string.cancel_button))
                }
            }
        )
    }
}
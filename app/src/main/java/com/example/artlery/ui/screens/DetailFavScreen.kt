package com.example.artlery.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.model.Datasource
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.CommentCard
import com.example.artlery.ui.components.ImageComp
import com.example.artlery.ui.components.StandardTextComp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


data class Comment(
    val id: Int,
    val author: String,
    val text: String,
    val timestamp: String
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailFavScreen(
    pieceName: String?,
    navController: NavController,
    onFavToggle: (String) -> Unit,
    pieces: MutableList<Piece>,
    userName: String,
    modifier: Modifier = Modifier
) {
    val piece = pieces.find { it.name == pieceName }

    val commentTwoDays = stringResource(R.string.timestamp_two_days)
    val commentOneDay = stringResource(R.string.timestamp_one_day)
    val commentFiveHours = stringResource(R.string.timestamp_five_hours)
    val commentToday = stringResource(R.string.timestamp_today)

    val initialComments = remember {
        listOf(
            Comment(
                1,
                "Ana Pérez.",
                "¡Me encanta la luz y la composición!",
                commentTwoDays
            ),

            Comment(
                2,
                "Luis G.",
                "Muy interesante, la descripción me ayudó a entender el contexto.",
                commentOneDay
            ),
            Comment(
                3,
                "Laura P.",
                "Una de mis piezas favoritas de este período.",
                commentFiveHours
            )
        )
    }


    var showDialog by remember { mutableStateOf(false) }
    var comments by remember { mutableStateOf(initialComments) }

    var showCommentDialog by remember { mutableStateOf(false) }
    var newCommentText by remember { mutableStateOf("") }

    if (showDialog && piece != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { StandardTextComp(text = stringResource(R.string.remove_fav_title)) },
            text = { StandardTextComp(text = stringResource(R.string.remove_fav_text)) },
            confirmButton = {
                TextButton(onClick = {
                    onFavToggle(piece.name)
                    showDialog = false
                }) {
                    StandardTextComp(text = stringResource(R.string.remove_fav_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    StandardTextComp(text = stringResource(R.string.remove_fav_cancel))
                }
            }
        )
    }


    val onSendComment: () -> Unit = {
        if (newCommentText.isNotBlank() && userName != "Visitante") {
            val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
            val time = LocalDateTime.now().format(formatter)

            val newComment = Comment(
                id = comments.size + 1,
                author = userName,
                text = newCommentText,
                timestamp = "$commentToday, $time"
            )
            comments = comments + newComment

            newCommentText = ""
            showCommentDialog = false
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (userName != "Visitante") {
                        showCommentDialog = true
                    } else {
                        println("ERROR: Debe iniciar sesión para comentar.")
                    }
                },
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
                        IconButton(onClick = {
                            if (pieceData.isFav) {
                                showDialog = true
                            } else {
                                onFavToggle(pieceData.name)
                            }
                        }) {
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
                items(comments.reversed()) { comment ->
                    CommentCard(comment = comment)
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }

            } ?: item {
                StandardTextComp(
                    text = stringResource(R.string.piece_not_found),
                    style = MaterialTheme.typography.bodyLarge
                )
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
                        onClick = onSendComment,
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
}
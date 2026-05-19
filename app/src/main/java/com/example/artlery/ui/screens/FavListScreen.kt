package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.FavPieceCardLand
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.PieceCard
import com.example.artlery.ui.components.StandardTextComp
import com.example.artlery.ui.viewmodels.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavListCompactScreen(
    navController: NavController,
    onRemoveFromFav: (Piece) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel()
) {
    val favoritePieces by viewModel.favoritePieces.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var pieceToUnfav by remember { mutableStateOf<Piece?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.artlery_fav_list))

        if (showDialog && pieceToUnfav != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { StandardTextComp(text = stringResource(R.string.remove_fav_title)) },
                text = { StandardTextComp(text = stringResource(R.string.remove_fav_text)) },
                confirmButton = {
                    TextButton(onClick = {
                        pieceToUnfav?.let { onRemoveFromFav(it) }
                        showDialog = false
                    }) {
                        StandardTextComp(text = stringResource(R.string.remove_fav_confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        StandardTextComp(text = stringResource(R.string.remove_fav_cancel))
                    }
                }
            )
        }

        if (favoritePieces.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                StandardTextComp(
                    text = stringResource(R.string.no_favorites_message),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(favoritePieces) { piece ->
                    PieceCard(
                        piece = piece,
                        onCardClick = {
                            navController.navigate("piece_detail/${piece.id}")
                        },
                        onFavClick = { clickedPiece ->
                            pieceToUnfav = clickedPiece
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavListMedExpScreen(
    navController: NavController,
    onRemoveFromFav: (Piece) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel()
) {
    val favoritePieces by viewModel.favoritePieces.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var pieceToUnfav by remember { mutableStateOf<Piece?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.artlery_fav_list))

        if (showDialog && pieceToUnfav != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { StandardTextComp(text = stringResource(R.string.remove_fav_title)) },
                text = { StandardTextComp(text = stringResource(R.string.remove_fav_text)) },
                confirmButton = {
                    TextButton(onClick = {
                        pieceToUnfav?.let { onRemoveFromFav(it) }
                        showDialog = false
                    }) {
                        StandardTextComp(text = stringResource(R.string.remove_fav_confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        StandardTextComp(text = stringResource(R.string.remove_fav_cancel))
                    }
                }
            )
        }

        if (favoritePieces.isEmpty()) {
            StandardTextComp(
                text = stringResource(R.string.no_favorites_message),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 16.dp),
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(favoritePieces) { piece ->
                    FavPieceCardLand(
                        piece = piece,
                        onClick = {
                            navController.navigate("piece_detail/${piece.id}")
                        },
                        onRemoveFromFav = {
                            pieceToUnfav = piece
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavListScreenPreview() {
    FavListCompactScreen(
        navController = NavController(LocalContext.current),
        onRemoveFromFav = {},
        modifier = Modifier
    )
}
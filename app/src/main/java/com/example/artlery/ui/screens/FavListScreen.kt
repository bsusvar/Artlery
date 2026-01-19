package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.model.Datasource
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.FavPieceCard
import com.example.artlery.ui.components.FavPieceCardLand
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.StandardTextComp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavListCompactScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    onRemoveFromFav: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }
    var pieceToUnfav by remember { mutableStateOf<Piece?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.artlery_fav_list))

        val favoritePieces = pieces.filter { it.isFav }

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
                    .padding(vertical = 32.dp),
            )
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                items(favoritePieces) { piece ->
                    FavPieceCard(
                        piece = piece,
                        onCardClick = {
                            navController.navigate("detail_fav/${piece.name}")
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

@Composable
fun FavListMedExpScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    onRemoveFromFav: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }
    var pieceToUnfav by remember { mutableStateOf<Piece?>(null) }

    Column(modifier = modifier.fillMaxSize()) {

        MedHeaderComp(stringResource(R.string.artlery_fav_list))

        val favoritePieces = pieces.filter { it.isFav }

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
                            navController.navigate("detail_fav/${piece.name}")
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
    val allPieces = Datasource.pieceList()

    val previewPieces = allPieces.mapIndexed { index, piece ->

        if (index < 2) piece.copy(isFavInitial = true) else piece
    }.toMutableList()

    FavListCompactScreen(
        pieces = previewPieces,
        navController = NavController(LocalContext.current),
        onRemoveFromFav = {},
        modifier = Modifier
    )
}
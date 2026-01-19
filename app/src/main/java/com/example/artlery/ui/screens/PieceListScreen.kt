package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
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
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.PieceCard
import com.example.artlery.ui.components.PieceCardLand
import com.example.artlery.ui.components.StandardTextComp

@Composable
fun PieceListCompactScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var pieceToUnfav by remember { mutableStateOf<Piece?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.piece_list))

        if (showDialog && pieceToUnfav != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { StandardTextComp(text = stringResource(R.string.remove_fav_title)) },
                text = { StandardTextComp(text = stringResource(R.string.remove_fav_text)) },
                confirmButton = {
                    TextButton(onClick = {
                        pieceToUnfav?.let { onFavToggle(it) }
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(pieces) { piece ->
                PieceCard(
                    piece = piece,
                    onCardClick = {
                        navController.navigate("piece_detail/${piece.name}")
                    },
                    onFavClick = { clickedPiece ->
                        if (clickedPiece.isFav) {
                            pieceToUnfav = clickedPiece
                            showDialog = true
                        } else {
                            onFavToggle(clickedPiece)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PieceListMedExpScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }
    var pieceToUnfav by remember { mutableStateOf<Piece?>(null) }

    Column(modifier = modifier.fillMaxSize()) {

        MedHeaderComp(title = stringResource(id = R.string.piece_list))

        if (showDialog && pieceToUnfav != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { StandardTextComp(text = stringResource(R.string.remove_fav_title)) },
                text = { StandardTextComp(text = stringResource(R.string.remove_fav_text)) },
                confirmButton = {
                    TextButton(onClick = {
                        pieceToUnfav?.let { onFavToggle(it) }
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(pieces) { piece ->
                PieceCardLand(
                    piece = piece,
                    onClick = {
                        navController.navigate("piece_detail/${piece.name}")
                    },
                    onFavClick = { clickedPiece ->
                        if (clickedPiece.isFav) {
                            pieceToUnfav = clickedPiece
                            showDialog = true
                        } else {
                            onFavToggle(clickedPiece)
                        }
                    }
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieceListScreenPreview() {
    val pieces = Datasource.pieceList().toMutableList()

    PieceListCompactScreen(
        pieces = pieces,
        navController = NavController(LocalContext.current),
        onFavToggle = {},
        modifier = Modifier
    )
}
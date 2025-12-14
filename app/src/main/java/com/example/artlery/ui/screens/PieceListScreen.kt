package com.example.artlery.ui.screens

import com.example.artlery.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artlery.model.Piece
import androidx.navigation.NavController
import com.example.artlery.model.Datasource
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.PieceCard
import com.example.artlery.ui.components.PieceCardLand

// Incluir botón para favs
@Composable
fun PieceListCompactScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.piece_list))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(pieces) { piece ->
//                val onFavToggle: (Piece) -> Unit = { p ->
//                    p.isFav = !p.isFav
//                }
                PieceCard(
                    piece = piece,
                    onCardClick = {
                        navController.navigate("piece_detail/${piece.name}")
                    },
                    onFavClick = onFavToggle
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
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.piece_list))
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
                    onFavClick = onFavToggle
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
package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.R
import com.example.artlery.model.Datasource
import com.example.artlery.ui.components.FavPieceCard
import com.example.artlery.ui.components.FavPieceCardLand
import com.example.artlery.ui.components.StandardTextComp


// Pantalla con la lista de elementos que han sido marcados como favoritos. En esta pantalla debe haber un botón para eliminar de favoritos.

@Composable
fun FavListCompactScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    onRemoveFromFav: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.artlery_fav_list))
        val favoritePieces = pieces.filter { it.isFav }

        if (favoritePieces.isEmpty()) {
            StandardTextComp(
                text = stringResource(R.string.no_favorites_message),
                style = MaterialTheme.typography.headlineMedium,
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
                        onRemoveFromFav = onRemoveFromFav
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
    Column(modifier = modifier.fillMaxSize()) {

        MedHeaderComp(stringResource(R.string.artlery_fav_list))

        val favoritePieces = pieces.filter { it.isFav }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            items(favoritePieces) { piece ->
                FavPieceCardLand(
                    piece = piece,
                    onRemoveFromFav = onRemoveFromFav,
                    onClick = {
                        navController.navigate("detail_fav/${piece.name}")
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavListScreenPreview() {
    val allPieces = Datasource.pieceList()

    val previewPieces = allPieces.mapIndexed { index, piece ->

        if (index < 2) piece.copy(isFav = true) else piece
    }.toMutableList()

    FavListCompactScreen(
        pieces = previewPieces,
        navController = NavController(LocalContext.current),
        onRemoveFromFav = {},
        modifier = Modifier
    )
}
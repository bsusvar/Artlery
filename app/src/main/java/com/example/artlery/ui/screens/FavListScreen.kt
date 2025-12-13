package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.R
import com.example.artlery.ui.components.FavPieceCardLand


// Pantalla con la lista de elementos que han sido marcados como favoritos. En esta pantalla debe haber un botón para eliminar de favoritos.

@Composable
fun FavListCompactScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.artlery_fav_list))
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(pieces) { piece ->
                FavPieceCard(piece) {
                    navController.navigate("piece_detail/${piece.name}")
                }
            }
        }
    }
}

@Composable
fun FavListMedExpScreen(
    pieces: MutableList<Piece>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            items(pieces) { piece ->
                FavPieceCardLand(piece) {
                    navController.navigate("piece_detail/${piece.name}")
                }
            }
        }
    }
)
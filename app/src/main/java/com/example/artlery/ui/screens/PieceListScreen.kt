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
import androidx.compose.ui.unit.dp
import com.example.artlery.model.Piece

@Composable
fun PieceListCompactScreen(
    pieces: MutableList<Piece>,
navController = NavController,
    modifier: Modifier = Modifier
    ) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = R.string.piece_list_compact_title)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(pieces) { piece ->
                PieceCard(piece = piece) {
                    navController.navigate("piece_detail/${piece.name}")
                }
            }
        }
    }
}

@Composable
fun PieceListMedExpScreen(
    pieces: MutableList<Piece>,
navController = NavController,
    modifier: Modifier = Modifier
    ) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = R.string.piece_list_med_exp_title)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(pieces) { piece ->
                PieceCardLand(piece) {
                    navController.navigate("piece_detail/${piece.name}")
                }
            }
        }
    }
}

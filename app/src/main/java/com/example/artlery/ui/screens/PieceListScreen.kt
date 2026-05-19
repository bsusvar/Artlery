package com.example.artlery.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artlery.R
import com.example.artlery.model.Piece
import com.example.artlery.ui.components.MedHeaderComp
import com.example.artlery.ui.components.PieceCard
import com.example.artlery.ui.components.PieceCardLand
import com.example.artlery.ui.components.StandardInputTextComp
import com.example.artlery.ui.components.StandardTextComp
import com.example.artlery.ui.viewmodels.ArtApiViewModel
import com.example.artlery.ui.viewmodels.ArtUiState

@Composable
fun PieceListCompactScreen(
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtApiViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ArtUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ArtUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                StandardTextComp(text = "Error al cargar las obras desde la API.")
            }
        }
        is ArtUiState.Success -> {
            PieceListCompactContent(
                pieces = state.pieces,
                navController = navController,
                onFavToggle = onFavToggle,
                modifier = modifier
            )
        }
    }
}

@Composable
fun PieceListCompactContent(
    pieces: List<Piece>,
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredPieces = pieces.filter { it.containsText(searchQuery) }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.piece_list))

        StandardInputTextComp(
            label = stringResource(R.string.search_label),
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(filteredPieces, key = { it.id }) { piece ->
                PieceCard(
                    piece = piece,
                    onCardClick = {
                        navController.navigate("piece_detail/${piece.id}")
                    },
                    onFavClick = { clickedPiece ->
                        onFavToggle(clickedPiece)
                    }
                )
            }
        }
    }
}

@Composable
fun PieceListMedExpScreen(
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtApiViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ArtUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ArtUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                StandardTextComp(text = "Error al cargar las obras desde la API.")
            }
        }
        is ArtUiState.Success -> {
            PieceListMedExpContent(
                pieces = state.pieces,
                navController = navController,
                onFavToggle = onFavToggle,
                modifier = modifier
            )
        }
    }
}

@Composable
fun PieceListMedExpContent(
    pieces: List<Piece>,
    navController: NavController,
    onFavToggle: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredPieces = pieces.filter { it.containsText(searchQuery) }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.piece_list))

        StandardInputTextComp(
            label = stringResource(R.string.search_label),
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(filteredPieces, key = { it.id }) { piece ->
                PieceCardLand(
                    piece = piece,
                    onClick = {
                        navController.navigate("piece_detail/${piece.id}")
                    },
                    onFavClick = { clickedPiece ->
                        onFavToggle(clickedPiece)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PieceListScreenPreview() {
    PieceListCompactContent(
        pieces = emptyList(),
        navController = NavController(LocalContext.current),
        onFavToggle = {},
        modifier = Modifier
    )
}
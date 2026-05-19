package com.example.artlery.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.artlery.R
import com.example.artlery.model.Datasource
import com.example.artlery.model.Piece

@Composable
fun PieceCard(
    piece: Piece,
    onCardClick: () -> Unit,
    onFavClick: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageComp(
                modifier = Modifier,
                photoUrl = piece.photo,
                height = 100,
                width = 100
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                StandardTextComp(
                    text = piece.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(5.dp))
                StandardTextComp(
                    text = piece.author,
                    style = MaterialTheme.typography.bodyMedium
                )
                StandardTextComp(
                    text = piece.year,
                    style = MaterialTheme.typography.bodyMedium
                )
                StandardTextComp(
                    text = piece.style,
                    style = MaterialTheme.typography.bodyMedium
                )
                StandardTextComp(
                    text = piece.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(
                onClick = { onFavClick(piece) },
            )
            {
                Icon(
                    imageVector = if (piece.isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_button),
                    tint = if (piece.isFav) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
fun PieceCardLand(
    piece: Piece, onClick: () -> Unit,
    onFavClick: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clickable { onClick() },

        shape = MaterialTheme.shapes.large
    )
    {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ImageComp(
                modifier = Modifier,
                photoUrl = piece.photo,
                height = 100,
                width = 100
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StandardTextComp(
                        text = piece.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(
                        onClick = { onFavClick(piece) }
                    ) {
                        Icon(
                            imageVector = if (piece.isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite_button),
                            tint = if (piece.isFav) Color.Red else Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    StandardTextComp(
                        text = piece.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    StandardTextComp(
                        text = piece.year,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                StandardTextComp(
                    text = piece.style,
                    style = MaterialTheme.typography.bodySmall
                )
                StandardTextComp(
                    text = piece.location,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun FavPieceCard(
    piece: Piece,
    onCardClick: () -> Unit,
    onRemoveFromFav: (Piece) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .clickable { onCardClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                ImageComp(
                    photoUrl = piece.photo,
                    height = 100,
                    width = 100
                )

                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                ) {
                    StandardTextComp(
                        text = piece.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    StandardTextComp(
                        text = piece.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    StandardTextComp(
                        text = piece.year,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    StandardTextComp(
                        text = piece.style,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    StandardTextComp(
                        text = piece.location,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            IconButton(
                onClick = {
                    Log.d("FavPieceCard", "Botón Eliminar pulsado para ${piece.name}")
                    onRemoveFromFav(piece)
                },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    modifier = Modifier.size(24.dp),
                    contentDescription = stringResource(R.string.delete_desc),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun FavPieceCardLand(
    piece: Piece,
    onRemoveFromFav: (Piece) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ImageComp(
                modifier = Modifier,
                photoUrl = piece.photo,
                height = 150,
                width = 150
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StandardTextComp(
                        text = piece.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(
                        onClick = {
                            Log.d("FavPieceCardLand", "Botón Eliminar pulsado para ${piece.name}")
                            onRemoveFromFav(piece)
                        },
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            modifier = Modifier.size(50.dp),
                            contentDescription = stringResource(R.string.delete_desc),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    StandardTextComp(
                        text = piece.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    StandardTextComp(
                        text = piece.year,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                StandardTextComp(
                    text = piece.style,
                    style = MaterialTheme.typography.bodySmall
                )
                StandardTextComp(
                    text = piece.location,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CommentCard(comment: com.example.artlery.ui.screens.Comment, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            StandardTextComp(
                text = comment.author,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            StandardTextComp(
                text = comment.text,
                style = MaterialTheme.typography.bodyMedium
            )
            StandardTextComp(
                text = comment.timestamp,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
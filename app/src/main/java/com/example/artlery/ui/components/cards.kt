package com.example.artlery.ui.components

import android.inputmethodservice.Keyboard
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
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
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
            horizontalArrangement = Arrangement.SpaceBetween // Si no, SpaceAround
        ) {
            // Imagen
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(piece.photo),
                height = 100,
                width = 100
            )
            // Atributos
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                // Nombre
                StandardTextComp(
                    text = piece.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(5.dp))
                // Autor
                StandardTextComp(
                    text = piece.author,
                    style = MaterialTheme.typography.bodyMedium // Añadir fonts
                )
                // Año
                StandardTextComp(
                    text = piece.year,
                    style = MaterialTheme.typography.bodyMedium // Añadir fonts
                )
                // Estilo
                StandardTextComp(
                    text = piece.style,
                    style = MaterialTheme.typography.bodyMedium // Añadir fonts
                )
                // Localización
                StandardTextComp(
                    text = piece.location,
                    style = MaterialTheme.typography.bodyMedium // Añadir fonts
                )
//                // Descripción
//                StandardTextComp(
//                    text = piece.description,
//                    style = MaterialTheme.typography.bodyMedium // Añadir fonts
//                )
            }
            // Botón de acción con icono para acciones futuras
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
fun PieceCardLand(piece: Piece, onClick: () -> Unit) {
    Card(
        modifier = Modifier
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
            // Imagen
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(piece.photo),
                height = 100,
                width = 100
            )
            // Atributos
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    StandardTextComp(
                        text = piece.name,
                        style = MaterialTheme.typography.titleMedium
                    )
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
        }
    }
}

@Composable
fun FavPieceCard(piece: Piece, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Imagen
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(piece.photo),
                height = 100,
                width = 100
            )
            // Atributos
            Column(
                modifier = Modifier
                    .weight(1f)
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
                // Quizás a partir de aquí sobre
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
                Column(verticalArrangement = Arrangement.SpaceAround) {
                    // Botón de acción con icono
                    IconButton(
                        onClick = {
                            Log.d(
                                "FavPieceCard",
                                "Botón Eliminar pulsado"
                            )
                        },
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            modifier = Modifier.size(50.dp),
                            contentDescription = stringResource(R.string.delete_desc),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    IconButton(
                        onClick = {
                            Log.d(
                                "FavPieceCard",
                                "Botón Más pulsado"
                            )
                        },
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.KeyboardArrowDown,
                            modifier = Modifier.size(50.dp),
                            contentDescription = stringResource(R.string.more_content_desc),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavPieceCardLand(piece: Piece, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Imagen
            ImageComp(
                modifier = Modifier,
                drawable = Datasource.getDrawableIdByName(piece.photo),
                height = 150,
                width = 150
            )
            // Atributos
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    StandardTextComp(
                        text = piece.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    StandardTextComp(
                        text = piece.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    // ...etc?
                    IconButton(
                        onClick = {
                            Log.d(
                                "FavPieceCard",
                                "Botón Favorito pulsado"
                            )
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
                StandardTextComp(
                    text = stringResource(R.string.piece_description),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }

    }
}
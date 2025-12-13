package com.example.artlery.ui.components

import android.R.attr.width
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.artlery.R
import com.example.artlery.model.Datasource
import com.example.artlery.model.Piece

@Composable
fun PieceCard(piece: Piece, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },

        shape = MaterialTheme.shapes.large // Cambiar a medium si eso
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
                height = 100.dp,
                width = 100.dp
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
                // Descripción
                StandardTextComp(
                    text = piece.description,
                    style = MaterialTheme.typography.bodyMedium // Añadir fonts
                )
            }
            // Botón de acción con icono para acciones futuras
            IconButton(
                onClick = {},
                modifier = Modifier.size(50.dp)
            )
            {
                Icon(
                    imageVector = Icons.TwoTone.KeyboardArrowDown, // Mirar
                    modifier = Modifier.size(50.dp),
                    contentDescription = stringResource(R.string.more_content_desc)
                )
            }
        }
    }


}
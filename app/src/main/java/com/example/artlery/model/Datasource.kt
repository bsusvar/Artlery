package com.example.artlery.model

import androidx.compose.runtime.mutableStateListOf
import com.example.artlery.R

object Datasource {

    val pieceList: () -> List<Piece> = {
        mutableStateListOf(
            Piece(
                id = 1,
                name = "La Anunciación",
                photo = "anunciacion",
                author = "Fra Angelico",
                year = "1425-1426",
                style = "Renacimiento",
                location = "Museo del Prado (España)",
                description = "Realizado con oro y temple al huevo sobre tabla. La escena trata de la Anunciación a la Virgen María y cinco escenas menores más."
            ),
            Piece(
                id = 2,
                name = "Estudio del retrato del papa Inocencio X",
                photo = "inocencio_x",
                author = "Francis Bacon",
                year = "1953",
                style = "Expresionismo",
                location = "Des Moines Art Center (Estados Unidos)",
                description = "Transformación del papa Inocencio X de Velázquez para convertirlo, en su versión, en el emblema de los horrores cometidos en nombre de la religión a lo largo de la historia."
            ),
            Piece(
                id = 3,
                name = "Perro semihundido",
                photo = "perro_semihundido",
                author = "Francisco de Goya",
                year = "1820-1823",
                style = "Romanticismo",
                location = "Museo del Prado (España)",
                description = "Óleo sobre revoco trasladado a lienzo. Se interpreta que el perro mira interesado el vuelo de unos pájaros, mientras que algunos expertos opinan que el cuadro nunca llegó a terminarse."
            ),
            Piece(
                id = 4,
                name = "La habitación azul",
                photo = "habitacion_azul",
                author = "Pablo Picasso",
                year = "1901",
                style = "Periodo azul",
                location = "The Phillips Collection (Estados Unidos)",
                description = "Obra dominada por los tonos azules característicos de la etapa del artista. Muestra a una mujer desnuda inclinada en la bañera, rodeada por elementos homenaje a obras de otros pintores."
            ),
            Piece(
                id = 5,
                name = "Paseo a orillas del mar",
                photo = "paseo_orillas_mar",
                author = "Joaquín Sorolla",
                year = "1909",
                style = "Luminismo valenciano",
                location = "Museo Sorolla (España)",
                description = "Obra en la que aparecen Clotilde García, mujer del pintor, junto a su hija mayor, María Clotilde, caminando al atardecer por la playa de Valencia acompañadas por la brisa marina."
            )
        )
    }

    val getListXTimes: (Int) -> MutableList<Piece> = { times ->
        val list = mutableListOf<Piece>()
        for (i in 1..times) {
            list.addAll(pieceList())
        }
        list.shuffle()
        list
    }

    val getPieceByName: (String) -> Piece? = { name ->
        pieceList().find { it.name == name }
    }

    val getSomeRandPieces: (Int) -> MutableList<Piece> = { num ->
        val pieces = pieceList()
        if (num <= pieces.size) {
            pieces.shuffled().take(num).toMutableList()
        } else {
            pieces.shuffled().toMutableList()
        }
    }

    fun getDrawableIdByName(name: String): Int {
        return when (name) {
            "anunciacion" -> R.drawable.anunciacion
            "inocencio_x" -> R.drawable.inocencio_x
            "perro_semihundido" -> R.drawable.perro_semihundido
            "habitacion_azul" -> R.drawable.habitacion_azul
            "paseo_orillas_mar" -> R.drawable.paseo_orillas_mar
            else -> R.drawable.a_icono
        }
    }
}
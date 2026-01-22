package com.example.artlery.model

import androidx.compose.runtime.mutableStateListOf
import com.example.artlery.R


object Datasource {

    val pieceList: () -> List<Piece> = {
        mutableStateListOf(
            Piece(

                "La Anunciación",
                "anunciacion",
                "Fra Angelico",
                "1425-1426",
                "Renacimiento",
                "Museo del Prado (España)",
                "Realizado con oro y temple al huevo sobre tabla. La escena trata de la Anunciación a la Virgen María y cinco escenas menores más."
            ),
            Piece(
                "Estudio del retrato del papa Inocencio X",
                "inocencio_x",
                "Francis Bacon",
                "1953",
                "Expresionismo",
                "Des Moines Art Center (Estados Unidos)",
                "Transformación del papa Inocencio X de Velázquez para convertirlo, en su versión, en el emblema de los horrores cometidos en nombre de la religión a lo largo de la historia."
            ),
            Piece(
                "Perro semihundido",
                "perro_semihundido",
                "Francisco de Goya",
                "1820-1823",
                "Romanticismo",
                "Museo del Prado (España)",
                "Óleo sobre revoco trasladado a lienzo. Se interpreta que el perro mira interesado el vuelo de unos pájaros, mientras que algunos expertos opinan que el cuadro nunca llegó a terminarse."
            ),
            Piece(
                "La habitación azul",
                "habitacion_azul",
                "Pablo Picasso",
                "1901",
                "Periodo azul",
                "The Phillips Collection (Estados Unidos)",
                "Obra dominada por los tonos azules característicos de la etapa del artista. Muestra a una mujer desnuda inclinada en la bañera, rodeada por elementos homenaje a obras de otros pintores,"
            ),
            Piece(
                "Paseo a orillas del mar",
                "paseo_orillas_mar",
                "Joaquín Sorolla",
                "1909",
                "Luminismo valenciano",
                "Museo Sorolla (España)",
                "Obra en la que aparecen Clotilde García, mujer del pintor, junto a su hija mayor, María Clotilde, caminando al atardecer por la playa de Valencia acompañadas por la brisa marina."

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
            pieces.shuffled().take(num) as MutableList<Piece>
        } else {
            pieces.shuffled() as MutableList<Piece>
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

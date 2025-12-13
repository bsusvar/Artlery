package com.example.artlery.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass


@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun getWindowSizeClass(activity: Activity): WindowWidthSizeClass {
    return calculateWindowSizeClass(activity).widthSizeClass
}

/*

/*
Introducción.
Para la realización de esta práctica se debe continuar con la práctica comenzada en la unidad didáctica anterior titulada “Creación y análisis de una aplicación (Proyecto)”.

En esta práctica se diseñarán las diferentes pantallas de la aplicación y se creará un sistema de navegación para poder recorrer todas las pantallas creadas. En la aplicación se hará uso de diferentes menús para conseguir una navegación completa y eficiente.

Desarrollo.
Tras aprender todas las bases necesarias para el diseño de interfaces móviles adaptadas a los tamaños de ventana, ha llegado el momento de seguir avanzando en el proyecto. Debes realizar las siguientes tareas:

Crea un tema completamente personalizado para esta app según las siguientes restricciones:
Los colores deben estar basados en los del logo de la app.
Se pueden realizar adaptaciones de colores de los principales roles a definir.
Se deben crear al menos dos colores personalizados para usarlos en algún elemento.
Especifica las fuentes usadas para la app y porqué las has usado, es decir, que tipo de fuente has buscado en base a qué criterios.
Muestra y explica la creación de un par de componentes con estilos personalizados, como la tarjeta de elementos y otro componente de tu elección.
La app debe tener las siguientes pantallas (Screens) en formato compacto:
ElemListScreen: Pantalla con la lista de elementos de los que trata la app. Cada elemento debe estar contenido en una tarjeta (Card). Esta tarjeta debe contener la información básica que estimes oportuna y botón para poder añadir a favoritos.
DetailItemScreen: Pantalla con todos los datos detallados del elemento que se ha pulsado en la lista de elementos de la pantalla anterior. Aquí también debe aparecer un botón para incluir a favoritos.
FavListScreen:  Pantalla con la lista de elementos que han sido marcados como favoritos. En esta pantalla debe haber un botón para eliminar de favoritos.
DetailFavScreen: Pantalla de detalles del elemento favorito que se ha seleccionado de la lista. Debe mostrar una lista de comentarios asociado a este elemento y un botón FAB para permitir incluir nuevos comentarios.
ProfileScreen: Pantalla con información del usuario con un botón para hacer login/logout.
AboutScreen: Pantalla con información sobre la app. (Ya creada en la práctica anterior)
Crear una variante de la interfaz para las ventanas de tamaño Medio y Expandido. Esta variante debe crearse para las pantallas de las listas y de los detalles de los elementos. (ElemListScreen y DetailItemScreen).
Configurar la automatización de visualización de las Screens en función del tamaño de ventana, mostrando la pantalla en formato compacto o medio/expandido según corresponda.
Realiza pruebas de uso en el emulador, mostrando las interfaces creadas para la lista general de elementos y la vista de detalles de un elemento. Realiza ambas capturas de pantalla de estas interfaces en:
Dispositivo móvil en vertical.
Dispositivo tablet en vertical.
*Nota: se puede crear un modelo de datos de pruebas para mostrar las interfaces de una manera más realista. Puedes seguir el procedimiento llevado a cabo en el laboratorio creando una data class para los elementos de la lista.

Criterios de calificación de la tarea.
Documentación: Se debe aportar un documento con todos los apartados especificados asociando las soluciones a cada apartado de forma clara e inequívoca.
Justificación: Todos los apartados deben estar justificados y aportar el código y las capturas necesarias en caso de que se soliciten.
Proyecto: Se debe incluir una copia del proyecto en formato "zip" por si fuera necesario la realización de pruebas de funcionamiento de la app.
Calidad del Código: Evaluación del uso de buenas prácticas, como nomenclatura, modularización y claridad.
Criterios de evaluación.
Esta práctica está asociada a los Criterios de evaluación que se relacionan a continuación:

CE 2.a. Se ha generado la estructura de clases necesaria para la aplicación.
C.E. 2.b. Se han analizado y utilizado las clases que modelan las ventanas, menús, alertas y controles para el desarrollo de aplicaciones gráficas sencillas.
C.E. 2.g. Se han realizado pruebas de interacción usuario-aplicación para optimizar las aplicaciones desarrolladas a partir de emuladores.
 */

 */
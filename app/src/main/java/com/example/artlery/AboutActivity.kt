package com.example.artlery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artlery.ui.theme.ArtleryTheme


/*
2. Añadir una Activity "Sobre la Aplicación":

   - En esta primera práctica, el proyecto solo contendrá una Activity que mostrará información básica sobre la aplicación.
   - En la pantalla de "Sobre la Aplicación" debes incluir algunos elementos básicos como:
     - El nombre de la aplicación.
     - La temática de la aplicación.
     - Una breve descripción del propósito de la app.
     - La versión de la app.
     - Un icono representativo de envío o correo electrónico.
 */

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            About()
        }
    }
}

@Preview
@Composable
fun About() {
    ArtleryTheme {
        Scaffold(
            topBar = {
                ToolBar()
            },
            content = {
                Content()
            },
            bottomBar = {
                BottomBar()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar() {

    val context = LocalContext.current // Esto es si uso toast

    TopAppBar(
        title = {
            Text(text = "Sobre Artlery")
            Color.Blue
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        )
    )
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                top = 30.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .background(Color.DarkGray, shape = RectangleShape)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RectangleShape
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        this.item {
            Image(
                painter = painterResource(id = R.drawable.ic_logo), // PONER LOGO
                contentDescription = "Logo Artlery"
            )
            Text(
                text = """Artlery es la primera red social dedicada a las artes plásticas.
                    Aprende más sobre tus obras favoritas, colabora en la difusión del conocimiento y construye comunidad.
                    Un oasis cultural por y para amantes de lo creativo.
                    """,
                // HABLAR DE LA APP
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 5.dp) // Separación imagen
            )
        }
    }

}

@Composable
fun BottomBar() {
// AÑADIR LOGOS Y COSAS QUE FALTAN

    /*
    2. Añadir una Activity "Sobre la Aplicación":

   - En esta primera práctica, el proyecto solo contendrá una Activity que mostrará información básica sobre la aplicación.
   - En la pantalla de "Sobre la Aplicación" debes incluir algunos elementos básicos como:
     - El nombre de la aplicación.
     - La temática de la aplicación.
     - Una breve descripción del propósito de la app.
     - La versión de la app.
     - Un icono representativo de envío o correo electrónico.
     */





    BottomAppBar(
        // modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        Row {
            Text(
                text = "Artlery © 2025. Todos los derechos reservados.",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
    }
}
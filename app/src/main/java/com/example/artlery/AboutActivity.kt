package com.example.artlery

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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

    CenterAlignedTopAppBar(
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

    val descripcionApp = """
        Artlery es la primera red social dedicada a las artes plásticas.
        Aprende más sobre tus obras favoritas, colabora en la difusión del conocimiento y construye comunidad.
        Un oasis cultural por y para amantes de lo creativo.
        """

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
                    painter = painterResource(id = R.drawable.artlery_logo2), // PONER LOGO
                    contentDescription = "Logo Artlery"
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .padding(horizontal = 15.dp),
                    text = descripcionApp.trimIndent(),
                    textAlign = TextAlign.Justify,
                    color = Color.White,
                    fontSize = 19.sp,
                    lineHeight = 40.sp,
                )
        }
        this.item {

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("destinatario@destinatario.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "Información sobre la Aplicación")
                            putExtra(Intent.EXTRA_TEXT, descripcionApp.trimIndent())
                        }
                        context.startActivity(Intent.createChooser(intent, "Enviar email: "))
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(40.dp),
                    imageVector = Icons.Filled.Email,
                    tint = Color.White,
                    contentDescription = "Icono de envío",
                )
            }
        }
    }

}

@Composable
fun BottomBar() {
// AÑADIR LOGOS Y COSAS QUE FALTAN  y      - La versión de la app.

    BottomAppBar(
        // modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        Row {
            Text(
                text = "Artlery © 2025. Todos los derechos reservados.",
                color = Color.Black,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
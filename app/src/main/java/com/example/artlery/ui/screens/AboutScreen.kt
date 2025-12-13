package com.example.artlery.ui.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artlery.R
import com.example.artlery.ui.theme.ArtleryTheme


class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AboutScreen()
        }
    }
}

@Composable
fun AboutScreen() {
    ArtleryTheme {
        Scaffold(
            topBar = {
                ToolBar()
            },
            content = { innerPadding ->
                Content(Modifier.padding(innerPadding))
            },
            bottomBar = {
                BottomBar()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    AboutScreen()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Sobre Artlery",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.DarkGray,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun Content(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val version = packageInfo.versionName
    val descripcion = stringResource(id = R.string.descripcionApp)
    val descripcionApp = String.format(descripcion, version)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.beige_logo)
            )
            .padding(
                top = 10.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 1.dp
            )
            .border(
                width = 3.dp,
                color = Color.DarkGray,
                shape = RectangleShape
            )
            .padding(
                start = 8.dp,
                top = 30.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .background(
                color = colorResource(id = R.color.beige_logo),
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
                color = Color.DarkGray,
                fontSize = 17.sp,
                lineHeight = 40.sp,
                fontFamily = FontFamily.Serif
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
                    tint = Color.DarkGray,
                    contentDescription = "Icono de envío",
                )
            }
        }
    }
}

@Composable
fun BottomBar() {

    BottomAppBar(
        containerColor = colorResource(id = R.color.beige_logo),
        contentColor = Color.White,
    ) {
        Row {
            Text(
                text = "Artlery © 2025. Todos los derechos reservados.",
                color = Color.DarkGray,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif
            )
        }
    }
}
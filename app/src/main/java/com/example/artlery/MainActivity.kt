package com.example.artlery

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artlery.model.Datasource
import com.example.artlery.model.Piece
import com.example.artlery.ui.screens.FavListCompactScreen
import com.example.artlery.ui.screens.FavListMedExpScreen
import com.example.artlery.ui.screens.PieceDetailCompactScreen
import com.example.artlery.ui.screens.PieceListCompactScreen
import com.example.artlery.ui.screens.PieceListMedExpScreen
import com.example.artlery.ui.screens.ProfileCompactScreen
import com.example.artlery.ui.screens.DetailFavScreen
import com.example.artlery.ui.theme.ArtleryComposeTheme
import com.example.artlery.utils.getWindowSizeClass
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ArtleryComposeTheme {
                ArtleryApp()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar {
        val items = listOf(
            BottomNavItem("piece_list", Icons.AutoMirrored.Filled.List, "Obras"),
            BottomNavItem("fav_list", Icons.Default.Favorite, "Favoritos"),
            BottomNavItem("profile", Icons.Default.Person, "Perfil"),
            BottomNavItem("about", Icons.Default.QuestionMark, "Acerca de")
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


@SuppressLint("ContextCastToActivity")
@Composable
fun ArtleryApp() {
    val pieces = remember {
        Datasource.getListXTimes(5).toMutableStateList()
    }
    val windowSize =
        getWindowSizeClass(LocalContext.current as Activity)
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsState(initial = "")

    val onFavToggle: (Piece) -> Unit = { pieceToToggle ->
        pieceToToggle.isFav = !pieceToToggle.isFav
    }

    val onRemoveFromFav: (Piece) -> Unit = { pieceToRemove ->
        pieceToRemove.isFav = false
    }

    ArtleryComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {},
            bottomBar = {
                BottomNavigationBar(navController, currentRoute)
            },
            floatingActionButton = {}
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "piece_list",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("piece_list") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            PieceListCompactScreen(
                                pieces = pieces,
                                navController = navController,
                                onFavToggle = onFavToggle,
                                Modifier.padding(8.dp)
                            )
                        }

                        else -> {
                            PieceListMedExpScreen(
                                pieces = pieces,
                                navController = navController,
                                onFavToggle = onFavToggle,
                                Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("fav_list") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            FavListCompactScreen(
                                pieces = pieces,
                                navController = navController,
                                onRemoveFromFav = onRemoveFromFav,
                                Modifier.padding(8.dp)
                            )
                        }

                        else -> {
                            FavListMedExpScreen(
                                pieces = pieces,
                                navController = navController,
                                onRemoveFromFav = onRemoveFromFav,
                                Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("profile") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            ProfileCompactScreen(
                                Modifier.padding(8.dp)
                            )
                        }

                        else -> {
                            ProfileCompactScreen(
                                Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("piece_detail/{piece_name}") { it ->
                    val pieceName = it.arguments?.getString("piece_name")
                    val detailScreenToggle: (String) -> Unit = { name ->
                        val pieceToToggle = pieces.find { p -> p.name == name }
                        pieceToToggle?.let { onFavToggle(it) }
                    }
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            PieceDetailCompactScreen(
                                pieceName = pieceName,
                                navController = navController,
                                onFavToggle = detailScreenToggle,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        else -> {
                            PieceDetailCompactScreen(
                                pieceName,
                                navController = navController,
                                onFavToggle = detailScreenToggle,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("detail_fav/{piece_name}") { backStackEntry ->
                    val pieceName = backStackEntry.arguments?.getString("piece_name")

                    val detailScreenToggle: (String) -> Unit = { name ->
                        val pieceToToggle = pieces.find { p -> p.name == name }
                        pieceToToggle?.let { onFavToggle(it) }
                    }

                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            DetailFavScreen(
                                pieceName = pieceName,
                                navController = navController,
                                onFavToggle = detailScreenToggle,
                                // modifier = Modifier.padding(8.dp)
                            )
                        }

                        else -> {
                            DetailFavScreen(
                                pieceName = pieceName,
                                navController = navController,
                                onFavToggle = detailScreenToggle,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtleryAppPreview() {
    ArtleryComposeTheme {
        ArtleryApp()
    }
}

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)
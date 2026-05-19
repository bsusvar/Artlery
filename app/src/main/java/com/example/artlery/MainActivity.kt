package com.example.artlery

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artlery.data.local.ArtDatabase
import com.example.artlery.data.local.ArtworkEntity
import com.example.artlery.data.repository.UserSettingsRepository
import com.example.artlery.model.Datasource
import com.example.artlery.model.Piece
import com.example.artlery.ui.screens.AboutScreen
import com.example.artlery.ui.screens.DetailFavScreen
import com.example.artlery.ui.screens.FavListCompactScreen
import com.example.artlery.ui.screens.FavListMedExpScreen
import com.example.artlery.ui.screens.PieceDetailCompactScreen
import com.example.artlery.ui.screens.PieceListCompactScreen
import com.example.artlery.ui.screens.PieceListMedExpScreen
import com.example.artlery.ui.screens.ProfileCompactScreen
import com.example.artlery.ui.theme.ArtleryComposeTheme
import com.example.artlery.utils.getWindowSizeClass
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        var shouldKeepSplashOn = true

        splashScreen.setKeepOnScreenCondition { shouldKeepSplashOn }

        Handler(Looper.getMainLooper()).postDelayed({
            shouldKeepSplashOn = false
        }, 1500)

        setContent {
            ArtleryApp()
        }
    }
}

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar {
        val items: List<BottomNavItem> = listOf(
            BottomNavItem(
                "piece_list",
                Icons.AutoMirrored.Filled.List,
                stringResource(R.string.menu_artworks_list)
            ),
            BottomNavItem(
                "fav_list",
                Icons.Default.Favorite,
                stringResource(R.string.menu_favorites_list)
            ),
            BottomNavItem("profile", Icons.Default.Person, stringResource(R.string.menu_profile)),
            BottomNavItem("about", Icons.Default.QuestionMark, stringResource(R.string.menu_about))
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

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ContextCastToActivity", "FlowOperatorInvokedInComposition")
@Composable
fun ArtleryApp() {
    val context = LocalContext.current
    val repository = remember { UserSettingsRepository(context.applicationContext) }
    val scope = rememberCoroutineScope()
    val dao = remember { ArtDatabase.getDatabase(context.applicationContext).artDao() }

    val appTheme by repository.appThemeFlow.collectAsState(initial = "Sistema")

    val useDarkTheme = when (appTheme) {
        "Claro" -> false
        "Oscuro" -> true
        else -> isSystemInDarkTheme()
    }

    val userName by repository.userNameFlow.collectAsState(initial = "")
    val isLogged = userName.isNotBlank()

    val pieces = remember {
        Datasource.getListXTimes(5).toMutableStateList()
    }

    val windowSize = getWindowSizeClass(context as Activity)
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsState(initial = "")

    val showBottomBar = isLogged

    val onFavToggle: (Piece) -> Unit = { pieceToToggle ->
        val toggledState = !pieceToToggle.isFav
        scope.launch {
            val favoriteEntity = dao.getFavoriteById(pieceToToggle.id)

            if (favoriteEntity != null && !toggledState) {
                dao.deleteFavorite(favoriteEntity)
            } else if (favoriteEntity == null && toggledState) {
                val newEntity = ArtworkEntity(
                    id = pieceToToggle.id,
                    title = pieceToToggle.name,
                    artist = pieceToToggle.author,
                    date = pieceToToggle.year,
                    style = pieceToToggle.style,
                    location = pieceToToggle.location,
                    description = pieceToToggle.description,
                    imageUrl = pieceToToggle.photo
                )
                dao.insertFavorite(newEntity)
            }
        }
    }

    val onRemoveFromFav: (Piece) -> Unit = { pieceToRemove ->
        scope.launch {
            val favoriteEntity = dao.getFavoriteById(pieceToRemove.id)
            if (favoriteEntity != null) {
                dao.deleteFavorite(favoriteEntity)
            }
        }
    }

    ArtleryComposeTheme(darkTheme = useDarkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {},
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(navController, currentRoute)
                }
            },
            floatingActionButton = {}
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "profile",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("piece_list") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            PieceListCompactScreen(
                                navController = navController,
                                onFavToggle = onFavToggle,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        else -> {
                            PieceListMedExpScreen(
                                navController = navController,
                                onFavToggle = onFavToggle,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("fav_list") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            FavListCompactScreen(
                                navController = navController,
                                onRemoveFromFav = onRemoveFromFav,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        else -> {
                            FavListMedExpScreen(
                                navController = navController,
                                onRemoveFromFav = onRemoveFromFav,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("profile") {
                    when (windowSize) {
                        WindowWidthSizeClass.Compact -> {
                            ProfileCompactScreen(
                                navController = navController
                            )
                        }
                        else -> {
                            ProfileCompactScreen(
                                navController = navController,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                composable("piece_detail/{piece_id}") { backStackEntry ->
                    val pieceId = backStackEntry.arguments?.getString("piece_id")?.toIntOrNull()

                    PieceDetailCompactScreen(
                        artworkId = pieceId,
                        navController = navController,
                        userName = userName,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                composable("detail_fav/{piece_id}") { backStackEntry ->
                    val pieceId = backStackEntry.arguments?.getString("piece_id")?.toIntOrNull()

                    PieceDetailCompactScreen(
                        artworkId = pieceId,
                        navController = navController,
                        userName = userName,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                composable("about") {
                    AboutScreen(Modifier.padding(8.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ArtleryAppPreview() {
    ArtleryComposeTheme {
        ArtleryApp()
    }
}
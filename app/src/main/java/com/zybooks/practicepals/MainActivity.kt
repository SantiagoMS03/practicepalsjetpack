package com.zybooks.practicepals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import com.zybooks.practicepals.ui.home.HomeScreen
import com.zybooks.practicepals.ui.metronome.MetronomeScreen
import com.zybooks.practicepals.ui.pieces.NewPieceScreen
import com.zybooks.practicepals.ui.pieces.PieceListScreen
import com.zybooks.practicepals.ui.pieces.PieceDetailScreen
import com.zybooks.practicepals.ui.stopwatch.StopwatchScreen
import com.zybooks.practicepals.ui.theme.PracticePalsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticePalsTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("metronome") {
            MetronomeScreen()
        }
        composable("pieces") {
            PieceListScreen(
                onAddPieceClick = { navController.navigate("add_piece") },
                onPieceClick = { pieceId ->
                    navController.navigate("pieceDetail/$pieceId")
                }
            )
        }
        composable(
            route = "pieceDetail/{pieceId}",
            arguments = listOf(navArgument("pieceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pieceId = backStackEntry.arguments?.getInt("pieceId") ?: return@composable
            PieceDetailScreen(pieceId = pieceId)
        }
        composable("piece_list") {
        }
        composable("add_piece") {
            NewPieceScreen(onPieceAdded = { navController.popBackStack() }) // Navigate back to list
        }
        composable("stopwatch") {
            StopwatchScreen()
        }
    }
}

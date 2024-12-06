package com.zybooks.practicepals.ui.navigation

import PracticeSessionBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zybooks.practicepals.ui.admin.AdminScreen
import com.zybooks.practicepals.ui.barchart.PracticeBarChartScreen
import com.zybooks.practicepals.ui.home.HomeScreen
import com.zybooks.practicepals.ui.metronome.MetronomeScreen
import com.zybooks.practicepals.ui.pieces.NewPieceScreen
import com.zybooks.practicepals.ui.pieces.PieceDetailScreen
import com.zybooks.practicepals.ui.pieces.PieceListScreen
import com.zybooks.practicepals.ui.stopwatch.StopwatchScreen
import com.zybooks.practicepals.viewmodel.UiStateViewModel

// Define CompositionLocal for NavController
val LocalNavController = staticCompositionLocalOf<NavHostController?> { null }

// Main Navigation Host
@Composable
fun MainNavHost(uiStateViewModel: UiStateViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isPracticeBarVisible by uiStateViewModel.isPracticeBarVisible

    CompositionLocalProvider(LocalNavController provides navController) {
        if (isPracticeBarVisible) {
            PracticeSessionBar()
        }

        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController)
            }
            composable("metronome") {
                uiStateViewModel.hidePracticeBarInScreen()
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
            composable("add_piece") {
                NewPieceScreen(onPieceAdded = { navController.popBackStack() })
            }
            composable("stopwatch") {
                StopwatchScreen()
            }
            composable("practice_bar_chart") { // Add this composable route
                PracticeBarChartScreen()
            }
            composable("admin") {
                AdminScreen()
            }

        }
    }
}

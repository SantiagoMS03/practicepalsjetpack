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
import com.zybooks.practicepals.ui.navigation.MainNavHost
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
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainNavHost() // The navController is managed within MainNavHost
                }
            }
        }
    }
}

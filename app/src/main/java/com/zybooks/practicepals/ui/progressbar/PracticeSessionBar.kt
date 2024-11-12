import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zybooks.practicepals.ui.navigation.LocalNavController
import com.zybooks.practicepals.viewmodel.StopwatchViewModel

@Composable
fun PracticeSessionBar(
    backgroundColor: Color = Color.Blue,
    shape: Shape = RoundedCornerShape(30.dp), // Rounded corners for pill shape
    stopwatchViewModel: StopwatchViewModel = hiltViewModel(),
) {
    val elapsedTime by stopwatchViewModel.elapsedTime.collectAsState()

    val minutes = (elapsedTime / 60000).toInt()
    val seconds = ((elapsedTime % 60000) / 1000).toInt()
    val milliseconds = ((elapsedTime % 1000) / 10).toInt()

    val navController = LocalNavController.current // Access NavController without passing as a parameter
    Log.d("NAV_CONTROLLER", "NavController is null: ${navController == null}")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust padding for floating effect
            .height(56.dp)
            .clip(shape)
            .background(color = backgroundColor)
            .clickable(onClick = {
                navController?.navigate("stopwatch")
                Log.d("TEST", "Tapped!")
            }),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Text(
                text = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds),
                fontSize = 48.sp
            )
        }
    }
}

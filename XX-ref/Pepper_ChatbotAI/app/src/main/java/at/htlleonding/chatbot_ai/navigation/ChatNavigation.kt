package at.htlleonding.chatbot_ai.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.htlleonding.chatbot_ai.model.api.HistoryRequests
import at.htlleonding.chatbot_ai.model.data.HistoryData
import at.htlleonding.chatbot_ai.screens.ChatScreen
import at.htlleonding.chatbot_ai.screens.ContactScreen
import com.aldebaran.qi.sdk.util.FutureUtils.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@Composable
fun ChatNavigation(randomImages: ArrayList<Int>) {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ContactScreen") {

        composable(Routes.ContactScreen.route) {
            ContactScreen(navController = navController, randomImages = randomImages)
        }
        composable(
            Routes.ChatScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ChatScreen(navController = navController, id = id)
        }
    }

}

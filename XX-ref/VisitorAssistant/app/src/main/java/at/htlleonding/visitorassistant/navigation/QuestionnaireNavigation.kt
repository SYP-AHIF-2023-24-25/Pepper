package at.htlleonding.visitorassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.htlleonding.visitorassistant.screens.HomeScreen
import at.htlleonding.visitorassistant.screens.MultipleChoiceScreen
import at.htlleonding.visitorassistant.screens.error_screen.InternetConnectionError
import at.htlleonding.visitorassistant.screens.goodbye_screens.IndecisiveGoodbye
import at.htlleonding.visitorassistant.screens.goodbye_screens.NegativeGoodbye
import at.htlleonding.visitorassistant.screens.goodbye_screens.PositiveGoodbye
import com.aldebaran.qi.sdk.QiContext

@Composable
fun QuestionnaireNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Routes.MultipleChoice.route) {
            MultipleChoiceScreen(navController = navController)
        }

        composable(Routes.PositiveGoodbye.route) { PositiveGoodbye(navController = navController) }
        composable(Routes.IndecisiveGoodbye.route) { IndecisiveGoodbye(navController = navController) }
        composable(Routes.InternetConnectionError.route) { InternetConnectionError(navController = navController) }

        composable(Routes.NegativeGoodbye.route) {
            NegativeGoodbye(navController = navController)
        }
    }

}
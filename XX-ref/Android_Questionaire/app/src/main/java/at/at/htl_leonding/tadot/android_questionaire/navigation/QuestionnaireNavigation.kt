package at.at.htl_leonding.tadot.android_questionaire.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.htlleonding.visitorassistant.screens.HomeScreen
import at.at.htl_leonding.tadot.android_questionaire.screens.MultipleChoiceScreen
import at.at.htl_leonding.tadot.android_questionaire.screens.goodbye_screens.NegativeGoodbye
import at.at.htl_leonding.tadot.android_questionaire.screens.goodbye_screens.PositiveGoodbye
import at.htlleonding.visitorassistant.screens.HomeScreen

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
        composable(Routes.NegativeGoodbye.route) {
            NegativeGoodbye(navController = navController)
        }
    }

}
package at.htlleonding.visitorassistant.navigation

sealed class Routes(val route: String){
    object HomeScreen: Routes("HomeScreen")
    object MultipleChoice: Routes("MultipleChoice")
    object PositiveGoodbye: Routes("PositiveGoodbye")
    object IndecisiveGoodbye: Routes("IndecisiveGoodbye")
    object NegativeGoodbye: Routes("NegativeGoodbye")
    object InternetConnectionError: Routes("InternetConnectionError")
}

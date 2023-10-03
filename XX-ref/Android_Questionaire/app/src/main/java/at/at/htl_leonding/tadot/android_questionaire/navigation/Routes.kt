package at.at.htl_leonding.tadot.android_questionaire.navigation

sealed class Routes(val route: String){
    object HomeScreen: Routes("HomeScreen")
    object MultipleChoice: Routes("MultipleChoice")
    object PositiveGoodbye: Routes("PositiveGoodbye")
    object NegativeGoodbye: Routes("NegativeGoodbye")
}

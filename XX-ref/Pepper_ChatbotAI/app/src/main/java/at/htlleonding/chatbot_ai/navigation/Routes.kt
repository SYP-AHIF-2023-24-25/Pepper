package at.htlleonding.chatbot_ai.navigation

sealed class Routes(val route: String){
    object ChatScreen: Routes ("ChatScreen/{id}")
    object ContactScreen: Routes ("ContactScreen")
}

package at.htlleonding.chatbot_ai.model.data

import at.htlleonding.chatbot_ai.screens.Message

data class HistoryData(
    val id : Int,
    val messages: ArrayList<Message>,
    val historyName: String
)

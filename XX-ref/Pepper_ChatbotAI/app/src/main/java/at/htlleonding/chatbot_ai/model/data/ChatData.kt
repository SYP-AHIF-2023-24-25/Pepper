package at.htlleonding.chatbot_ai.model.data

import at.htlleonding.chatbot_ai.screens.Message

data class ChatData(
    val newMessage : Message,
    val historyName: String
)
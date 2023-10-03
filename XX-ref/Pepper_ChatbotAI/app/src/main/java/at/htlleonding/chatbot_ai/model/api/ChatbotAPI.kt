package at.htlleonding.chatbot_ai.model.api

import at.htlleonding.chatbot_ai.model.data.ChatData
import at.htlleonding.chatbot_ai.model.data.ChatResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatbotAPI {
    @POST("query/")
    fun postQuery(@Body chatData: ChatData): Call<ChatResponse?>?

}
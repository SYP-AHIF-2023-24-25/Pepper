package at.htlleonding.chatbot_ai.model.api

import at.htlleonding.chatbot_ai.model.data.ChatData
import at.htlleonding.chatbot_ai.model.data.ChatResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatbotRequests {
    companion object {
        fun postQuery(chatData: ChatData): Call<ChatResponse?>? {
            val baseUrl = "http://81.10.232.88:27015/"

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val chatbotAPI = retrofit.create(ChatbotAPI::class.java)
            val call = chatbotAPI.postQuery(chatData)

            return call
        }
    }
}

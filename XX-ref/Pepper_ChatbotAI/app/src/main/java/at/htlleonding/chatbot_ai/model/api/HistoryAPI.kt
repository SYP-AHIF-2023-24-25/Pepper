package at.htlleonding.chatbot_ai.model.api

import at.htlleonding.chatbot_ai.model.data.HistoryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryAPI {

    @GET("histories/")
    fun getHistory(): Call<ArrayList<HistoryData>>

    @GET("histories/{id}")
    fun getHistoryById(@Path("id") id: Int): Call<HistoryData>

}
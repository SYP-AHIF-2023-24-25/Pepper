package at.htlleonding.chatbot_ai.model.api

import at.htlleonding.chatbot_ai.model.data.HistoryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HistoryRequests {

    companion object {
        fun getHistory(): Call<ArrayList<HistoryData>> {
            val baseUrl = "http://81.10.232.88:27015/"

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val historyAPI = retrofit.create(HistoryAPI::class.java)

            return historyAPI.getHistory()
        }


        suspend fun getHistoryById(id: Int): HistoryData {
            val baseUrl = "http://81.10.232.88:27015/"


            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val historyAPI = retrofit.create(HistoryAPI::class.java)

            return withContext(Dispatchers.IO) {
                val response = historyAPI.getHistoryById(id).execute()
                if (response.isSuccessful) {
                    response.body() ?: throw IllegalStateException("Empty response body")
                } else {
                    throw HttpException(response)
                }
            }
        }


    }
}
package at.htlleonding.visitorassistant.model.api

import android.content.Context
import android.util.Log
import at.htlleonding.visitorassistant.model.data.QuestionData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TadeotRequests {
    companion object {
        fun postAnswer(qData: QuestionData): Call<QuestionData?>? {

            //val baseUrl = "https://vm64.htl-leonding.ac.at/tadeot-backend/"
            val baseUrl = "https://tadeot.htl-leonding.ac.at/tadeot-backend-v23/api/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val tadeotAPI = retrofit.create(TadeotAPI::class.java)
            val call: Call<QuestionData?>? = tadeotAPI.postAnswer(qData)

            Log.d("HSP", call.toString());

            return call
        }
    }
}
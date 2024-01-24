package at.htlleonding.visitorassistant.model.api

import at.htlleonding.visitorassistant.model.data.QuestionData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TadeotAPI {
    @POST("answers")
    fun postAnswer(@Body questionData: QuestionData?): Call<QuestionData?>?

}
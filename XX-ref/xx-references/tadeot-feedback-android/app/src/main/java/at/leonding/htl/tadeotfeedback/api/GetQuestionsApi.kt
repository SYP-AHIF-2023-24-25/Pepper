package at.leonding.htl.tadeotfeedback.api
import at.leonding.htl.tadeotfeedback.entities.Answer
import at.leonding.htl.tadeotfeedback.entities.Question
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*


interface GetQuestionsApi {

    @GET("questions")
    fun getQuestions() : Observable<List<Question>>

    @POST("answers/postanswer")
    @FormUrlEncoded
    fun postAnswer(@Field("questionNumber") questionNumber: Int,
                 @Field("rating") rating: Int,
                 @Field("detail") detail: String): Flowable<Any>

    @Headers("Content-type: application/json")
    @POST("answers")
    fun addAnswer(@Body answer: Answer) : Observable<Answer>

}

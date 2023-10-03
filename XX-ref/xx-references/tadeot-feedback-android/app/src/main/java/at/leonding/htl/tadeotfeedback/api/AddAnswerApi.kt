package at.leonding.htl.tadeotfeedback.api
import at.leonding.htl.tadeotfeedback.entities.Answer
import at.leonding.htl.tadeotfeedback.entities.Question
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*


interface AddAnswerApi {

    @Headers("Content-type: application/json")
    @POST("answers")
    fun addAnswer(@Body answer: Answer) : Observable<Answer>

}

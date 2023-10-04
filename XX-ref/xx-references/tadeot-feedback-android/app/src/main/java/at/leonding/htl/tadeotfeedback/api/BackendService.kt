package at.leonding.htl.tadeotfeedback.api

import android.util.Log
import at.leonding.htl.tadeotfeedback.MainActivity
import at.leonding.htl.tadeotfeedback.Preferences
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BackendService {
    private val LOG_TAG = BackendService::class.java.simpleName

    //private var _myCompositeDisposable: CompositeDisposable? = null

    //private val BASE_URL = "http://10.0.0.31:5000/api/"

    fun getBaseUrl() : String{
        Log.d(LOG_TAG, "getBaseUrl(): ${Preferences.ipAdress}:${Preferences.port}/api/")
        return "http://${Preferences.ipAdress}:${Preferences.port}/api/"
    }
    private val questionsApi = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GetQuestionsApi::class.java)

    private val addAnswerApi = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AddAnswerApi::class.java)

    fun getQuestionsApi(): GetQuestionsApi {
        Log.d(LOG_TAG, "getQuestionsApi() called!")
        return questionsApi
    }

    fun getAddAnswerApi(): AddAnswerApi {
        Log.d(LOG_TAG, "getAddAnswerApi() called!")
        return addAnswerApi
    }
}
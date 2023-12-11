/*
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

    private val BASE_URL = "https://vm64.htl-leonding.ac.at/tadeot-backend/api/";

   /* fun getBaseUrl() : String{
        Log.d(LOG_TAG, "getBaseUrl(): ${Preferences.ipAdress}:${Preferences.port}/api/")
        return "http://${Preferences.ipAdress}:${Preferences.port}/api/"
    }https://vm64.htl-leonding.ac.at/tadeot-backend/api/questions*/
    private val questionsApi = Retrofit.Builder()
            .baseUrl(BASE_URL + "questions/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GetQuestionsApi::class.java)

    private val addAnswerApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AddAnswerApi::class.java)

    fun getQuestionsApi(): GetQuestionsApi {
        Log.d(LOG_TAG, "${BASE_URL + "questions/"}")

        Log.d(LOG_TAG, "${questionsApi}");
        return questionsApi
    }

    fun getAddAnswerApi(): AddAnswerApi {
        Log.d(LOG_TAG, "getAddAnswerApi() called!")
        return addAnswerApi
    }
}*/
package at.leonding.htl.tadeotfeedback.api
import at.leonding.htl.tadeotfeedback.api.GetQuestionsApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object BackendService {
    private const val BASE_URL = "https://vm64.htl-leonding.ac.at/tadeot-backend/api/"

    // Initialisiert das Retrofit-Objekt für die Fragen-API
    private val questionsApi: GetQuestionsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GetQuestionsApi::class.java)

    private val addAnswerApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AddAnswerApi::class.java)

    // Gibt die API-Schnittstelle für die Fragen zurück
    fun getQuestionsApi(): GetQuestionsApi = questionsApi

    fun AddAnswerApi(): AddAnswerApi = addAnswerApi;
}
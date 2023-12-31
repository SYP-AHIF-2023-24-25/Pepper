package at.leonding.htl.tadeotfeedback


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import at.leonding.htl.tadeotfeedback.api.BackendService
import at.leonding.htl.tadeotfeedback.api.GetQuestionsApi
import at.leonding.htl.tadeotfeedback.entities.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_settings.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.util.*
import kotlin.concurrent.thread
import io.reactivex.Observable
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private val LOG_TAG = MainActivity::class.java.simpleName

    private var _myCompositeDisposable: CompositeDisposable? = null
    private lateinit var _currentQuestion : Question;

    val backendService = BackendService
    val questionsApi = backendService.getQuestionsApi()

     var allQustions: List<Question> = emptyList();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Preferences.setContext(this)

        val compositeDisposable = CompositeDisposable()

        val observable = questionsApi.getQuestions()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { questions ->
                    // Hier kannst du die Liste von Fragen weiterverarbeiten
                    questions.forEach {
                        println("Number: ${it.number}, Title: ${it.title}")
                    }
                    Log.d(LOG_TAG, "${questions}");
                    allQustions = questions;
                },
                { error ->
                    println("Fehler beim Laden der Fragen: ${error.message}")
                    allQustions = emptyList();
                }
            )

        Thread.sleep(1000);


        if(!allQustions.isEmpty()){
            compositeDisposable.add(observable)
            Thread.sleep(5000);
            _currentQuestion = allQustions[0];

            val titleTextView: TextView = findViewById(R.id.txt_title)

            // Setze den Text des TextView auf den Titel der aktuellen Frage
            titleTextView.text = _currentQuestion.title
            txt_sub_title.text = _currentQuestion.subTitle

            Repository.setQuestions(allQustions);


            Log.d(LOG_TAG, "${allQustions}")

            getSettingsFromDialog()
            Log.d(LOG_TAG, "onCreate() nach getSettings()")
        }else{
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Keine Internetverbindung!")
            builder.setPositiveButton("Anwendung neu starten") { _, _ ->

                restartApplication();
                exitProcess(0);
            }
            val dialog: AlertDialog = builder.create()
            dialog.show();

        }


    }

    private fun restartApplication() {
        //Restart Application
        val intent = intent
        finish()
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_dialogopen -> {
            getSettingsFromDialog()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    private fun getSettingsFromDialog() {
        Preferences.setContext(this)
        //val dialogSettingsView = LayoutInflater.from(this).inflate(R.layout.dialog_settings, null)
        //dialogSettingsView.et_ip_address.setText(Preferences.ipAdress)

        //dialogSettingsView.et_port.setText(Preferences.port.toString())
/*
        val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogSettingsView)
                .setTitle("Settings")
        val settingsDialog = dialogBuilder.show()

        //When "Save Settings" clicked
        dialogSettingsView.btn_save_preferences.setOnClickListener {
            Preferences.save(dialogSettingsView.et_ip_address.text.toString(), Integer.parseInt(dialogSettingsView.et_port.text.toString()))
            settingsDialog.dismiss()
            loadQuestions()
        }

        //When "Test Connection" clicked
        dialogSettingsView.btn_testConnection.setOnClickListener {
            //Testet die Connection
            val ip = dialogSettingsView.et_ip_address
            val port = dialogSettingsView.et_port
            var result = false
            val t = thread {
                try {
                    loadQuestions()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            while (t.isAlive) {
            }

            if (result)
                Toast.makeText(this, "Connected successfully", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Connection failed", Toast.LENGTH_LONG).show()
        }*/
    }

    private val BASE_URL = "https://vm64.htl-leonding.ac.at/tadeot-backend/api/"

    private fun loadQuestions() {
        val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL + "questions")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GetQuestionsApi::class.java)

        val observable = BackendService.getQuestionsApi().getQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            handleResponse(it)
                        },
                        {
                            Log.e(LOG_TAG, "loadQuestions() failed ${it.message}")
                            Toast.makeText(applicationContext, "Loading questions failed!", Toast.LENGTH_LONG).show();
                        })
        _myCompositeDisposable?.add(observable)


    }


    private fun handleResponse(questions: List<Question>) {
        Log.e(LOG_TAG, "handleResponse() " + questions.size.toString())
        Repository.setQuestions(questions)
        startFeedback()
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    override fun onDestroy() {
        super.onDestroy()
        _myCompositeDisposable?.clear()
    }

    fun startFeedback() {
        Repository.clearAnswers()
        reload()
    }

    fun onLeftClick(btn: View) {
        Repository.gotoPreviusQuestion();
        reload()
    }

    fun onRightClick(btn: View) {
        if (Repository.gotoNextQuestion())
            newFeedback()
        reload()
    }

    fun onRatingClick(btn: View) {
        val rating = Integer.parseInt(btn.tag.toString())
        Repository.setRatingForCurrentQuestion(rating)
        if (rating >= 4) {
            val detailsActivity = Intent(getApplicationContext(), DetailsActivity::class.java)
            startActivity(detailsActivity);
        } else {
            if (Repository.gotoNextQuestion())
                newFeedback()
            reload()
        }
    }

    fun reload() {
        if (Repository.getQuestionsCount() === 0) {
            return
        }
        val currentQuestion = Repository.getCurrentQuestion()
        if (currentQuestion.number === 1) {
            left_button.setVisibility(View.INVISIBLE)
        } else {
            left_button.setVisibility(View.VISIBLE)
        }
        if (currentQuestion.number < Repository.getQuestionsCount()) {
            right_button.setVisibility(View.VISIBLE)
        } else {
            right_button.setVisibility(View.INVISIBLE)
        }
        if (currentQuestion.number === 0) {
            txt_title.setText(currentQuestion.title)
            txt_sub_title.text = currentQuestion.subTitle
            txt_question_counter.text = "${currentQuestion.number}/${Repository.getQuestionsCount()}"
        } else {  // Auswahlfrage
            val detailsActivity = Intent(getApplicationContext(), DetailsActivity::class.java)
            startActivity(detailsActivity);
        }
    }

    fun newFeedback() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Danke für Ihr Feedback!")
        builder.setPositiveButton("Weiter") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}

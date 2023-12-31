package at.leonding.htl.tadeotfeedback

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import at.leonding.htl.tadeotfeedback.entities.Answer
import at.leonding.htl.tadeotfeedback.entities.Question

import kotlinx.android.synthetic.main.activity_details.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private val LOG_TAG = MainActivity::class.java.simpleName

    private lateinit var _buttonsList : List<Button>

    private lateinit var gridLayout: GridLayout;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        this.gridLayout = findViewById<GridLayout>(R.id.buttonsContainer)




        _buttonsList = listOf<Button>(btn_1,btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12) as MutableList<Button>
        if (Repository.getCurrentQuestion().number === 1){
            textview_details_Title.text = Repository.getCurrentQuestion().title
        }
        else{
            textview_details_Title.text = Repository.getCurrentQuestion().detailsQuestion
        }
        showButtons("option")
    }


    private fun showButtons(options: String) {
        hideAllButtons()
        val details: List<String>;

        if(options === "details")
        {
            details = Repository.getCurrentDetails();
        }
        else{
            details = Repository.getCurrentOptoins();
        }


        this.gridLayout.removeAllViews();

        for ((index, detail) in details.withIndex()) {
            if (index < _buttonsList.size) {
                val button = Button(this) // Create a new Button dynamically
                button.text = detail
                button.setOnClickListener { onDetailClick(it) }

                button.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light))


                val params = GridLayout.LayoutParams()
                params.width = GridLayout.LayoutParams.WRAP_CONTENT
                params.height = GridLayout.LayoutParams.WRAP_CONTENT
                params.setMargins(8, 8, 8, 8)  // Optional: Set the margins between buttons

                button.layoutParams = params

                this.gridLayout.addView(button)



            }
        }
    }
    fun onLeftClick(btn : View){
        Repository.gotoPreviusQuestion();
        finish()
    }

    fun onRightClick(btn : View){
        Repository.gotoNextQuestion();
        finish()
    }

    fun showDetailsQuestion(question: Question)
    {
        textview_details_Title.text = question.detailsQuestion
        showButtons("details");
    }

    var answer: String = "";
    var detailAnswer: String = "";


    fun onDetailClick(view : View){

        val btn = view as Button

        if(this.answer === ""){
            this.answer = btn.text.toString();
        }
        else{
            this.detailAnswer = btn.text.toString();
        }

        Log.d(LOG_TAG, "Button klicked Value: ${btn.text.toString()}")



        Log.d(LOG_TAG, " ${Repository.getCurrentQuestion().detailsIf[0].trim().toString()}")
        if(btn.text.toString().equals(Repository.getCurrentQuestion().detailsIf[0].trim().toString()) ){
            showDetailsQuestion( Repository.getCurrentQuestion());

            Log.d(LOG_TAG, "Show details")

        }
        else
        {
            try{
                //this.answer = btn.text.toString();

                // Aktuellen Zeitstempel erstellen
                val currentTimestamp = Date()

                // Einen Stunde hinzufügen
                val calendar = Calendar.getInstance()
                calendar.time = currentTimestamp
                calendar.add(Calendar.HOUR_OF_DAY, 1)

                // Den neuen Zeitstempel erhalten
                val newTimestamp = calendar.time

                val question = Repository.getCurrentQuestion();
                val answer: Answer = Answer(
                    timestamp =  currentTimestamp.toString(),
                    questionText = question.title,
                    questionId = question.id,
                    questionNumber = question.number,
                    answer = this.answer,
                    detailsQuestion = question.detailsQuestion,
                    detailText = this.detailAnswer
                );


                Repository.postAnswer(answer);

                var lastQuestion = Repository.gotoNextQuestion();
                if (lastQuestion)
                    newFeedback()

                finish()


            }catch(e: java.lang.Exception){
                Log.d(LOG_TAG, "Feedback not saved")
                val builder = AlertDialog.Builder(this@DetailsActivity)
                builder.setTitle("Feedback konnte nicht gespeichert werden." +
                        "Keine Netzwerkverbindung");
                builder.setPositiveButton("Feedback neustarten") { _, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }



        }


    }

    private fun hideAllButtons() {

        for (btn : Button in _buttonsList) {
            btn.visibility = View.INVISIBLE
        }

    }

    fun newFeedback(){
        val builder = AlertDialog.Builder(this@DetailsActivity)
        builder.setTitle("Danke für Ihr Feedback!")
        builder.setPositiveButton("Weiter"){_,_ ->finish()}
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}

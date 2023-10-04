package at.leonding.htl.tadeotfeedback

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.Button

import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private val LOG_TAG = MainActivity::class.java.simpleName

    private lateinit var _buttonsList : List<Button>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        _buttonsList = listOf<Button>(btn_1,btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12)
        if (Repository.getCurrentQuestion().questionType == 1){
            textview_details_Title.text = Repository.getCurrentQuestion().titleGerman
        }
        else{
            textview_details_Title.text = Repository.getCurrentQuestion().bonusQuestion
        }
        showButtons()
    }

    private fun showButtons() {
        hideAllButtons()
        val details = Repository.getCurrentDetails()
        var i = 0
        for (detail in details){
            var button = _buttonsList[i]
            button.text = detail
            button.visibility = View.VISIBLE
            if (i+1 < _buttonsList.size){
                i++
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

    fun onDetailClick(view : View){
        val btn = view as Button
        Repository.setDetailForCurrentQuestion(btn.text.toString())
        var lastQuestion = Repository.gotoNextQuestion()
        if (lastQuestion)
            newFeedback()
        else
            finish()
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

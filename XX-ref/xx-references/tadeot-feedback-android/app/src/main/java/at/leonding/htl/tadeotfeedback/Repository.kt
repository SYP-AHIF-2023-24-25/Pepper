package at.leonding.htl.tadeotfeedback

import android.util.Log
import android.widget.Toast
import at.leonding.htl.tadeotfeedback.api.BackendService
import at.leonding.htl.tadeotfeedback.entities.Answer
import at.leonding.htl.tadeotfeedback.entities.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


object Repository {
    private val LOG_TAG = Repository::class.java.simpleName

    private var _questions: ArrayList<Question> = ArrayList()

    private var _currentQuestionNumber = 0
    private var _answers = HashMap<Question, Answer>()

    fun setQuestions(questions: List<Question>) {
        _questions = ArrayList<Question>(questions)

    }

    fun clearAnswers() {
        _answers.clear()
        _currentQuestionNumber = 0
    }

    fun getQuestionsCount(): Int {
        return _questions.size;
    }

    fun gotoPreviusQuestion() {
        if (_currentQuestionNumber > 0) _currentQuestionNumber--
    }

    fun gotoNextQuestion(): Boolean {
        _currentQuestionNumber++
        if (_currentQuestionNumber >= _questions.size) {
            for (answer in _answers.values) {
                addAnswer(answer)
                postAnswer(answer)
                //Log.d(LOG_TAG, "postAnswers questionNumber: ${answer.questionNumber}, Rating: ${answer.rating}, Detail: ${answer.detailText}")
            }
            clearAnswers()
            return true
        }
        return false
    }

     fun postAnswer(answer: Answer) {
        Log.d(LOG_TAG, "postAnswer() ${answer}")
        BackendService.AddAnswerApi()
            .addAnswer(
                Answer(
                    timestamp = answer.timestamp,
                    questionText = answer.questionText,
                    questionId = answer.questionId,
                    questionNumber = answer.questionNumber,
                    answer = answer.answer,
                    detailsQuestion = answer.detailsQuestion,
                    detailText = answer.detailText
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    //Toast.makeText(null, response.toString(), Toast.LENGTH_LONG).show()
                    Log.e(LOG_TAG, "postAnswer() OK, Response from Server: $response")
                },
                { error ->
                    //Toast.makeText(null, error.message, Toast.LENGTH_LONG).show()
                    Log.e(LOG_TAG, "postAnswer() ${error.message}")
                }
            )
    }

    private fun addAnswer(answer: Answer) {
        Log.d(LOG_TAG, "addAnswer() ${answer}")
        /*
        BackendService.getAddAnswerApi().addAnswer(answer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.e(LOG_TAG, "addAnswer() OK, Response from Server: ${it}")
                        },
                        {
                            Log.e(LOG_TAG, "addAnswer() ${answer} failed! ${it.message}")
                        })*/
    }

    fun getCurrentQuestion(): Question {
        return _questions[_currentQuestionNumber]
    }

    fun setRatingForCurrentQuestion(rating: Int) {
        /*
        val question = _questions[_currentQuestionNumber]
        if (_answers.containsKey(question)) {
            _answers[question]?.rating = rating
        } else {
            _answers[question] = Answer(question.number, rating)
        }
        Log.d(LOG_TAG, "setRatingForCurrentQuestion ${question.number}, ${question.title} ${question.subTitle}: $rating")*/
    }

    fun getCurrentOptions(): List<String> {
        val question = _questions[_currentQuestionNumber]
        return question.options
    }

    fun getCurrentDetails(): List<String> {
        val question = _questions[_currentQuestionNumber]


        return question.details
    }

    fun getCurrentOptoins(): List<String> {
        val question = _questions[_currentQuestionNumber]


        return question.options
    }

    fun setDetailForCurrentQuestion(detailText: String) {
        /*

        val question = _questions[_currentQuestionNumber]
        if (_answers.containsKey(question)) {
            _answers[question]?.detailText = detailText
        } else {
            _answers[question] = Answer(question.number, detailText = detailText)
        }
        Log.d(LOG_TAG, "setDetailForCurrentQuestion ${question.title}: $detailText")*/
    }

    /* fun setQuestions() {
         _questions.clear()
         val strings = arrayOf("" +
                 "1;0;Wie hat dir der Tag der offenen Tür insgesamt gefallen?;Did you generally enjoy the open house at HTL Leonding?;;;Unfreundlichkeit~Lange Wartezeiten~Gedränge~Falsche Erwartungshaltung~Sonstiges;Unfriendly~Long wait~Incompetence~Dirty~Crowd~False expectations;Warum haben dir die Stationen nicht so gefallen?",
                 "2;0;War der Tag der offenen Tür informativ?;Was the open house informative for you?;;;Unfreundlichkeit~Lange Wartezeiten~Inkompetenz~Sonstiges;Unfriendly~Long wait~Incompetence;Warum war er nicht informativ?",
                 "3;0;War das Programm abwechslungsreich?;Was the programme diverse?;;;Langweilig~Sonstiges;Incompetence~Boring;Warum war das Programm nicht abwechslungsreich?",
                 "4;0;Wie haben dir die Stationen am Tag der offenen Tür gefallen?;How did you like;Stationen Informatik;the exhibits and activities of the Department of Informatics ;Unfreundlichkeit~Lange Wartezeiten~Gedränge~Falsche Erwartungshaltung~Sonstiges;Unfriendly~Long wait~Incompetence~Dirty~Crowd~False expectations;Warum haben dir die Informatik Stationen nicht so gefallen?",
                 "8;1;Welchen Zweig würden Sie gerne besuchen?;Which department would you like to attend?;;;Informatik~Medientechnik~Elektronik~Medizintechnik~Keinen;Informatik~Medientechnik~Elektronik~Medizintechnik~None;")

         for (s in strings) {
             var cols = s.split(";")
             var quest = Question(
                     cols[0].toInt(),
                     cols[1].toInt(),
                     cols[2],
                     cols[3],
                     cols[4],
                     cols[5],
                     cols[6].split("~"),
                     cols[7].split("~"),
                     cols[8])
             _questions.add(quest)
         }
 }
 */
}

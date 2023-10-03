package at.htlleonding.visitorassistant.says

import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say

var goodbye_says: ArrayList<Say?>? = null

val GREETING_PHRASE = Phrase("Hallo! Hättest du Zeit für eine kurze Umfrage?")
val QUESTION_ONE_PHRASE = Phrase("Kannst du dir vorstellen, dich für die H T L Leonding anzumelden?")
val QUESTION_TWO_PHRASE = Phrase("Welchen Zweig?")
val PLAINTEXT_GOODBYE_PHRASES: Array<String> = arrayOf("Tschüss", "Auf Wiedersehen", "Tschau", "Bis Bald", "Bye Bye", "Servus", "adeee", "Auf Wiederschaun", "pfiati")

var GREETING: Say? = null
var QUESTION_ONE: Say? = null
var QUESTION_TWO: Say? = null

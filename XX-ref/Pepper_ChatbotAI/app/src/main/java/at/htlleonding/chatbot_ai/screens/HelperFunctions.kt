package at.htlleonding.chatbot_ai.screens

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.`object`.conversation.Say
import java.util.concurrent.Future

class HelperFunctions {

    companion object {
        //make this one true if you are working on the pepper so the qisdk functions work, else turn it to false, so you dont get an error when starting the emulator(the android studio one not the pepper emulator)
        var onPepper:Boolean=false

        var qiContext: QiContext? = null

        fun sayAsync(qiContext: QiContext?, content: String): Future<Void>? {

            val say: Future<Say>? = SayBuilder.with(qiContext)
                .withText(content)
                .buildAsync()
            while (!say!!.isDone) {
            }
            return try {
                say.get().async().run()
            } catch (e: Exception) {
                null
            }
        }
    }
}
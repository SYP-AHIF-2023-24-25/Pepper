package at.htlleonding.visitorassistant

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import at.htlleonding.visitorassistant.navigation.QuestionnaireNavigation
import at.htlleonding.visitorassistant.says.*
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val activity = LocalContext.current as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            QuestionnaireNavigation()
        }

        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        GREETING = SayBuilder.with(qiContext).withPhrase(GREETING_PHRASE).build()
        QUESTION_ONE = SayBuilder.with(qiContext).withPhrase(QUESTION_ONE_PHRASE).build()
        QUESTION_TWO = SayBuilder.with(qiContext).withPhrase(QUESTION_TWO_PHRASE).build()

        goodbye_says = ArrayList()

        for (plain_phrase in PLAINTEXT_GOODBYE_PHRASES) {
            val newPhrase = Phrase(plain_phrase)

            goodbye_says!!.add(SayBuilder.with(qiContext).withPhrase(newPhrase).build())
        }
    }

    override fun onRobotFocusLost() {
    }

    override fun onRobotFocusRefused(reason: String?) {
    }


}

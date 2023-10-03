package at.htlleonding.chatbot_ai

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import at.htlleonding.chatbot_ai.navigation.ChatNavigation
import at.htlleonding.chatbot_ai.screens.HelperFunctions
import at.htlleonding.skeleton.R
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : RobotActivity(), RobotLifecycleCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (HelperFunctions.onPepper) {
                while (HelperFunctions.qiContext == null) {
                }
            }
            val activity = LocalContext.current as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            ChatNavigation(randomImages(10))
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        HelperFunctions.qiContext = qiContext
    }

    override fun onRobotFocusLost() {
    }

    override fun onRobotFocusRefused(reason: String?) {
    }


    fun randomImages(amount: Int): ArrayList<Int> {
        val images = ArrayList<Int>()
        val random = Random()
        var randomNumber: Int
        var prevNumber = 0
        for (i in 1..amount) {
            randomNumber = random.nextInt(4) + 1
            while (randomNumber == prevNumber) {
                randomNumber = random.nextInt(4) + 1
            }
            when (randomNumber) {
                1 -> images.add(R.drawable.bird1)
                2 -> images.add(R.drawable.bird2)
                3 -> images.add(R.drawable.bird3)
                4 -> images.add(R.drawable.bird4)
            }
            prevNumber = randomNumber
        }
        return images
    }

}


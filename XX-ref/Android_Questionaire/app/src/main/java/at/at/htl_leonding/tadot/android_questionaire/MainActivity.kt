package at.at.htl_leonding.tadot.android_questionaire

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import at.at.htl_leonding.tadot.android_questionaire.navigation.QuestionnaireNavigation



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as Activity

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            QuestionnaireNavigation()        }
    }
}

package at.at.htl_leonding.tadot.android_questionaire.screens.goodbye_screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.at.htl_leonding.tadot.android_questionaire.R
import at.at.htl_leonding.tadot.android_questionaire.navigation.Routes

@Composable
fun NegativeGoodbye(navController: NavController) {

    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            Text(
                text = "Schade, auf Wiedersehen",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

        }
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.htl_leonding_logo),
                contentDescription = null, modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
    object : CountDownTimer (3000, 1000){
        override fun onTick(millisecUntilFinished: Long) {
        }
        override fun onFinish() {
            navController.navigate(Routes.HomeScreen.route)
        }
    }.start()

}

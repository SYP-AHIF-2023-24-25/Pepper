package at.htlleonding.visitorassistant.screens.goodbye_screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.htlleonding.visitorassistant.navigation.Routes
import at.htlleonding.visitorassistant.R
import at.htlleonding.visitorassistant.says.goodbye_says
import at.htlleonding.visitorassistant.screens.firstSwitch

var firstSwitchPositiveBye = true


@Composable
fun PositiveGoodbye(navController: NavController) {
    firstSwitch = true

    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            Text(
                text = "Bis bald :)",
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
                contentDescription = null, modifier = Modifier.align(TopCenter)
            )
        }
    }

    if (firstSwitchPositiveBye) {
        while (goodbye_says == null) {}
        goodbye_says!![(Math.random() * goodbye_says!!.size).toInt()]!!.async().run()
        firstSwitchPositiveBye= false
        object : CountDownTimer(2000, 1000){
            override fun onTick(millisecUntilFinished: Long) {
            }
            override fun onFinish() {
                navController.navigate(Routes.HomeScreen.route)
            }
        }.start()
    }
}
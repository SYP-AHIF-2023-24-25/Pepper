package at.htlleonding.visitorassistant.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.htlleonding.visitorassistant.navigation.Routes
import at.htlleonding.visitorassistant.ui.theme.LightGreen
import at.htlleonding.visitorassistant.ui.theme.LightRed
import at.htlleonding.visitorassistant.R
import at.htlleonding.visitorassistant.model.api.TadeotAPI
import at.htlleonding.visitorassistant.model.api.TadeotRequests
import at.htlleonding.visitorassistant.model.data.QuestionData
import at.htlleonding.visitorassistant.says.QUESTION_ONE
import at.htlleonding.visitorassistant.screens.goodbye_screens.firstSwitchNegativeBye
import at.htlleonding.visitorassistant.screens.goodbye_screens.firstSwitchIndecisiveBye
import at.htlleonding.visitorassistant.screens.goodbye_screens.firstSwitchPositiveBye
import at.htlleonding.visitorassistant.ui.theme.LightBlue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.typeOf

var firstSwitch = true

@Composable
fun HomeScreen(navController: NavController) {
    firstSwitchIndecisiveBye = true
    firstSwitchNegativeBye = true
    firstSwitchPositiveBye = true
    firstSwitchMulti = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Kannst Du Dir vorstellen, Dich für die HTL Leonding anzumelden?",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Column(
            Modifier.padding(7.5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .padding(horizontal = 25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(LightGreen)
                        .fillMaxWidth(0.31f)
                        .fillMaxHeight(0.8f)
                ) {
                    Log.d("Navigation", "Button 'Ja' clicked - navigating to ${Routes.MultipleChoice.route}");
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            Log.d("Navigation", "Button 'Ja' clicked - navigating to ${Routes.MultipleChoice.route}");
                            navController.navigate(Routes.MultipleChoice.route)
                          },
                        colors = ButtonDefaults.buttonColors(LightGreen)
                    ) {
                        Text(
                            text = "Ja",
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(30.dp)
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.48f)
                        .fillMaxHeight(0.8f)
                ){
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            TadeotRequests.postAnswer(QuestionData(1, 1, "Weiß noch nicht", "Wenn ja, für welchen Zweig?", ""))!!.enqueue(object :
                                Callback<QuestionData?> {
                                override fun onResponse(call: Call<QuestionData?>, response: Response<QuestionData?>) {
                                    Log.d("HSP", "Request reached server")
                                    navController.navigate(Routes.IndecisiveGoodbye.route)
                                }

                                override fun onFailure(call: Call<QuestionData?>, t: Throwable) {
                                    navController.navigate(Routes.InternetConnectionError.route)
                                }
                            })
                        },
                        colors = ButtonDefaults.buttonColors(LightBlue)
                    ){
                        Text(
                            text = "Vielleicht",
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(25.dp)
                                .padding(horizontal = 7.5.dp)
                        )                    }
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxSize(),
                        onClick = {
                            TadeotRequests.postAnswer(QuestionData(1, 1, "Nein", "Wenn ja, für welchen Zweig?", ""))!!.enqueue(object :
                                Callback<QuestionData?> {
                                override fun onResponse(call: Call<QuestionData?>, response: Response<QuestionData?>) {
                                    Log.d("HSP", "Request reached server")
                                    navController.navigate(Routes.NegativeGoodbye.route)
                                }

                                override fun onFailure(call: Call<QuestionData?>, t: Throwable) {
                                    navController.navigate(Routes.InternetConnectionError.route)
                                }
                            })
                        },
                        colors = ButtonDefaults.buttonColors(LightRed)
                    ) {
                        Text(
                            text = "Nein",
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(30.dp)
                                .padding(horizontal = 7.5.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.htl_leonding_logo),
                    contentDescription = null, modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
        }
    }/*
    if (firstSwitch) {
        while (QUESTION_ONE == null) {}
        QUESTION_ONE!!.async().run()
        firstSwitch = false
    }*/
}

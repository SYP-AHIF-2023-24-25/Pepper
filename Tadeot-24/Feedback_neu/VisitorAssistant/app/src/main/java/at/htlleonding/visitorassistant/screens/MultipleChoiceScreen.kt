package at.htlleonding.visitorassistant.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.htlleonding.visitorassistant.R
import at.htlleonding.visitorassistant.model.api.TadeotRequests
import at.htlleonding.visitorassistant.model.data.QuestionData
import at.htlleonding.visitorassistant.navigation.Routes
import at.htlleonding.visitorassistant.says.QUESTION_TWO
import at.htlleonding.visitorassistant.says.QUESTION_TWO_PHRASE
import at.htlleonding.visitorassistant.ui.theme.ElectronicsRed
import at.htlleonding.visitorassistant.ui.theme.InformaticsBlue
import at.htlleonding.visitorassistant.ui.theme.MediatechnologyBlue
import at.htlleonding.visitorassistant.ui.theme.MedicineTechnologyOrange
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var firstSwitchMulti = true

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MultipleChoiceScreen(
    navController: NavController
) {
    Log.d("Navigation", "Rendering MultipleChoiceScreen")
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
                .background(Color.Transparent)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Für welchen Zweig würdest Du Dich anmelden?",
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            Button(modifier = Modifier
                .padding(top = 20.dp)
                .size(80.dp)
                .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = { navController.navigate(Routes.HomeScreen.route) }) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(InformaticsBlue)
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.7f)

                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            val currentTimestamp: LocalDateTime = LocalDateTime.now()

                            // Format the timestamp as a string
                            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                            val formattedTimestamp: String = currentTimestamp.format(formatter)
                            TadeotRequests.postAnswer(
                                QuestionData(
                                    formattedTimestamp,
                                    "Kannst Du Dir vorstellen, Dich für die HTL Leonding anzumelden?",
                                    1,
                                    1,
                                    "Ja",
                                    "Wenn ja, für welchen Zweig?",
                                    "Informatik"
                                )
                            )!!.enqueue(object :
                                Callback<QuestionData?> {
                                override fun onResponse(call: Call<QuestionData?>, response: Response<QuestionData?>) {
                                    Log.d("HSP", "Request reached server")
                                    navController.navigate(Routes.PositiveGoodbye.route)
                                }

                                override fun onFailure(call: Call<QuestionData?>, t: Throwable) {
                                    navController.navigate(Routes.InternetConnectionError.route)
                                }
                            })
                        },
                        colors = ButtonDefaults.buttonColors(InformaticsBlue)
                    ) {
                        Text(
                            text = "Informatik",
                            fontSize = 30.sp,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                        Image(
                            painter = painterResource(id = R.drawable.informatics_logo),
                            contentDescription = null
                        )
                    }

                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MediatechnologyBlue)
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            val currentTimestamp: LocalDateTime = LocalDateTime.now()

                            // Format the timestamp as a string
                            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                            val formattedTimestamp: String = currentTimestamp.format(formatter)
                            TadeotRequests.postAnswer(
                                QuestionData(
                                    formattedTimestamp,
                                    "Kannst Du Dir vorstellen, Dich für die HTL Leonding anzumelden?",
                                    1,
                                    1,
                                    "Ja",
                                    "Wenn ja, für welchen Zweig?",
                                    "Medientechnik"
                                )
                            )!!.enqueue(object :
                                Callback<QuestionData?> {

                                override fun onResponse(call: Call<QuestionData?>, response: Response<QuestionData?>) {
                                    Log.d("HSP", "Request reached server")
                                }

                                override fun onFailure(call: Call<QuestionData?>, t: Throwable) {
                                    Log.d("HSP", "Negative Goodbye")
                                    navController.navigate(Routes.InternetConnectionError.route)
                                }
                            })
                        },
                        colors = ButtonDefaults.buttonColors(MediatechnologyBlue)
                    ) {
                        Text(
                            text = "Medientechnik ",
                            fontSize = 30.sp,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                        Image(
                            painter = painterResource(id = R.drawable.media_technology_logo),
                            contentDescription = null
                        )

                    }

                }

            }
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(ElectronicsRed)
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            val currentTimestamp: LocalDateTime = LocalDateTime.now()

                            // Format the timestamp as a string
                            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                            val formattedTimestamp: String = currentTimestamp.format(formatter)
                            TadeotRequests.postAnswer(
                                QuestionData(
                                    formattedTimestamp,
                                    "Kannst Du Dir vorstellen, Dich für die HTL Leonding anzumelden?",
                                    1,
                                    1,
                                    "Ja",
                                    "Wenn ja, für welchen Zweig?",
                                    "Elektronik"
                                )
                            )!!.enqueue(object :
                                Callback<QuestionData?> {
                                override fun onResponse(call: Call<QuestionData?>, response: Response<QuestionData?>) {
                                    Log.d("HSP", "Request reached server")
                                    navController.navigate(Routes.PositiveGoodbye.route)
                                }

                                override fun onFailure(call: Call<QuestionData?>, t: Throwable) {
                                    Log.d("HSP", "Negative goodbye")
                                    navController.navigate(Routes.InternetConnectionError.route)
                                }
                            })
                        },
                        colors = ButtonDefaults.buttonColors(ElectronicsRed)
                    ) {
                        Text(
                            text = "Elektronik ",
                            fontSize = 30.sp,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                        Image(
                            painter = painterResource(id = R.drawable.electronics_logo),
                            contentDescription = null
                        )

                    }
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MedicineTechnologyOrange)
                        .fillMaxSize()
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            val currentTimestamp: LocalDateTime = LocalDateTime.now()

                            // Format the timestamp as a string
                            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                            val formattedTimestamp: String = currentTimestamp.format(formatter)
                            TadeotRequests.postAnswer(
                                QuestionData(
                                    formattedTimestamp,
                                    "Kannst Du Dir vorstellen, Dich für die HTL Leonding anzumelden?",
                                    1,
                                    1,
                                    "Ja",
                                    "Wenn ja, für welchen Zweig?",
                                    "Medizintechnnik"
                                )
                            )!!.enqueue(object :
                                Callback<QuestionData?> {
                                override fun onResponse(call: Call<QuestionData?>, response: Response<QuestionData?>) {
                                    Log.d("HSP", "Request reached server")
                                    navController.navigate(Routes.PositiveGoodbye.route)
                                }

                                override fun onFailure(call: Call<QuestionData?>, t: Throwable) {
                                    navController.navigate(Routes.InternetConnectionError.route)
                                }
                            })
                        },
                        colors = ButtonDefaults.buttonColors(MedicineTechnologyOrange)
                    ) {
                        Text(
                            text = "Medizintechnik",
                            fontSize = 30.sp,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                        Image(
                            painter = painterResource(id = R.drawable.medicine_technology_logo),
                            contentDescription = null
                        )

                    }

                }

            }
            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.htl_leonding_logo),
                    contentDescription = null
                )
            }
        }
    }

    if (firstSwitchMulti) {
        /*
        while (QUESTION_TWO == null) {
            Log.d("Navigation", "Waiting for QUESTION_TWO...");
        }
        QUESTION_TWO!!.async().run()*/
        firstSwitchMulti = false
    }
}

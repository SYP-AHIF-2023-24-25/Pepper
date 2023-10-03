package at.at.htl_leonding.tadot.android_questionaire.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import at.at.htl_leonding.tadot.android_questionaire.R
import at.at.htl_leonding.tadot.android_questionaire.navigation.Routes
import com.example.visitorassistant.ui.theme.ElectronicsRed
import com.example.visitorassistant.ui.theme.InformaticsBlue
import com.example.visitorassistant.ui.theme.MediatechnologyBlue
import com.example.visitorassistant.ui.theme.MedicineTechnologyOrange

@Composable
fun MultipleChoiceScreen(
    navController: NavController
) {
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
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )

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
                        onClick = { navController.navigate(Routes.PositiveGoodbye.route) },
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
                        onClick = { navController.navigate(Routes.PositiveGoodbye.route) },
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
                        onClick = { navController.navigate(Routes.PositiveGoodbye.route) },
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
                        onClick = { navController.navigate(Routes.PositiveGoodbye.route) },
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
}
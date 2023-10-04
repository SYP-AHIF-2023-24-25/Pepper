package at.htlleonding.visitorassistant.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.at.htl_leonding.tadot.android_questionaire.R
import at.at.htl_leonding.tadot.android_questionaire.navigation.Routes
import com.example.visitorassistant.ui.theme.LightGreen
import com.example.visitorassistant.ui.theme.LightRed


import com.example.visitorassistant.ui.theme.LightBlue

var firstSwitch = true

@Composable
fun HomeScreen(navController: NavController) {


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
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight(0.8f)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = { navController.navigate(Routes.MultipleChoice.route) },
                        colors = ButtonDefaults.buttonColors(LightGreen)
                    ) {
                        Text(
                            text = "Ja",
                            fontSize = 50.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(80.dp)
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight(0.8f)
                ){
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {navController.navigate(Routes.PositiveGoodbye.route)},
                        colors = ButtonDefaults.buttonColors(LightBlue)
                    ){
                        Text(
                            text = "Vielleicht",
                            fontSize = 50.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(80.dp)
                                .padding(horizontal = 7.5.dp)
                        )                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight(0.8f)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxSize(),
                        onClick = { navController.navigate(Routes.NegativeGoodbye.route) },
                        colors = ButtonDefaults.buttonColors(LightRed)
                    ) {
                        Text(
                            text = "Nein",
                            fontSize = 50.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(80.dp)
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
    }

}


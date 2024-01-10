package at.htlleonding.visitorassistant.screens.error_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.htlleonding.visitorassistant.R
import at.htlleonding.visitorassistant.navigation.Routes

@Composable
fun InternetConnectionError(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Transparent)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Keine Internet Verbindung! Bitte informiere den Pepper-sitter in deiner Nähe!",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(
                    Alignment.Center
                )
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

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.htl_leonding_logo),
                contentDescription = null, modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
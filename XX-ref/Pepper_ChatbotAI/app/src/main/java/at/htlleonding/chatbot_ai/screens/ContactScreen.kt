package at.htlleonding.chatbot_ai.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.htlleonding.chatbot_ai.model.api.HistoryRequests
import at.htlleonding.chatbot_ai.model.data.HistoryData
import at.htlleonding.chatbot_ai.screens.HelperFunctions.Companion.onPepper
import at.htlleonding.chatbot_ai.theme.DarkBlue
import at.htlleonding.chatbot_ai.theme.Teal
import com.aldebaran.qi.sdk.util.FutureUtils.wait
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.absoluteValue


@Composable
fun ContactScreen(navController: NavController, randomImages: ArrayList<Int>) {

    var switchOn by remember { mutableStateOf(false) }

    var historyData by remember { mutableStateOf(ArrayList<HistoryData>()) }

    DisposableEffect(Unit) {
        val disposable = CoroutineScope(Dispatchers.IO).launch {
            val historyRequests = getHistory()
            withContext(Dispatchers.Main) {
                historyData = historyRequests
                Log.d("History Data in Disposable Effect", "HistoryData: $historyData")
            }
        }

        onDispose {
            disposable.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DarkBlue,
                        Teal
                    ),
                )
            )
    ) {
        if (historyData.isEmpty()) {
            Log.d("History waiting", "HistoryData: $historyData")
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 270.dp),
                color = Color.White
            )
        } else {
            Log.d("History given", "HistoryData: $historyData")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.14f)
                    .padding(vertical = 20.dp)

            ) {
                Text(
                    text = "Kontakte",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.89f)
                        .padding(start = 60.dp)
                )
                Switch(
                    checked = switchOn,
                    onCheckedChange = { switchOn_ ->
                        switchOn = switchOn_
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DarkBlue,
                        checkedTrackColor = Color.White
                    )

                )
            }
            onPepper = switchOn;
            Box(
                modifier = Modifier
                    .padding(horizontal = 60.dp)
                    .padding(vertical = 10.dp)
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
            ) {
                Column {
                    historyData.forEach() {
                        val i = historyData.indexOf(it)
                        Log.d("ContactScreen", "HistoryData: ${it!!.historyName}")
                        SingleContact(navController, randomImages, it)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                }
            }
        }
    }
}

@Composable
fun SingleContact(navController: NavController, randomImages: ArrayList<Int>, historyData: HistoryData) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White.copy(alpha = 0.45f))
            .padding(bottom = 5.dp)
            .height(85.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("ChatScreen/${historyData.id}") }

    ) {
        Row {
            Image(
                painter = painterResource(randomImages[historyData.id]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(60.dp)
                    .clip(
                        CircleShape
                    )
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = historyData.historyName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Left,
                color = DarkBlue,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 25.dp)
            )
        }
    }
}

suspend fun getHistory(): ArrayList<HistoryData> {
    return suspendCoroutine { continuation ->
        val call = HistoryRequests.getHistory()
        call.enqueue(object : Callback<ArrayList<HistoryData>> {
            override fun onResponse(
                call: Call<ArrayList<HistoryData>>,
                response: Response<ArrayList<HistoryData>>
            ) {
                if (response.isSuccessful) {
                    val historyData = response.body()
                    continuation.resume(historyData ?: ArrayList())
                } else {
                    Log.e("History", "Request was not successful")
                    continuation.resume(ArrayList())
                }
            }

            override fun onFailure(call: Call<ArrayList<HistoryData>>, t: Throwable) {
                Log.e("History", "Request failed", t)
                continuation.resume(ArrayList())
            }
        })
    }
}

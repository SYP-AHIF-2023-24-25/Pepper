package at.htlleonding.chatbot_ai.screens

import android.os.Build
import android.util.Log
import android.widget.GridLayout.Spec
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import at.htlleonding.chatbot_ai.model.api.ChatbotRequests.Companion.postQuery
import at.htlleonding.chatbot_ai.model.api.HistoryRequests
import at.htlleonding.chatbot_ai.model.api.HistoryRequests.Companion.getHistoryById
import at.htlleonding.chatbot_ai.model.data.ChatData
import at.htlleonding.chatbot_ai.model.data.ChatResponse
import at.htlleonding.chatbot_ai.model.data.HistoryData
import at.htlleonding.chatbot_ai.navigation.Routes
import at.htlleonding.chatbot_ai.screens.HelperFunctions.Companion.onPepper
import at.htlleonding.skeleton.R
import at.htlleonding.chatbot_ai.theme.DarkBlue
import at.htlleonding.chatbot_ai.theme.Teal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(navController: NavController, id: Int) {
    val kc = LocalSoftwareKeyboardController.current

    var messagehistoryid = id
    var historyName = "Kontakt $id"
    var output by remember {
        mutableStateOf("")
    }

    val messages = remember {
        mutableStateListOf<Message>()
    }
    var status by remember {
        mutableStateOf("\njetzt online")
    }
    var temp: String

    var historyData by remember {
        mutableStateOf<HistoryData?>(null)
    }

    DisposableEffect(Unit) {
        val disposable = CoroutineScope(Dispatchers.IO).launch {
            val historyRequests = getHistoryById(id)
            withContext(Dispatchers.Main) {
                historyData = historyRequests
                Log.d("History Data in Disposable Effect", "HistoryData: $historyData")
            }
        }

        onDispose {
            disposable.cancel()
        }
    }


    Box(
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
        if(historyData == null){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                color = Color.White
            )
            Log.d("History Data in if statement", "HistoryData: $historyData")

        }
        else {
            if(messages.isEmpty()){
                messages.addAll(historyData!!.messages)
            }
            Log.d("History Data in else statement", "HistoryData: $historyData")

            Column {
                Header(status, navController, id, historyName)
                Spacer(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .verticalScroll(rememberScrollState(Int.MAX_VALUE))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {

                        messages.forEach { message ->
                            MessageBubble(
                                message = message.message,
                                isUser = message.isMine
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(modifier = Modifier.align(Alignment.Center)) {
                        TextField(
                            value = output,
                            onValueChange = { output = it },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(top = 20.dp)
                                .padding(horizontal = 10.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                        )
                        if (output.contains("\n")) {
                            kc?.hide()
                            output = output.replace("\n", "")
                        }
                        Button(
                            onClick = {
                                kc?.hide()
                                if (output.isNotEmpty()) {
                                    if (messages.isEmpty()) {
                                        messagehistoryid = -1
                                    }
                                    val message = Message(output, true, messagehistoryid)
                                    Log.d("Message to be sent", "Message: $message")
                                    messages.add(message)
                                    temp = output
                                    output = ""
                                    postQuery(ChatData(message, historyName))!!.enqueue(object :
                                        Callback<ChatResponse?> {
                                        override fun onResponse(
                                            call: Call<ChatResponse?>,
                                            response: Response<ChatResponse?>
                                        ) {
                                            if (response.code() == 400) {
                                                messages.add(
                                                    Message(
                                                        "Tut mir leid. Das weiß ich nicht. Bitte frag mich etwas anderes.",
                                                        false,
                                                        messagehistoryid
                                                    )
                                                )
                                                if (onPepper) {
                                                    HelperFunctions.sayAsync(
                                                        HelperFunctions.qiContext,
                                                        "Tut mir leid. Das weiß ich nicht. Bitte frag mich etwas anderes."
                                                    );
                                                }
                                            } else {
                                                Log.e("Response body", "Response: ${response}")
                                                messages.add(
                                                    response.body()!!.responseMessage
                                                )
                                                Log.d("HSP", "Response: ${response.body()}")
                                                status = "\n jetzt online"
                                                if (onPepper) {
                                                    HelperFunctions.sayAsync(
                                                        HelperFunctions.qiContext,
                                                        response.body()!!.responseMessage.message.replace(
                                                            "\n\n",
                                                            ""
                                                        )
                                                    );
                                                }
                                            }
                                        }

                                        @RequiresApi(Build.VERSION_CODES.O)
                                        override fun onFailure(
                                            call: Call<ChatResponse?>,
                                            t: Throwable
                                        ) {
                                            status = "\n zuletzt online um: ${
                                                LocalDateTime.now().format(
                                                    DateTimeFormatter.ofPattern("HH:mm")
                                                )
                                            } Uhr"
                                            Log.e("HSP", "Error: ${t.message}")
                                            if (t.message == "timeout") {
                                                messages.add(
                                                    Message(
                                                        "Tut mir leid. Das weiß ich nicht. Bitte frag mich etwas anderes.",
                                                        false,
                                                        messagehistoryid
                                                    )
                                                )
                                                if (onPepper) {
                                                    HelperFunctions.sayAsync(
                                                        HelperFunctions.qiContext,
                                                        "Tut mir leid. Das weiß ich nicht. Bitte frag mich etwas anderes."
                                                    );
                                                }
                                            } else {
                                                messages.add(
                                                    Message(
                                                        "Tut mir leid. Ich bin gerade  müde. Bitte versuche es später noch einmal.",
                                                        false,
                                                        messagehistoryid
                                                    )
                                                )
                                                if (onPepper) {
                                                    HelperFunctions.sayAsync(
                                                        HelperFunctions.qiContext,
                                                        "Tut mir leid. Ich bin gerade müde. Bitte versuche es später noch einmal."
                                                    );
                                                }
                                            }
                                            Log.e("HSP", "Error: ${t.message}")
                                        }
                                    })
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),

                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .size(60.dp)
                                .clip(
                                    CircleShape
                                )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.airplane),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(status: String, navController: NavController, id: Int, historyName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)
    ) {
        Spacer(
            modifier = Modifier
                .background(Color.Transparent)
                .size(20.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterVertically)
                .clickable { navController.navigate(Routes.ContactScreen.route) }
        )
        Spacer(
            modifier = Modifier
                .background(Color.Transparent)
                .size(340.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.pepper),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(
                    CircleShape
                )
                .align(Alignment.CenterVertically)
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 25.sp, color = Color.White)) {
                    append(historyName)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                ) {
                    append(status)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 10.dp)

        )

    }
}

@Composable
fun MessageBubble(
    message: String,
    isUser: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentWidth(align = if (isUser) Alignment.End else Alignment.Start)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = if (isUser) Color.LightGray else Color.White,
            elevation = 1.dp
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body2
            )
        }
    }
}



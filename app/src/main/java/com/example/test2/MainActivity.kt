package com.example.test2

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.test2.ml.IntentModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var speechRecognition: SpeechToTextHelper
    private lateinit var texttospeech: TextToSpeechHelper
    private var recognizedSpeech: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var openApp = functionality(this)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }






//        findViewById<TextView>(R.id.tv2).text = predictedIntent


        val geminiAIHelper = GeminiAIHelper()
        val inp = findViewById<EditText>(R.id.inputBox)
        var tv2 = findViewById<TextView>(R.id.tv2)




        texttospeech = TextToSpeechHelper(this)
        speechRecognition = SpeechToTextHelper(this) { recognizedText ->
            tv2.text = recognizedText
            if (tv2.text == "YouTube" || tv2.text == "open YouTube") {
                val intent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
                if (intent != null) {
                    startActivity(intent)
                } else {
                    // YouTube app is not installed, open in browser
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
                    startActivity(browserIntent)
                }

            }

//            geminiAIHelper.getGeminiResponse(tv2.text.toString()){
//                    response->texttospeech.speak(response)
//
//            }


        }


        val micBtn = findViewById<Button>(R.id.mic)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        val lottieAnimationView2 = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        var chk = false
        var c = 0
        lottieAnimationView2.cancelAnimation()
        micBtn.setOnClickListener {

            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)


//            val x=openApp.getBatteryPercentage(this)
//            Log.i("my_tag", x.toString())



//            lottieAnimationView.visibility= VISIBLE
//            lottieAnimationView2.playAnimation()
//            chk=true
//            if(c==0) {
//                openApp.toggleFlashlight(true)
//
//                c+=1
//            }
//            else {
//                tv2.text = openApp.toggleFlashlight(false).toString()
//                c-=1
//            }
//            openApp.open_app(detectIntent(inp.text.toString()))


        }
        lottieAnimationView2.setOnClickListener {
            if (chk) {
                lottieAnimationView2.pauseAnimation()
                chk = false
                speechRecognition.stopListening()
                texttospeech.stopSpeaking()


            } else {
                lottieAnimationView2.playAnimation()
                chk = true

                speechRecognition.startListening()
            }


        }


    }

    fun detectIntent(userInput: String): String {
        val commands = mapOf(
            "youtube" to listOf("open youtube", "launch youtube", "start youtube", "run youtube"),
            "instagram" to listOf(
                "open instagram",
                "launch instagram",
                "start instagram",
                "run instagram"
            ),
            "whatsapp" to listOf(
                "open whatsapp",
                "launch whatsapp",
                "start whatsapp",
                "run whatsapp"
            ),
            "facebook" to listOf(
                "open facebook",
                "launch facebook",
                "start facebook",
                "run facebook"
            ),
            "twitter" to listOf("open twitter", "launch twitter", "start twitter", "run twitter"),
            "snapchat" to listOf(
                "open snapchat",
                "launch snapchat",
                "start snapchat",
                "run snapchat"
            ),
            "spotify" to listOf("open spotify", "launch spotify", "start spotify", "run spotify"),
            "chrome" to listOf("open chrome", "launch chrome", "start chrome", "run chrome"),
            "gmail" to listOf("open gmail", "launch gmail", "start gmail", "run gmail"),
            "flashlight_on" to listOf("turn on flashlight", "enable flashlight", "flashlight on"),
            "flashlight_off" to listOf(
                "turn off flashlight",
                "disable flashlight",
                "flashlight off"
            ),
            "greet" to listOf("hi", "hello", "hey", "wassup"),
            "set_alarm" to listOf("set an alarm", "create an alarm", "schedule an alarm"),
            "stop_alarm" to listOf("stop alarm", "turn off alarm", "disable alarm"),
            "about_bot" to listOf("who are you", "what is your name", "introduce yourself"),
            "developed_by" to listOf(
                "developed you", "created you", "about developer", "made this assistant",
                "behind this ai", "built this assistant?", "who designed you", "who programmed you"
            ),
            "about_bot" to listOf(
                "who are you", "your name", "about yourself", "introduce yourself",
                "who you are?", "what do people call you?", "who am i talking to?", "your identity"
            ),
            "navigation" to listOf(
                "open maps", "launch maps", "start google maps", "navigate to home"
            ),
            "music_control" to listOf(
                "play music", "pause music", "resume music", "next song", "previous song"
            ),
            "weather" to listOf(
                "weather", "today's weather"
            ),
            "time" to listOf(
                "time", "current time"
            ),
            "phone_control" to listOf(
                "increase volume", "decrease volume", "mute the phone", "turn on do not disturb"
            ),
            "calls_messages" to listOf(
                "call", "send a message to John", "text dad saying I’ll be late"
            ),
            "wifi_bluetooth" to listOf(
                "turn on WiFi", "turn off WiFi", "enable Bluetooth", "disable Bluetooth"
            ),
            "device_status" to listOf(
                "how much battery is left?",
                "what's my battery percentage?",
                "is my phone charging?"
            )
        )


        val lowerInput = userInput.lowercase()
        for ((intent, phrases) in commands) {
            if (phrases.any { phrase -> lowerInput.contains(phrase) }) {
                return intent
            }
        }
        return "unknown_intent"
    }

    fun getIntentFromIndex(index: Int): String {
        val intents = arrayOf(
            "about_bot", "developed_by", "flash_off", "flash_on", "greet_hi",
            "open_chrome", "open_facebook", "open_gmail", "open_insta", "open_snapchat",
            "open_spotify", "open_tiktok", "open_twitter", "open_whatsapp",
            "open_youtube", "set_alarm", "stop_alarm"
        )


        return if (index in intents.indices) intents[index] else "unknown_intent"
    }


}

//    fun detectIntent(userInput: String): String {
//        val commands = mapOf(
//            "youtube" to listOf(
//                "open youtube", "launch youtube", "start youtube", "can you open youtube for me?",
//                "i want to play youtube", "run youtube", "go to youtube", "access youtube"
//            ),
//            "instagram" to listOf(
//                "open instagram", "launch instagram", "start instagram", "can you open instagram for me?",
//                "i want to play instagram", "run instagram", "go to instagram", "access instagram",
//                "run insta", "open insta"
//            ),
//            "whatsapp" to listOf(
//                "open whatsapp", "launch whatsapp", "start whatsapp", "can you open whatsapp for me?",
//                "i want to use whatsapp", "run whatsapp", "go to whatsapp", "access whatsapp"
//            ),
//            "facebook" to listOf(
//                "open facebook", "launch facebook", "start facebook", "can you open facebook for me?",
//                "i want to browse facebook", "run facebook", "go to facebook", "access facebook"
//            ),
//            "twitter" to listOf(
//                "open twitter", "launch twitter", "start twitter", "can you open twitter for me?",
//                "i want to browse twitter", "run twitter", "go to twitter", "access twitter"
//            ),
//            "snapchat" to listOf(
//                "open snapchat", "launch snapchat", "start snapchat", "can you open snapchat for me?",
//                "i want to use snapchat", "run snapchat", "go to snapchat", "access snapchat"
//            ),
//            "spotify" to listOf(
//                "open spotify", "launch spotify", "start spotify", "can you open spotify for me?",
//                "i want to play music on spotify", "run spotify", "go to spotify", "access spotify"
//            ),
//            "chrome" to listOf(
//                "open google chrome", "launch chrome", "start chrome", "can you open chrome for me?",
//                "i want to browse on chrome", "run chrome", "go to chrome", "access chrome"
//            ),
//            "gmail" to listOf(
//                "open gmail", "launch gmail", "start gmail", "can you open gmail for me?",
//                "i want to check my gmail", "run gmail", "go to gmail", "access gmail"
//            ),
//            "greet" to listOf(
//                "hi", "hii", "hy", "hyy", "wassup", "hello", "hlo", "hello "
//            ),
//            "set_alarm" to listOf(
//                "set an alarm", "create an alarm", "schedule an alarm", "can you set an alarm for me?",
//                "i want to set an alarm", "make an alarm", "alarm for 7 am", "wake me up at 6 am"
//            ),
//            "stop_alarm" to listOf(
//                "stop the alarm", "turn off the alarm", "disable the alarm", "cancel my alarm",
//                "can you stop my alarm?", "end the alarm", "dismiss the alarm", "alarm off"
//            ),
//            "flashlight_on" to listOf(
//                "turn on flashlight", "enable flashlight", "switch on flashlight", "can you turn on the flashlight?",
//                "i need light", "activate flashlight", "flashlight on", "turn the light on"
//            ),
//            "flashlight_off" to listOf(
//                "turn off flashlight", "disable flashlight", "switch off flashlight", "can you turn off the flashlight?",
//                "stop the light", "deactivate flashlight", "flashlight off", "turn the light off"
//            ),
//            "developed_by" to listOf(
//                "who developed you?", "who created you?", "tell me about your developer", "who made this assistant?",
//                "who is behind this ai?", "who built this assistant?", "who designed you?", "who programmed you?"
//            ),
//            "about_bot" to listOf(
//                "who are you?", "what is your name?", "tell me about yourself", "introduce yourself",
//                "can you tell me who you are?", "what do people call you?", "who am i talking to?", "tell me your identity"
//            ),
//            "navigation" to listOf(
//                "open maps", "launch maps", "start google maps", "navigate to home", "show me the route to work"
//            ),
//            "music_control" to listOf(
//                "play music", "pause music", "resume music", "next song", "previous song"
//            ),
//            "weather" to listOf(
//                "what's the weather like?", "tell me today's weather", "is it going to rain today?"
//            ),
//            "time_date" to listOf(
//                "what time is it?", "tell me the current time", "what's today's date?"
//            ),
//            "phone_control" to listOf(
//                "increase volume", "decrease volume", "mute the phone", "turn on do not disturb"
//            ),
//            "calls_messages" to listOf(
//                "call mom", "call my friend", "send a message to John", "text dad saying I’ll be late"
//            ),
//            "wifi_bluetooth" to listOf(
//                "turn on WiFi", "turn off WiFi", "enable Bluetooth", "disable Bluetooth"
//            ),
//            "device_status" to listOf(
//                "how much battery is left?", "what's my battery percentage?", "is my phone charging?"
//            )
//        )
//
//        val lowerInput = userInput.lowercase()
//
//        for ((intent, phrases) in commands) {
//            if (phrases.any { lowerInput.contains(it) }) {
//                return intent
//            }
//        }
//        return "unknown_intent"
//    }








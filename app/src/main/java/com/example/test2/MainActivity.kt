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
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
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
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val geminiAIHelper = GeminiAIHelper()

        var tv2 = findViewById<TextView>(R.id.tv2)
        texttospeech = TextToSpeechHelper(this)
        speechRecognition = SpeechToTextHelper(this) { recognizedText ->
            tv2.text = recognizedText
            if(tv2.text == "YouTube" ||tv2.text== "open YouTube"){
                val intent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
                if (intent != null) {
                    startActivity(intent)
                } else {
                    // YouTube app is not installed, open in browser
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
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
        lottieAnimationView2.cancelAnimation()
        micBtn.setOnClickListener {
//            lottieAnimationView.visibility= VISIBLE
//            lottieAnimationView2.playAnimation()
//            chk=true



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
}





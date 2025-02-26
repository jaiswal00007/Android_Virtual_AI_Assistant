package com.example.test2
import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(context: Context) {
    private var textToSpeech: TextToSpeech? = null

    init {
        val googleTtsEngine = "com.google.android.tts" // Google TTS package name

        textToSpeech = TextToSpeech(context, { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US // Set language

                // List available voices in Logcat
                val availableVoices = textToSpeech?.voices
                availableVoices?.forEach { Log.d("TTS", "Voice: ${it.name}") }

                // Select a specific voice (Example: Male/Female)
                val selectedVoice = availableVoices?.find { it.name.contains("en-gb-x-rjs-local") }
                if (selectedVoice != null) {
                    textToSpeech?.voice = selectedVoice
                }
            } else {
                Log.e("TTS", "Initialization failed!")
            }
        }, googleTtsEngine) // Specify Google TTS engine here
    }

    fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)


    }
    fun stopSpeaking(){
        textToSpeech?.stop()
    }

    fun shutdown() {
        textToSpeech?.shutdown()
    }
}

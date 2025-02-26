package com.example.test2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import java.util.Locale

class SpeechToTextHelper(private val context: Context, private val callback: (String) -> Unit) {
    private var speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    init {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d("SpeechToTextHelper", "Ready for speech")
            }

            override fun onBeginningOfSpeech() {
                Log.d("SpeechToTextHelper", "Speech started")
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Not needed
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Not needed
            }

            override fun onEndOfSpeech() {
                Log.d("SpeechToTextHelper", "Speech ended")
            }

            override fun onError(error: Int) {
                Log.e("SpeechToTextHelper", "Error: $error")

                Toast.makeText(context, "Speech recognition error: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val recognizedText = matches?.get(0) ?: "No speech detected"
                callback(recognizedText) // Send text back to MainActivity
            }

            override fun onPartialResults(partialResults: Bundle?) {
                // Optional: Handle partial results
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Not needed
            }
        })
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer.stopListening()
    }

    fun destroy() {
        speechRecognizer.destroy()
    }
}

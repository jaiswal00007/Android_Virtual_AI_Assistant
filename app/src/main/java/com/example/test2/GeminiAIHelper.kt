package com.example.test2

import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class GeminiAIHelper {
    private val API_KEY = "AIzaSyByTw1v4lnBZVRJdjG51ebAaxdIah4ENOo"  // Secure this key
    private val client = OkHttpClient()

    fun getGeminiResponse(inputText: String, callback: (String) -> Unit) {
        val json = """
            {
                "contents": [
                    {
                        "parts": [
                            {
                                "text": "$inputText"
                            }
                        ]
                    }
                ]
            }
        """.trimIndent()

        val mediaType = "application/json".toMediaType()
        val requestBody = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$API_KEY")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    callback("Error: ${response.code} - ${response.message}")
                    return
                }

                val responseData = response.body?.string()
                try {
                    val jsonObject = JSONObject(responseData ?: "{}")
                    val candidates = jsonObject.optJSONArray("candidates")

                    val text = if (candidates != null && candidates.length() > 0) {
                        candidates.getJSONObject(0)
                            .optJSONObject("content")
                            ?.optJSONArray("parts")
                            ?.optJSONObject(0)
                            ?.optString("text", "No response from Gemini")
                    } else {
                        "No valid response"
                    }

                    callback(text ?: "No response from Gemini")
                } catch (e: Exception) {
                    callback("Parsing Error: ${e.message}")
                }
            }
        })
    }
}

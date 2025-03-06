package com.example.test2

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.ContentResolver
import android.content.IntentFilter
import android.database.Cursor
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.provider.ContactsContract

import androidx.core.app.ActivityCompat.startActivityForResult


@Suppress("DEPRECATION")
class functionality(private val context: Context) {
    var flashLightStatus: Boolean = false
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    // Mapping app names to their correct package names
    private val appPackages = mapOf(
        "youtube" to "com.google.android.youtube",
        "instagram" to "com.instagram.android",
        "whatsapp" to "com.whatsapp",
        "facebook" to "com.facebook.katana",
        "twitter" to "com.twitter.android",
        "snapchat" to "com.snapchat.android",
        "tiktok" to "com.zhiliaoapp.musically",
        "spotify" to "com.spotify.music",
        "chrome" to "com.android.chrome",
        "gmail" to "com.google.android.gm"
    )

    // Mapping apps to their correct website URLs
    private val appUrls = mapOf(
        "youtube" to "https://www.youtube.com",
        "instagram" to "https://www.instagram.com",
        "whatsapp" to "https://web.whatsapp.com",
        "facebook" to "https://www.facebook.com",
        "twitter" to "https://twitter.com",
        "snapchat" to "https://www.snapchat.com",
        "tiktok" to "https://www.tiktok.com",
        "spotify" to "https://www.spotify.com",
        "chrome" to "https://www.google.com",
        "gmail" to "https://mail.google.com"
    )

    fun open_app(appName: String) {
        val packageName = appPackages[appName.lowercase()]
        val url = appUrls[appName.lowercase()]

        if (packageName != null) {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                context.startActivity(intent)
                return
            }
        }

        // If app is not installed, open in browser
        if (url != null) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        } else {
            Toast.makeText(context, "App not found", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun toggleFlashlight(turnOn: Boolean): Int {
        if (flashLightStatus == turnOn) {
            return -1
        } else {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, turnOn)
            flashLightStatus = turnOn
            return 0
        }

    }

    // to make a call by name
    fun callContactByName(contactName: String) {
        // Check if permissions are granted
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE),
                1
            )
            return
        }

        // Get the phone number associated with the contact name
        val phoneNumber = getPhoneNumberFromContact(contactName)

        if (phoneNumber != null) {
            // Create the Intent to make the call
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(intent)
        } else {
            // Handle the case when no contact is found
            println("Contact not found")
        }
    }

    fun getPhoneNumberFromContact(contactName: String): String? {
        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} = ?",
            arrayOf(contactName),
            null
        )

        var phoneNumber: String? = null
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val numberIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (numberIndex != -1) {
                    phoneNumber = cursor.getString(numberIndex)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close() // Ensure the cursor is closed after use
        }

        return phoneNumber
    }

    // to turn on/off wifi
    fun switchnWifi(context: Context, switch: Int) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (switch == 1) {
                if (!wifiManager.isWifiEnabled) {
                    wifiManager.isWifiEnabled = true
                    Toast.makeText(context, "Wi-Fi turned ON", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Wi-Fi is already ON", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (wifiManager.isWifiEnabled) {
                    wifiManager.isWifiEnabled = false
                } else {
                    Toast.makeText(context, "Wi-Fi is already OFF", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // For Android 10 (API level 29) and above, request the user to turn on Wi-Fi in settings
            val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(intent)
        }
    }
    // to get battery percentage
    fun getBatteryPercentage(context: Context): Int {
        // Create an intent filter to listen for battery status updates
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        // Get the battery status from the system
        val batteryStatus: Intent? = context.registerReceiver(null, ifilter)

        // Retrieve battery level and scale
        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        // Calculate battery percentage
        return if (level != -1 && scale != -1) {
            (level / scale.toFloat() * 100).toInt()
        } else {
            -1 // If the battery percentage cannot be fetched
        }
    }



    fun adjustVolume(action: Int) {
        when (action) {
            1 -> increaseVolume()
            -1 -> decreaseVolume()
            0 -> muteVolume()
            10 -> unmuteVolume()
        }
    }

    private fun increaseVolume() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_RAISE,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun decreaseVolume() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_LOWER,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun muteVolume() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_MUTE,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun unmuteVolume() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_UNMUTE,
            AudioManager.FLAG_SHOW_UI
        )
    }
    // to turn on bluetooth

}



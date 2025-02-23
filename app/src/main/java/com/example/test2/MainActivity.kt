package com.example.test2

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val micBtn=findViewById<Button>(R.id.mic)
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        val lottieAnimationView2 = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        var chk=false
        lottieAnimationView2.cancelAnimation()
        micBtn.setOnClickListener{
//            lottieAnimationView.visibility= VISIBLE
            lottieAnimationView2.playAnimation()
            chk=true



        }
        lottieAnimationView2.setOnClickListener{
            if(chk) {
                lottieAnimationView2.pauseAnimation()
                chk=false
            }
            else{
                lottieAnimationView2.playAnimation()
                chk=true
            }
        }




    }
}
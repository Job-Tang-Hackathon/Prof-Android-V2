package com.gsm.prof_androidv2.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.gsm.prof_androidv2.R

class SplashActivity : AppCompatActivity() {
    lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val text: TextView = findViewById(R.id.main_text)
        val t1: TextView = findViewById(R.id.t1)
        val t2: TextView = findViewById(R.id.t2)
        val t3: TextView = findViewById(R.id.t3)
        val anim = AnimationUtils.loadAnimation(this, R.anim.pop)
        val slide = AnimationUtils.loadAnimation(this,R.anim.slide)
        t1.startAnimation(slide)
        t2.startAnimation(slide)
        t3.startAnimation(slide)
        Handler().postDelayed({
            text.startAnimation(anim)
        },1500)

    }
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            checkUserUid()

        }, 3000)
    }
    private fun checkUserUid() {
        uid = FirebaseAuth.getInstance().uid.toString()
        Log.d("로그", "uid : $uid")
        if (uid != "null") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



}
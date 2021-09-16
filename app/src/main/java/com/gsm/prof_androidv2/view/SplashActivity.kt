package com.gsm.prof_androidv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.gsm.prof_androidv2.R

class SplashActivity : AppCompatActivity() {
    lateinit var uid :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                checkUserUid()

            },
            1500
        )


    }

    private fun checkUserUid(){
        uid = FirebaseAuth.getInstance().uid.toString()
        Log.d("로그","uid : $uid")
        if(uid != "null"){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
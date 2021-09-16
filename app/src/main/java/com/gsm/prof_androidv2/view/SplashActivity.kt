package com.gsm.prof_androidv2.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            hideSystemUIAndNavigation(this)
        }
    }

    private fun checkUserUid(){
        uid = FirebaseAuth.getInstance().uid.toString()
        Log.d("로그","uid : $uid")
        if(uid != "null"){
            val intent = Intent(this,CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun hideSystemUIAndNavigation(activity: Activity) {
        val decorView: View = activity.window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

//
//    private fun splashScreen(){
//
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//
//        binding.textWriter
//            .setWidth(12F)
//            .setDelay(50)
//            .setColor(R.color.black)
//            .setConfig(TextWriter.Configuration.INTERMEDIATE)
//            .setSizeFactor(30f)
//            .setLetterSpacing(25f)
//            .setText("COVID SEE")
//            .setListener(TextWriter.Listener {
//                binding.cvSeeImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pop))
//                Handler().postDelayed({
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                },1500)
//            })
//            .startAnimation()
//    }

}
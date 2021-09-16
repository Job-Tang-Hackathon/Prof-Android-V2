package com.gsm.prof_androidv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivityCategoryBinding
import com.gsm.prof_androidv2.view.upload.ProjectUploadActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activity = this

    }

    fun categoryAndroidClick(view: View){
        intent("android")
    }

    fun categoryIosClick(view: View){
        intent("ios")
    }

    fun categoryFrontendClick(view: View){
        intent("frontend")
    }

    fun categoryBackendClick(view: View){
        intent("backend")
    }

    fun categoryAiClick(view: View){
        intent("ai")
    }

    fun categoryGameClick(view: View){
        intent("game")
    }

    fun categoryIotClick(view: View){
        intent("iot")
    }

    fun categoryInfoSecurityClick(view: View){
        intent("infosecurity")
    }

    fun categoryRoboticsClick(view: View){
        intent("robotics")
    }

    private fun intent(state:String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("state",state)
        startActivity(intent)
        finish()
    }
    fun upLoad(view: View){
        val intent = Intent(this, ProjectUploadActivity::class.java)
        startActivity(intent)
    }
}
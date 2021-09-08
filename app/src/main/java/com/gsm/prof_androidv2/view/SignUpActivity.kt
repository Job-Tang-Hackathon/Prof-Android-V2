package com.gsm.prof_androidv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivitySignInBinding
import com.gsm.prof_androidv2.viewmodel.SignInViewModel

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
}
package com.gsm.prof_androidv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivitySignUpBinding
import com.gsm.prof_androidv2.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.gsm.prof_androidv2.databinding.ActivitySignInBinding
import com.gsm.prof_androidv2.viewmodel.SignInViewModel

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val TAG = "SignUp"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activity = this
        observeViewModel()
    }

    private fun observeViewModel() {
        signUpViewModel.signUpResponse.observe(this, Observer {
            binding.loading.visibility = View.GONE
            when (it) {
                1 -> Toast.makeText(this, "회원가입에 성공했습니다!", Toast.LENGTH_SHORT).show()
                0, 2 -> Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun createAccount(view: View) {
        if (!TextUtils.isEmpty(binding.email.text.toString()) && !TextUtils.isEmpty(binding.password.text.toString()) && !TextUtils.isEmpty(binding.passwordCheck.text.toString())) {
            if (useRegex(binding.email.text.toString())) {
                if(binding.password.text.toString().length >=6){
                    if (checkPasswordSame(binding.password.text.toString(), binding.passwordCheck.text.toString())){
                        binding.loading.visibility = View.VISIBLE
                        signUpViewModel.signUp(binding.email.text.toString(), binding.password.text.toString())
                    }
                    else Toast.makeText(this, "재확인 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()

                }else Toast.makeText(this, "비밀번호를 6자 이상 작성해 주세요", Toast.LENGTH_SHORT).show()

            }else Toast.makeText(this, "학교 계정을 사용하세요", Toast.LENGTH_SHORT).show()

        } else Toast.makeText(this, "필수 항목을 입력해 주세요", Toast.LENGTH_SHORT).show()

    }

    //비밀번호 재확인 비밀번호와 같은지 확인
    private fun checkPasswordSame(password: String, passwordCheck: String) =
        password == passwordCheck

    //이메일 정규식 필터
    fun useRegex(input: String): Boolean {
        val regex = Regex(
            pattern = "^[a-zA-Z][0-9][0-9][0-9][0-9][0-9]+@[a-zA-Z]sm\\.hs\\.kr\$",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        return regex.matches(input)
    }

}
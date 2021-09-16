package com.gsm.prof_androidv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivitySignInBinding
import com.gsm.prof_androidv2.utils.Utils.default_web_client_id
import com.gsm.prof_androidv2.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val signInViewModel by viewModels<SignInViewModel>()
    private val TAG = "SignIn"
    private val GOOGLE_REQUEST_CODE = 99
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activity = this
        initGoogleLogin()
        observeViewModel()
    }

    fun googleLoginBtnClick(view: View){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
    }

    fun loginBtnClick(view: View){
        binding.progressBar.visibility = View.VISIBLE
        Log.d(TAG,"email : ${binding.email.text.toString()}, password : ${binding.password.text.toString()}")
        signInViewModel.signIn(binding.email.text.toString(),binding.password.text.toString())
    }

    fun signUpBtnClick(view: View){
        binding.progressBar.visibility = View.GONE
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun initGoogleLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(default_web_client_id)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    private fun observeViewModel(){
        signInViewModel.signInResponse.observe(this, Observer {
            when(it){
                0 -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "이메일 또는 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this,"로그인에 성공하였습니다",Toast.LENGTH_SHORT).show()
                    loginSuccess()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "구글 로그인 성공", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    loginSuccess()
                } else {
                    Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginSuccess() {
        binding.progressBar.visibility = View.VISIBLE
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
        finish()
    }
}
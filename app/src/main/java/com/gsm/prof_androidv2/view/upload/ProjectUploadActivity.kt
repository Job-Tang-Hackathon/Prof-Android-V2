package com.gsm.prof_androidv2.view.upload

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivityProjectUploadBinding
import com.gsm.prof_androidv2.utils.showHorizontal
import com.gsm.prof_androidv2.viewmodel.ActionType
import com.gsm.prof_androidv2.viewmodel.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.storage.StorageReference
import com.gsm.prof_androidv2.utils.Utils.storageURI


@AndroidEntryPoint
class ProjectUploadActivity : AppCompatActivity() {
    val TAG: String = "로그"
    private var tag = ""
    lateinit var binding: ActivityProjectUploadBinding

    private val viewModel by viewModels<UploadViewModel>()
    lateinit var adapter: UploadAdapter

    private val auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid


    companion object {
        var photoIndex: Int = 0
    }

    override fun onStart() {
        super.onStart()
        viewModel.photocnt.observe(this, Observer {
            binding.choicePhotoRecyclerView.showHorizontal(this)
            binding.choicePhotoRecyclerView.adapter = UploadAdapter(viewModel)
            photoIndex = it

        })
        observerData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_upload)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_upload)
        setSpinnerTag()
        setupSpinnerHandler()
        initStartView()
    }

    fun setupSpinnerHandler() {
        binding.uploadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var state = "android"
                when (position) {
                    0 -> {
                        tag = "안드로이드"
                        state = "android"
                    }
                    1 -> {
                        tag = "IOS"
                        state = "ios"
                    }
                    2 -> {
                        tag = "프론트엔드"
                        state = "frontend"
                    }
                    3 -> {
                        tag = "백엔드"
                        state = "backend"
                    }
                    4 -> {
                        tag = "AI"
                        state = "ai"
                    }
                    5 -> {
                        tag = "게임개발"
                        state = "game"
                    }
                    6 -> {
                        tag = "IOT"
                        state = "iot"
                    }
                    7 -> {
                        tag = "정보보안"
                        state = "infosecurity"
                    }
                    8 -> {
                        tag = "로봇틱스"
                        state = "robotics"
                    }
                    else -> {
                        tag = "기타"
                        state = "etc"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun initStartView() {
        binding.viewmodel = viewModel
        binding.activity = this@ProjectUploadActivity
        binding.lifecycleOwner = this

        binding.choicePhotoRecyclerView.showHorizontal(this)
        binding.choicePhotoRecyclerView.adapter = UploadAdapter(viewModel)
        //리사이클러뷰 item간 사이 조절
        binding.choicePhotoRecyclerView.addItemDecoration(HorizontalItemDecorator(20))
    }


    fun uploadBtn() {
        imgUpLoad()
        finish()
    }

    fun observerData() {
        viewModel.imgs.observe(this, Observer {
            binding.choicePhotoRecyclerView.adapter = UploadAdapter(viewModel)
            adapter = binding.choicePhotoRecyclerView.adapter as UploadAdapter

        })
    }

    fun goAlbum() {
        if (photoIndex >= 5) {
            Toast.makeText(this, "사진은 최대 5장까지만 선택이 가능합니다.", Toast.LENGTH_SHORT).show()
        } else if (photoIndex < 5) {

            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 200)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            when (resultCode) {
                RESULT_OK -> {
                    data?.let {
                        when {
                            it.data != null -> viewModel.setImg(it.data!!)
                            it.clipData != null -> {
                                val clip = it.clipData
                                val size = clip?.itemCount!!

                                if (size > 5 || photoIndex + size > 5) {
                                    Toast.makeText(this, "사진은 최대 5장까지 가능합니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    return
                                }
                                for (i in 0 until size) {
                                    val item = clip.getItemAt(i).uri
                                    viewModel.setImg(item)
                                    viewModel.photoCount(ActionType.PLUS, 1)

                                }


                            }
                        }
                    }

                }
            }

        }

    }

    fun imgUpLoad() {
        val formatter = SimpleDateFormat("yyyyMMHH_mmss")
        Log.d("로그", "값 : ${viewModel.photocnt.value}")
        for (i in 0 until photoIndex - 1) {
            viewModel.cnt.observe(this, Observer {
                if (uid != null && photoIndex != 0) {
                    viewModel.progress()
                    viewModel.imgUpLoad(formatter, uid,title = binding.postTitle.text.toString(),
                        fullLine = binding.postFull.text.toString(),
                        oneLine = binding.postContents.text.toString(),
                        people = binding.postPeople.text.toString(),
                        tag = binding.postTag.text.toString(),
                        link = binding.postGithub.text.toString(),
                        state = binding.postState.text.toString(),)
                }
                Log.d(TAG, "imgUpLoadActivity: $it")
            })
        }

        viewModel.loadingToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                viewModel.loadingText.observe(this, Observer {
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                })
            }
        })


    }



    fun backBtn() {
        finish()
    }

    fun setSpinnerTag() {
        binding.uploadSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.UploadListItem,
            R.layout.main_spinner_item
        )
    }


}
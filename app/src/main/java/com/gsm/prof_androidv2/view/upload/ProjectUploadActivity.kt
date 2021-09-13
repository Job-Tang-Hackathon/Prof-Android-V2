package com.gsm.prof_androidv2.view.upload

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivityProjectUploadBinding

class ProjectUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityProjectUploadBinding
    private val list = ArrayList<Uri>()
    private val adapter = UploadAdapter(list, this)

    private val lmg: RecyclerView.LayoutManager =
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_upload)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_upload)
        initStartView()
        initFirebase()


    }


    fun initStartView() {
        binding.lifecycleOwner = this
        binding.activity = this@ProjectUploadActivity

        binding.choicePhotoRecyclerView.layoutManager = lmg
        binding.choicePhotoRecyclerView.adapter = adapter
        //리사이클러뷰 item간 사이 조절
        binding.choicePhotoRecyclerView.addItemDecoration(HorizontalItemDecorator(20))
    }

    fun initFirebase() {
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }


    fun goAlbum() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 200)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 200) {

            if (data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = data.clipData!!.itemCount
                if (count > 5) {
                    Toast.makeText(this, "5장까지만 선택이 가능합니다.", Toast.LENGTH_SHORT).show()
                    return
                }
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }

            } else { // 단일 선택
                data?.data?.let {
                    val imageUri: Uri? = data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

}
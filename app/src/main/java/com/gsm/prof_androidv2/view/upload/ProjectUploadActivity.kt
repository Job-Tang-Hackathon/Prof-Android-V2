package com.gsm.prof_androidv2.view.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsm.prof_androidv2.R

class ProjectUploadActivity : AppCompatActivity() {
    val lmg : RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_upload)
    }
}
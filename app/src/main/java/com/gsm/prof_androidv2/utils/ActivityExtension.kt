package com.gsm.prof_androidv2.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//RecyclerView Vertical
fun RecyclerView.showVertical(context: Context){
    this.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}
package com.gsm.prof_androidv2.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GetPostBindingAdapter {

    //프로젝트 제목
    @BindingAdapter("app:title")
    @JvmStatic
    fun getPostTitle(text: TextView, title: String) {
        text.text = title
    }

    //프로젝트 한줄 설명
    @BindingAdapter("app:oneLineExplanation")
    @JvmStatic
    fun getPostOneLineExplanation(text: TextView, oneLineExplanation: String) {
        text.text = oneLineExplanation
    }

    //프로젝트 태그
    @BindingAdapter("app:postTag")
    @JvmStatic
    fun getPostTag(text: TextView, tag: String) {
        text.text = tag
    }

    //프로젝트 현재 상태 예) 진행중, 완성 등
    @BindingAdapter("app:state")
    @JvmStatic
    fun getPostState(text: TextView, state: String) {
        text.text = state
    }


}
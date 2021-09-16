package com.gsm.prof_androidv2.view.upload

import android.view.View
import androidx.databinding.BindingAdapter

object UploadBindingAdapter {
    @BindingAdapter("visible")
    @JvmStatic
    fun visible(view:View,isVisible:Boolean){
        view.visibility = if(isVisible){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}
package com.gsm.prof_androidv2.bindingadapter

import android.annotation.SuppressLint
import android.provider.Settings.Global.getString
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gsm.prof_androidv2.R

class ViewBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("peopleText")
        fun peopleText(text: TextView, people: String) {

            val string = "참여한 사람들: $people"
            text.text = string


        }
    }
}
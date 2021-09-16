package com.gsm.prof_androidv2.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetategoryPostDto(
    val fullExplanation: String = "",
    val oneLineExplanation: String = "",
    val people: String = "",
    val photo: ArrayList<String>,
    val state: String = "",
    val tag: String = "",
    val title: String = "",
    val link: String = ""
) : Parcelable
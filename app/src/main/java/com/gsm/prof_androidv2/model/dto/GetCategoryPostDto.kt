package com.gsm.prof_androidv2.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetCategoryPostDto(
    val fullExplanation: String = "",
    val oneLineExplanation: String = "",
    val people: String = "",
    val photo: String = "",
    val state: String = "",
    val tag: String = "",
    val title: String = "",
    val link: String = ""
) : Parcelable
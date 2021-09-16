package com.gsm.prof_androidv2.model.dto

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class UploadDto(
    val fullExplanation: String = "",
    val oneLineExplanation: String = "",
    val people: String ="",
    var photo: List<Uri>,
    val state: String ="",
    val tag: String ="",
    val title: String ="",
    val link: String =""
):Parcelable
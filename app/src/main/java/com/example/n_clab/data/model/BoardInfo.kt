package com.example.n_clab.data.model

import com.google.gson.annotations.SerializedName

data class BoardInfo(
    @SerializedName("boardNo") val boardNo: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("regUser") val regUser: String,
    @SerializedName("regDate") val regDate: String,
    @SerializedName("updUser") val updUser: String,
    @SerializedName("updDate") val updDate: String,
    @SerializedName("status") val status: String,
    @SerializedName("viewCnt") val viewCnt: Int

)
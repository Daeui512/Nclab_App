package com.example.n_clab.data.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("empNo")
    val empNo: String,

    @SerializedName("empName")
    val empName: String,

    @SerializedName("mobile")
    val mobile: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("hireDate")
    val hireDate: String,

    @SerializedName("yearCnt")
    val yearCnt: String,

    @SerializedName("registerDate")
    val registerDate: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("notes")
    val notes: String,

    @SerializedName("projId")
    val projId: String,

    @SerializedName("roleId")
    val roleId: String

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑
)
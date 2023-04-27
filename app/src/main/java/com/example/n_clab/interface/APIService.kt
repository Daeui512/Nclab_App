package com.example.n_clab.`interface`

import com.example.n_clab.data.model.UserInfo
import retrofit2.Call
import retrofit2.http.*
import java.util.Objects

public interface APIService {

    //@GET("posts/1")
    //fun getUser(): Call<TestUserModel>

    @FormUrlEncoded
    @HTTP(method = "POST", path = "auth/login")
    fun login(@Field("userId") userId: String, @Field("password") password: String): Call<UserInfo>

    @Headers("Content-Type:application/json; charset=UTF-8")
    @HTTP(method = "POST", path = "users/signUp", hasBody = true)
    fun signUp(@Body userInfo: UserInfo): Call<Void>

    @HTTP(method = "PUT", path = "users/upd", hasBody = true)
    fun updateUser(@Body userInfo: UserInfo): Call<Void>

    @HTTP(method = "DELETE", path="users/del",hasBody = true)
    fun deleteUser(@Body userInfo: UserInfo): Call<Void>
}

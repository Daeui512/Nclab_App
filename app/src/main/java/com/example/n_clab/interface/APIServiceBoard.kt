package com.example.n_clab.`interface`

import com.example.n_clab.ui.board.NotificationBody
import com.example.n_clab.data.model.BoardInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIServiceBoard {

    @GET("/boards/getBoardList")
    @Headers("accept: application/json", "content-type: application/json")
    fun getBoardList(): Call<List<BoardInfo>>

    @POST("/boards/getBoard")
    @Headers("accept: application/json", "content-type: application/json")
    fun getBoard(@Body jsonparams: BoardInfo): Call<List<BoardInfo>>

    @POST("/boards/insertBoard")
    @Headers("accept: application/json", "content-type: application/json")
    fun insertBoard(@Body jsonparams: BoardInfo): Call<String>

    @POST("/boards/updateBoard")
    @Headers("accept: application/json", "content-type: application/json")
    fun updateBoard(@Body jsonparams: BoardInfo): Call<String>

    @POST("/boards/deleteBoard")
    @Headers("accept: application/json", "content-type: application/json")
    fun deleteBoard(@Body jsonparams: BoardInfo): Call<String>

    @POST("/boards/deleteBoard01")
    @Headers("accept: application/json", "content-type: application/json")
    fun deleteBoard01(@Body jsonparams: BoardInfo): Call<String>

    @POST("/boards/updateViewCnt")
    @Headers("accept: application/json", "content-type: application/json")
    fun updateViewCnt(@Body jsonparams: BoardInfo): Call<String>

    @POST("fcm/send")
    suspend fun sendNotification(
        @Body notification: NotificationBody
    ) : Response<ResponseBody>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
        const val FCM_URL = "https://fcm.googleapis.com"

        fun create(): APIServiceBoard {

            val gson : Gson =   GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIServiceBoard::class.java)
        }
    }
}
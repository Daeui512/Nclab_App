package com.example.n_clab.ui.board

import com.example.n_clab.`interface`.APIServiceBoard
import com.example.n_clab.`interface`.APIServiceBoard.Companion.FCM_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(FCM_URL)
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : APIServiceBoard by lazy {
        retrofit.create(APIServiceBoard::class.java)
    }

    // Client
    private fun provideOkHttpClient(
        interceptor: AppInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .run {
            addInterceptor(interceptor)
            build()
        }

    // 헤더 추가
    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain)
                : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "key=BHUhWWVQfRbtf-iwQGxucng3zCdH2H5LtvE9COzhUqMBfJKGGpB85J6kntOwGm85bRiSlCTq2pGz6F0kSsTzX1U")
                .addHeader("Authorization", "key=$")
                .addHeader("Content-Type", "application/json")
                .build()
            proceed(newRequest)
        }
    }
}
package com.example.n_clab.`interface`

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APICient {
    // 안드로이드 디바이스의 경우 실행 환경이 다르기 때문에 르프백 주소(127.0.0.1)로 접근하는건 말이 안된다.
    // 안드로이드는 가상 라우터와 방화벽 뒤에서 네트워크가 동작하기 때문에 127.0.0.1 이라는 건 에뮬레이터를 실행하는 컴퓨터가 아니라 에뮬레이터 자신이 되어버린다
    // 따라서 불변의 접근 가능한 주소를 제공해줘야 한다.
    // 10.0.2.2 또는 10.0.3.2
    private const val BASE_URL: String = "http://10.0.2.2:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
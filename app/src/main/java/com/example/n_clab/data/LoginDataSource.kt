package com.example.n_clab.data

import android.util.Log
import com.example.n_clab.data.model.LoggedInUser
import com.example.n_clab.data.model.UserInfo
import com.example.n_clab.`interface`.APICient
import com.example.n_clab.`interface`.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            val apiService = APICient.create(APIService::class.java)

            val response = apiService.login(username, password).enqueue(object : Callback<UserInfo> {
                        override fun onResponse( call: Call<UserInfo>, response: Response<UserInfo>) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성공된 경우
                                var result: UserInfo? = response.body()
                                Log.d("Test", "onResponse 성공 : " + result?.toString())
                            } else {
                                // 통신이 실패한 경우(응답코드 3XX, 4XX 등등)
                                Log.d("Test", "onResponse 실패")
                            }
                        }

                        override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
            })
            print("response ::: " + response)

            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Hong")
            return Result.Success(fakeUser)

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
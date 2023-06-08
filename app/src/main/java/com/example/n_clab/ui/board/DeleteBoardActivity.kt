package com.example.n_clab.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.n_clab.R
import com.example.n_clab.data.model.BoardInfo
import com.example.n_clab.`interface`.APIServiceBoard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeleteBoardActivity: AppCompatActivity() {
    private val api = APIServiceBoard.create()
    private val mainActivity = MainActivity()
    private val boardListActivity = BoardListActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val boardNo = intent.getStringExtra("boardNo")

        val board = BoardInfo(boardNo = boardNo!!.toInt(), title = "", content = "", regUser = "", regDate = "", updUser = "", updDate = "", status = "", viewCnt = 0)

        val intent = Intent(this, BoardDetailActivity::class.java)

        api.deleteBoard01(board).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("log",response.toString())
                Log.d("log", response.body().toString())

                BoardListActivity.getBoardList(mutableListOf<BoardListItem>())
                setContentView(R.layout.board_list)
                finish()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // 실패
                Log.d("log",t.message.toString())
                Log.d("log","fail")

                BoardListActivity.getBoardList(mutableListOf<BoardListItem>())
                setContentView(R.layout.board_list)
                finish()
            }
        })

    }

}
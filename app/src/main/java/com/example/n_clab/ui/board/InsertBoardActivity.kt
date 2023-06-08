package com.example.n_clab.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.n_clab.R
import com.example.n_clab.data.model.BoardInfo
import com.example.n_clab.`interface`.APIServiceBoard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InsertBoardActivity: AppCompatActivity() {
    private val api = APIServiceBoard.create()
    private val mainActivity = MainActivity()
    private val boardListActivity = BoardListActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insert_board)

        val backBtn = findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener { view: View ->
            onBackPressed()
        }

        val insertBtn = findViewById<Button>(R.id.insert_btn)
        insertBtn.setOnClickListener { view: View ->
            val title = findViewById<EditText>(R.id.title).text.toString()
            val content = findViewById<EditText>(R.id.content).text.toString()
            val regUser = "사용자"

            Log.d("파라미터", title)
            Log.d("파라미터", content)

            val board = BoardInfo(boardNo = 0, title = title, content = content, regUser = regUser, regDate = "", updUser = "", updDate = "", status = "", viewCnt = 0)

            val intent = Intent(this, BoardListActivity::class.java)

            api.insertBoard(board).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("log",response.toString())
                    Log.d("log", response.body().toString())

                    startActivity(intent)
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

}
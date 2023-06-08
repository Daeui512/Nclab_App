package com.example.n_clab.ui.board

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.n_clab.R
import com.example.n_clab.data.model.BoardInfo
import com.example.n_clab.`interface`.APIServiceBoard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateBoardActivity: AppCompatActivity() {
    private val api = APIServiceBoard.create()
    private val mainActivity = MainActivity()
    private val boardListActivity = BoardListActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_board)

        val boardNo = intent.getStringExtra("boardNo")
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        findViewById<EditText>(R.id.title).setText(title)
        findViewById<EditText>(R.id.content).setText(content)

        val backBtn = findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener { view: View ->
            val intent = Intent(this, BoardDetailActivity::class.java)
            intent.putExtra("boardNo", boardNo)
            startActivity(intent)
        }

        val updateBtn = findViewById<Button>(R.id.update_btn)
        updateBtn.setOnClickListener { view: View ->
            val title = findViewById<EditText>(R.id.title).text.toString()
            val content = findViewById<EditText>(R.id.content).text.toString()
            val regUser = "사용자"

            Log.d("파라미터", title)
            Log.d("파라미터", content)

            val board = BoardInfo(boardNo = boardNo!!.toInt(), title = title, content = content, regUser = regUser, regDate = "", updUser = "", updDate = "", status = "", viewCnt = 0)

            val intent = Intent(this, BoardDetailActivity::class.java)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("수정")
                .setMessage("수정하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        api.updateBoard(board).enqueue(object : Callback<String> {
                            override fun onResponse(call: Call<String>, response: Response<String>) {
                                Log.d("log",response.toString())
                                Log.d("log", response.body().toString())

                                intent.putExtra("boardNo", boardNo)
                                startActivity(intent)
                                Toast.makeText(this@UpdateBoardActivity, "수정 완료", Toast.LENGTH_SHORT).show()
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
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            builder.show()


        }

    }

}
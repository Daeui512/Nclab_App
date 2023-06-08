package com.example.n_clab.ui.board

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.n_clab.R
import com.example.n_clab.data.model.BoardInfo
import com.example.n_clab.`interface`.APIServiceBoard
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardDetailActivity: AppCompatActivity() {
    private val api = APIServiceBoard.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_detail)

        val boardNo = intent.getStringExtra("boardNo")
        var title = "제목"
        var content = "내용"

        val board = BoardInfo(boardNo = boardNo!!.toInt(), title = "", content = "", regUser = "", regDate = "", updUser = "", updDate = "", status = "", viewCnt = 0)

        api.getBoard(board).enqueue(object : Callback<List<BoardInfo>> {
            // 전송 성공
            override fun onResponse(call: Call<List<BoardInfo>>, response: Response<List<BoardInfo>>) {
                Log.d("태그", "response : ${response.body()}")
                Log.d("태그: 에러바디", "response : ${response.errorBody()}")
                Log.d("태그: 메시지", "response : ${response.message()}")
                Log.d("태그: 코드", "response : ${response.code()}")
                Log.d("태그: 오호라", "response : ${response.body().toString()}")

                val makeGson = GsonBuilder().create()
                var listType: TypeToken<List<BoardInfo>> = object : TypeToken<List<BoardInfo>>() {}
                var jsonArray = JSONArray(makeGson.toJson(response.body(), listType.type))

                // 제이슨으로 변환
                var jsonObject = jsonArray.getJSONObject(0)
                Log.d("태그", "jsonArray : ${jsonObject.toString()}")

                title = jsonObject.getString("title")
                content = jsonObject.getString("content")

                findViewById<TextView>(R.id.title).text = title
                findViewById<TextView>(R.id.content).text = content
                findViewById<TextView>(R.id.reg_user).text = jsonObject.getString("regUser")
                findViewById<TextView>(R.id.reg_date).text = jsonObject.getString("regDate")
                findViewById<TextView>(R.id.view_cnt).text = "조회수 " + jsonObject.getInt("viewCnt")
            }

            // 전송 실패
            override fun onFailure(call: Call<List<BoardInfo>>, t: Throwable) {
                Log.d("태그", t.message!!)

                BoardListActivity.getBoardList(mutableListOf<BoardListItem>())
                setContentView(R.layout.board_list)
                finish()
            }
        })

        val backBtn = findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener { view: View ->
            BoardListActivity.getBoardList(mutableListOf<BoardListItem>())
            setContentView(R.layout.board_list)
            finish()
        }

        val menuBtn = findViewById<Button>(R.id.menu_btn)
        menuBtn.setOnClickListener {
            var popupMenu = PopupMenu(this, it)

            menuInflater.inflate(R.menu.popup, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.go_update_board_btn -> {
                        val intent = Intent(this, UpdateBoardActivity::class.java)
                        intent.putExtra("boardNo", boardNo)
                        intent.putExtra("title", title)
                        intent.putExtra("content", content)
                        startActivity(intent)
                        finish()
                    }
                    R.id.go_delete_board_btn -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("삭제")
                            .setMessage("삭제하시겠습니까?")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                    val intent = Intent(this, DeleteBoardActivity::class.java)
                                    intent.putExtra("boardNo", boardNo)
                                    startActivity(intent)
                                    finish()
                                })
                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, id ->

                                })
                        builder.show()
                    }
                    R.id.go_send_message_btn -> {
                        val intent = Intent(this, MessageTargetListActivity::class.java)
                        intent.putExtra("boardNo", boardNo)
                        intent.putExtra("title", title)
                        intent.putExtra("content", content)
                        startActivity(intent)
                    }
                }
                false
            }
            popupMenu.show()
        }

    }

}
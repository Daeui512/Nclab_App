package com.example.n_clab.ui.board

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.n_clab.R
import com.example.n_clab.data.model.BoardInfo
import com.example.n_clab.`interface`.APIServiceBoard
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardListActivity: AppCompatActivity() {
    private val api = APIServiceBoard.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var items = mutableListOf<BoardListItem>()

        items.clear()
        api.getBoardList().enqueue(object : Callback<List<BoardInfo>> {
            // 전송 성공
            override fun onResponse(call: Call<List<BoardInfo>>, response: Response<List<BoardInfo>>) {
                Log.d("태그", "response : ${response.body()}")
                Log.d("태그: 에러바디", "response : ${response.errorBody()}")
                Log.d("태그: 메시지", "response : ${response.message()}")
                Log.d("태그: 코드", "response : ${response.code()}")

                val makeGson = GsonBuilder().create()
                var listType: TypeToken<List<BoardInfo>> = object : TypeToken<List<BoardInfo>>() {}

                // 제이슨으로 변환
                var jsonArray = JSONArray(makeGson.toJson(response.body(), listType.type))
                Log.d("태그", "jsonArray : ${jsonArray.toString()}")

                for (index in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(index)
                    Log.d("태그", "jsonArray : ${jsonObject.toString()}")

                    val boardNo = jsonObject.getString("boardNo")
                    val title = jsonObject.getString("title")
                    val content = jsonObject.getString("content")
                    val regUser = jsonObject.getString("regUser")
                    val regDate = jsonObject.getString("regDate")
                    val viewCnt = jsonObject.getString("viewCnt")

                    items.add(BoardListItem(boardNo, title, content, regUser, regDate, viewCnt))
                }

            }

            // 전송 실패
            override fun onFailure(call: Call<List<BoardInfo>>, t: Throwable) {
                Log.d("태그", t.message!!)
            }
        })

        setContentView(R.layout.board_list)

        val listView = findViewById<ListView>(R.id.listView)
        var adapter = BoardListAdapter(items)

        adapter.notifyDataSetChanged()
        listView.adapter = adapter

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = parent.getItemAtPosition(position) as BoardListItem
            Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()

            val board = BoardInfo(boardNo = item.boardNo!!.toInt(), title = "", content = "", regUser = "", regDate = "", updUser = "", updDate = "", status = "", viewCnt = item.viewCnt!!.toInt())

            val intent = Intent(this, BoardDetailActivity::class.java)

            api.updateViewCnt(board).enqueue(object : Callback<String> {
                // 전송 성공
                override fun onResponse(call1: Call<String>, response1: Response<String>) {
                    Log.d("태그: view_cnt", "view_cnt")

                    intent.putExtra("boardNo", item.boardNo)
                    startActivity(intent)

                }

                // 전송 실패
                override fun onFailure(call1: Call<String>, t1: Throwable) {
                    Log.d("태그", t1.message!!)
                }
            })



            val builder = AlertDialog.Builder(this)
            builder.setTitle("타이틀 입니다.")
                .setMessage("제목 : ${item.title}, $position, $id, ${item.boardNo}")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "확인 클릭"
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->
//                        resultText.text = "취소 클릭"
                    })
            // 다이얼로그를 띄워주기
//            builder.show()
        }

        val regBtn = findViewById<Button>(R.id.go_insert_board_btn)
        regBtn.setOnClickListener { view: View ->
            val intent = Intent(this, InsertBoardActivity::class.java)
            startActivity(intent)
        }

        val swipeRefreshLayout: SwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getBoardList(items)
        }

    }

    fun getBoardList(items: MutableList<BoardListItem>) {
        items.clear()
        val listView = findViewById<ListView>(R.id.listView)
        var adapter = BoardListAdapter(items)

        api.getBoardList().enqueue(object : Callback<List<BoardInfo>> {
            // 전송 성공
            override fun onResponse(call: Call<List<BoardInfo>>, response: Response<List<BoardInfo>>) {
                Log.d("태그", "response : ${response.body()}")
                Log.d("태그: 에러바디", "response : ${response.errorBody()}")
                Log.d("태그: 메시지", "response : ${response.message()}")
                Log.d("태그: 코드", "response : ${response.code()}")

                val makeGson = GsonBuilder().create()
                var listType: TypeToken<List<BoardInfo>> = object : TypeToken<List<BoardInfo>>() {}

                // 제이슨으로 변환
                var jsonArray = JSONArray(makeGson.toJson(response.body(), listType.type))
                Log.d("태그", "jsonArray : ${jsonArray.toString()}")

                for (index in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(index)
                    Log.d("태그", "jsonArray : ${jsonObject.toString()}")

                    val boardNo = jsonObject.getString("boardNo")
                    val title = jsonObject.getString("title")
                    val content = jsonObject.getString("content")
                    val regUser = jsonObject.getString("regUser")
                    val regDate = jsonObject.getString("regDate")
                    val viewCnt = jsonObject.getString("viewCnt")

                    items.add(BoardListItem(boardNo, title, content, regUser, regDate, viewCnt))
                }

                adapter.notifyDataSetChanged()
                listView.adapter = adapter
            }

            // 전송 실패
            override fun onFailure(call: Call<List<BoardInfo>>, t: Throwable) {
                Log.d("태그", t.message!!)
            }
        })
    }

    companion object {
        fun getBoardList(mutableListOf: MutableList<BoardListItem>) {

        }
    }


}
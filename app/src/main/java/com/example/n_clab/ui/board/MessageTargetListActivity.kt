package com.example.n_clab.ui.board

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.activity.viewModels
import com.example.n_clab.R
import com.example.n_clab.`interface`.APIServiceBoard

class MessageTargetListActivity: AppCompatActivity() {
    private val api = APIServiceBoard.create()
    private val mainActivity = MainActivity()
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var items = mutableListOf<UserListItem>()

        items.clear()

        // 유저 리스트 끌어오는 api로 교체해야 함.
//        api.getBoardList().enqueue(object : Callback<List<BoardVO>> {
//            // 전송 성공
//            override fun onResponse(call: Call<List<BoardVO>>, response: Response<List<BoardVO>>) {
//                Log.d("태그", "response : ${response.body()}")
//                Log.d("태그: 에러바디", "response : ${response.errorBody()}")
//                Log.d("태그: 메시지", "response : ${response.message()}")
//                Log.d("태그: 코드", "response : ${response.code()}")
//
//                val makeGson = GsonBuilder().create()
//                var listType: TypeToken<List<BoardVO>> = object : TypeToken<List<BoardVO>>() {}
//
//                // 제이슨으로 변환
//                var jsonArray = JSONArray(makeGson.toJson(response.body(), listType.type))
//                Log.d("태그", "jsonArray : ${jsonArray.toString()}")
//
//                for (index in 0 until jsonArray.length()){
//                    val jsonObject = jsonArray.getJSONObject(index)
//                    Log.d("태그", "jsonArray : ${jsonObject.toString()}")
//
//                    val boardNo = jsonObject.getString("boardNo")
//                    val title = jsonObject.getString("title")
//                    val content = jsonObject.getString("content")
//                    val regUser = jsonObject.getString("regUser")
//                    val regDate = jsonObject.getString("regDate")
//                    val viewCnt = jsonObject.getString("viewCnt")
//
//                    items.add(BoardListItem(boardNo, title, content, regUser, regDate, viewCnt))
//                }
//
//            }
//
//            // 전송 실패
//            override fun onFailure(call: Call<List<BoardVO>>, t: Throwable) {
//                Log.d("태그", t.message!!)
//            }
//        })

        var token = "eKSgbKj5TE67YJj76BKxMz:APA91bGUHAPEeM5jDMOlvzyLiRW5CD8_n-dUVOLK_GT5XKbOd0Bua5oMiujHFEXyn1giz9ZLkfXbhSMZe3QMWPgwIhcId67ED_MkK03AlrWdDjo6upAsrQ2rPF3_CwZguh69Ltfuq8Bc"
        items.add(UserListItem("A1234567", "홍길동", token))
        items.add(UserListItem("A1234567", "홍길동", token))
        items.add(UserListItem("A1234567", "홍길동", token))

        setContentView(R.layout.message_target_list)

        val listView = findViewById<ListView>(R.id.listView)
        var adapter = UserListAdapter(items)

        adapter.notifyDataSetChanged()
        listView.adapter = adapter

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = parent.getItemAtPosition(position) as UserListItem
            Toast.makeText(this, item.empName, Toast.LENGTH_SHORT).show()

        }

        val regBtn = findViewById<Button>(R.id.send_message_btn)
        regBtn.setOnClickListener { view: View ->
            var tokens = "eKSgbKj5TE67YJj76BKxMz:APA91bGUHAPEeM5jDMOlvzyLiRW5CD8_n-dUVOLK_GT5XKbOd0Bua5oMiujHFEXyn1giz9ZLkfXbhSMZe3QMWPgwIhcId67ED_MkK03AlrWdDjo6upAsrQ2rPF3_CwZguh69Ltfuq8Bc"
            var checkBoxes = findViewById<CheckBox>(R.id.emp_name)
            for(index in 0 until checkBoxes.length()) {
                Log.d("태그", "isChecked - $index : ${checkBoxes.isChecked}")
            }
            Log.d("태그", "checkBoxes.length() : ${checkBoxes.length()}")
            Log.d("태그", "checkBoxes : ${checkBoxes}")
//            Log.d("태그", "알림 대상자들 토큰 : $tokens")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("알림 전송")
                .setMessage("명에게 푸시 알림을 전송하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 메세지 세팅
                        val time = System.currentTimeMillis()
                        val message = "Test Message123";

                        // FCM 전송하기
                        val data = NotificationBody.NotificationData(
                            getString(R.string.app_name), "", message
                        )
                        val body = NotificationBody(token,data)
                        firebaseViewModel.sendNotification(body)
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            builder.show()
        }

        val swipeRefreshLayout: SwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            // 유저 리스트 얻어오는 api로 교체해야함
//            getBoardList(items)
        }

        val backBtn = findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener { view: View ->
            BoardListActivity.getBoardList(mutableListOf<BoardListItem>())
            setContentView(R.layout.board_list)
            finish()
        }

    }


}
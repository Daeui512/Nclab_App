package com.example.n_clab.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.n_clab.`interface`.APIServiceBoard
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private val api = APIServiceBoard.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, BoardListActivity::class.java)
        startActivity(intent)
        initFirebase()
    }

    private fun initFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("태그", "token111 : ${task.result.toString()}")
            }
        }
    }

}
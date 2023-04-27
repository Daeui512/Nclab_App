package com.example.n_clab.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import com.example.n_clab.data.model.UserInfo
import com.example.n_clab.databinding.ActivityUserRegistBinding
import com.example.n_clab.`interface`.APICient
import com.example.n_clab.`interface`.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class UserRegistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserRegistBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: LinearLayout
    private val hideHandler = Handler(Looper.myLooper()!!)

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            fullscreenContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            fullscreenContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreenContentControls.visibility = View.VISIBLE
    }
    private var isFullscreen: Boolean = false

    private val hideRunnable = Runnable { hide() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val delayHideTouchListener = View.OnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS)
            }
            MotionEvent.ACTION_UP -> view.performClick()
            else -> {
            }
        }
        false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val empName = binding.empName //text

        val userId = binding.userId //text
        val password = binding.password //text
        val password2 = binding.password2 //text

        // birthDate
        val birth1 = binding.birth1 //text
        val birth2 = binding.birth2 //text
        val birth3 = binding.birth3 //text

        // hireDate
        val hire1 = binding.hire1  //text
        val hire2 = binding.hire2  //text
        val hire3 = binding.hire3  //text

        val email = binding.email   //text
        val gender = binding.gender //text
        val mobile = binding.mobile //text

        val signupbutton = binding.signupbutton // button

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isFullscreen = true

        signupbutton.setOnClickListener {
            val vEmpName = empName.text.toString()
            val vUserId = userId.text.toString()
            val vPassword = password.text.toString()
            val vBirthDate = birth1.text.toString() + birth2.text.toString() + birth3.text.toString()
            val vHireDate = hire1.text.toString() + hire2.text.toString() + hire3.text.toString()
            val vEmail = email.text.toString()
            val vGender = gender.text.toString()
            val vMobile = mobile.text.toString()

            val userInfo = UserInfo("20000", vEmpName, vMobile,vEmail,"", vGender, vBirthDate, vHireDate, "", "","","","" ,"")
            print("userInfo" + userInfo.toString())

            val apiService = APICient.create(APIService::class.java)
/*

            val update = apiService.updateUser(userInfo).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    var result = response.body()
                    if (response.isSuccessful){
                        println("response ::: " + response)
                        Log.d("Test", "onResponse 성공 : " + result?.toString())
                    }else{
                        println("response ::: " + response)
                        Log.d("Test", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

            val delete = apiService.deleteUser(userInfo).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    var result = response.body()
                    if (response.isSuccessful){
                        println("response ::: " + response)
                        Log.d("Test", "onResponse 성공 : " + result?.toString())
                    }else{
                        println("response ::: " + response)
                        Log.d("Test", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
            */
            val response = apiService.signUp(userInfo).enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    var result = response.body()
                    if (response.isSuccessful){
                        println("response ::: " + response)
                        Log.d("Test", "onResponse 성공 : " + result?.toString())
                    }else{
                        println("response ::: " + response)
                        Log.d("Test", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    println("response.Failed")
                }

            })


        }


        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = binding.fullscreenContent
        fullscreenContent.setOnClickListener { toggle() }

        /////////fullscreenContentControls = binding.fullscreenContentControls

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        /////////binding.dummyButton.setOnTouchListener(delayHideTouchListener)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (isFullscreen) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
//        fullscreenContentControls.visibility = View.GONE
  //      isFullscreen = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= 30) {
            fullscreenContent.windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            fullscreenContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        isFullscreen = true

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }
}
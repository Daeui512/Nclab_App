package com.example.n_clab.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.n_clab.data.LoginRepository
import com.example.n_clab.data.Result

import com.example.n_clab.R

/**
 * 로그인 뷰모델 클래스 생성
 * val loginRepository = Login Repository
 * ViewModel() 상속받음
 */
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    /*
     MutableLiveData
     - 애플리케이션의 여러 부분에서 업데이트 및 관찰할 수 있는 관찰가능한 데이터 객체를 생성할수 있도록 안드로이드 아키텍차
     구성요소 라이브러리에서 제공하는 클래스이다.
     - 읽기 및 관찰만 가능하고 수정할 수 없는 일반 LiveData객체와 달리 애플리케이션 내에서 업데이트할수도 있다.
     - 일부 사용자 또는 시스템 이벤트에 대한 응답으로 LiveData 개체의 값을 업데이트해야 하는 시나리오에 유용하다.
     */
    /*
    LiveData
    - 데이터를 보유하고 데이터가 변경될 때마다 모든 관찰자에게 알릴 수 있는 클래스이다. 수명주기를 인식하도록 설계 되었다.
    - 관찰중인 구성요소가 활성 상태일 때만 관찰자에게 알립니다. 따라서 앱에서 UI관련 데이터를 편리하고 효율적으로 관리 할수 있다.
     */
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) : Boolean{
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        var loginRslt = false;

        if (result is Result.Success) {
            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            loginRslt = true
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
            loginRslt = false
        }

        return loginRslt
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // username(id) validation check
    private fun isUserNameValid(username: String): Boolean {
        // 숫자만(아이디는 사번으로 통일하도록)
        return username.matches("^[0-9]*\$".toRegex())
    }

    // 패스워드 validation check
    private fun isPasswordValid(password: String): Boolean {
        // 숫자, 문자, 특수문자 중 2가지 포함(10~20자)
        return password.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{10,20}\$".toRegex())
    }

    /*

    1) 숫자만 : ^[0-9]*$
    2) 영문자만 : ^[a-zA-Z]*$
    3) 한글만 : ^[가-힣]*$
    4) 영어 & 숫자만 : ^[a-zA-Z0-9]*$
    5) E-Mail : ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$
    6) 휴대폰 : ^01(?:0|1|[6-9]) - (?:\d{3}|\d{4}) - \d{4}$
    7) 일반전화 : ^\d{2.3} - \d{3,4} - \d{4}$
    8) 주민등록번호 : \d{6} \- [1-4]\d{6}
    9) IP 주소 : ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3})

     -- 패턴화 시켜서 공통으로 관리하자
    */
}
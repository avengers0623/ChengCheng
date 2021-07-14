package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.login_signup.*

class login_signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup)


        val selfsignup = Intent(this,self_signup::class.java)
        val activitymain = Intent(this,MainActivity::class.java)

        /*button_kakao_login.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                }
            }
        }*/
        //회원가입 창
        TextView_signup.setOnClickListener {
            startActivity(selfsignup)
        }

        // 임시로 로그인 버튼 눌렸을때 메인 화면으로
        login_button.setOnClickListener {
            startActivity(activitymain)
        }

    }
}
package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.login_signup.*

class login_signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup)
        val context: Context = applicationContext

        val selfsignup = Intent(this,self_signup::class.java)
        val activitymain = Intent(this,MainActivity::class.java)
        val socialSignup = Intent(this,social_signup::class.java)


        button_kakao_login.setOnClickListener {
            Toast.makeText(context, "dadsdasdasd", Toast.LENGTH_SHORT).show()
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                    startActivity(socialSignup)
                }
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
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
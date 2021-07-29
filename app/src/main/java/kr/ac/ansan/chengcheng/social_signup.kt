package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.social_signup.*

class social_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_signup)
        val mainPage = Intent(this, kr.ac.ansan.chengcheng.MainActivity::class.java)
        val pP = Intent(this,Privacy_Policy::class.java)
        agreeCb2.setOnClickListener {
            startActivity(pP)
        }
        exit.setOnClickListener {
            // 연결 끊기
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e(TAG, "연결 끊기 실패", error)
                }
                else {
                    Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
        }
        start.setOnClickListener {
            startActivity(mainPage)

        }

    }


}
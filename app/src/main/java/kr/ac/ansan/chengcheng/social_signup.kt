package kr.ac.ansan.chengcheng

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.social_signup.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.database
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.platformFlag
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_name
import kr.ac.ansan.chengcheng.MainActivity.Companion.userId

class social_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_signup)
        val mainPage = Intent(this, kr.ac.ansan.chengcheng.MainActivity::class.java)
        val pP = Intent(this, Privacy_Policy::class.java)
        var social_name_text: EditText? = null
        var social_age_text: EditText? = null
        social_name_text = findViewById(R.id.social_name_text)
        social_age_text = findViewById(R.id.social_age_text)



        agreeCb2.setOnClickListener {
            startActivity(pP)
        }

        start.setOnClickListener {
            //정보 확인
            if (social_name_text.text.isEmpty()) {
                Toast.makeText(this, "별명을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (!social_name_text.text.matches(("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+\$").toRegex())) {
                Toast.makeText(this, "별명을 확인해 주세요(특수문자,띄어쓰기 제외)", Toast.LENGTH_SHORT).show()
            } else {
                if (agreeCb.isChecked && agreeCb2.isChecked) {

                    //별명 DB에 등록 하는 부분
                    social_name = social_name_text.text.toString()

                    if (AuthApiClient.instance.hasToken()) {
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {

                            } else if (user != null) {
                                userId = user.id.toString()
                                nickName = user.kakaoAccount?.profile?.nickname
                            }
                        }
                        platformFlag = "ka"
                    }
                    val firebaseAuth = FirebaseAuth.getInstance()
                    if (firebaseAuth.currentUser?.uid != null) {
                        userId = firebaseAuth.currentUser!!.uid
                        nickName = firebaseAuth.currentUser!!.displayName
                        platformFlag = "go"
                    }

                    database.getReference("User")
                        .child("${platformFlag},${userId},${nickName}")
                        .child("nickName")
                        .setValue(social_name)

                    startActivity(mainPage)
                    finish()

                } else if (agreeCb.isChecked) {
                    Toast.makeText(this, "개인정보 취급방침에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
                } else if (agreeCb2.isChecked) {
                    Toast.makeText(this, "서비스 이용약관에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "약관에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
                }
            }
        }


        All.setOnClickListener {
            if (All.isChecked) {
                agreeCb.isChecked = true
                agreeCb2.isChecked = true
            } else {
                agreeCb.isChecked = false
                agreeCb2.isChecked = false
            }

        }


    }


}






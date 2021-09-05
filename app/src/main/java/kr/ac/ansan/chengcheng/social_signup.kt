package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.self_signup.*
import kotlinx.android.synthetic.main.social_signup.*
import kotlinx.android.synthetic.main.social_signup.All
import kotlinx.android.synthetic.main.social_signup.agreeCb
import kotlinx.android.synthetic.main.social_signup.agreeCb2
import java.util.regex.Pattern

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

        var test = 0
        var test2 = 0



        agreeCb2.setOnClickListener {
            startActivity(pP)
        }

        start.setOnClickListener {
            //정보 확인
            if (social_name_text.text.isEmpty()) {
                Toast.makeText(this, "별명을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if(!social_name_text.text.matches(("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+\$").toRegex())){
                Toast.makeText(this, "별명을 확인해 주세요(특수문자 제외)", Toast.LENGTH_SHORT).show()
            } else {
                if (agreeCb.isChecked && agreeCb2.isChecked) {
                    test = 1
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






package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.self_signup.*
import kotlinx.android.synthetic.main.social_signup.*
import kotlinx.android.synthetic.main.social_signup.All
import kotlinx.android.synthetic.main.social_signup.agreeCb
import kotlinx.android.synthetic.main.social_signup.agreeCb2

class social_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_signup)
        val mainPage = Intent(this, kr.ac.ansan.chengcheng.MainActivity::class.java)
        val pP = Intent(this, Privacy_Policy::class.java)

        agreeCb2.setOnClickListener {
            startActivity(pP)
        }

        start.setOnClickListener {

            if (agreeCb.isChecked && agreeCb2.isChecked) {
                startActivity(mainPage)
            } else if (agreeCb.isChecked) {
                Toast.makeText(this, "개인정보 취급방침에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
            } else if (agreeCb2.isChecked) {
                Toast.makeText(this, "서비스 이용약관에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "약관에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
            }


        }


        All.setOnClickListener {
            if(All.isChecked)
            {
                agreeCb.isChecked = true
                agreeCb2.isChecked = true
            }
            else
            {
                agreeCb.isChecked = false
                agreeCb2.isChecked = false
            }

        }




    }
}






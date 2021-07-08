package kr.ac.ansan.chengcheng

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_signup.*

class login_signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup)

        val selfsignup = Intent(this,self_signup::class.java)
        val activitymain = Intent(this,MainActivity::class.java)

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
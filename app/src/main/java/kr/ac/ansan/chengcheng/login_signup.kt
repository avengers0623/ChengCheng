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
        TextView_signup.setOnClickListener {
            startActivity(selfsignup)
        }

    }
}
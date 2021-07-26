package kr.ac.ansan.chengcheng

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.self_signup.*

class self_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.self_signup)

        val MainActivity = Intent(this, MainActivity::class.java)

        var name : String = social_name_text.text.toString()

        button_self_signup.setOnClickListener {
            startActivity(MainActivity)
        }

    }
}
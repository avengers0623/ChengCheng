package kr.ac.ansan.chengcheng

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.self_signup.*
import kotlinx.android.synthetic.main.self_signup.agreeCb2
import kotlinx.android.synthetic.main.self_signup.social_name_text
import kotlinx.android.synthetic.main.social_signup.*

class self_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.self_signup)

        val MainActivity = Intent(this, MainActivity::class.java)
        val pP = Intent(this,Privacy_Policy::class.java)
        var name : String = social_name_text.text.toString()

        button_self_signup.setOnClickListener {
            startActivity(MainActivity)
        }
        agreeCb2.setOnClickListener {
            startActivity(pP)
        }

    }
}
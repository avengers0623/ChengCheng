package kr.ac.ansan.chengcheng

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.self_signup.*
import kotlinx.android.synthetic.main.self_signup.agreeCb2
import kotlinx.android.synthetic.main.self_signup.social_name_text
import kotlinx.android.synthetic.main.social_signup.*
import kotlinx.android.synthetic.main.self_signup.All as All1
import kotlinx.android.synthetic.main.self_signup.agreeCb as agreeCb1

class self_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.self_signup)

        val MainActivity = Intent(this, MainActivity::class.java)
        val pP = Intent(this,Privacy_Policy::class.java)
        var name : String = social_name_text.text.toString()

        button_self_signup.setOnClickListener {
            if (agreeCb.isChecked && agreeCb2.isChecked) {
                startActivity(MainActivity)
            } else if (agreeCb.isChecked) {
                Toast.makeText(this, "개인정보 취급방침에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
            } else if (agreeCb2.isChecked) {
                Toast.makeText(this, "서비스 이용약관에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "약관에 동의 하셔야 합니다", Toast.LENGTH_SHORT).show()
            }

        }


        agreeCb2.setOnClickListener {
            startActivity(pP)
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
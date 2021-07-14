package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.social_signup.*

class social_signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_signup)
        val mainPage = Intent(this, kr.ac.ansan.chengcheng.MainPage::class.java)


        start.setOnClickListener {
            startActivity(mainPage)

        }



    }


}
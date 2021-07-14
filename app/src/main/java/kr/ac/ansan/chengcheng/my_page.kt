package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.my_page.*

class my_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {

        Toast.makeText(this, "설정창 액티비티 실행", Toast.LENGTH_SHORT).show()
        val loginSignup = Intent(this,login_signup::class.java)


    }
}
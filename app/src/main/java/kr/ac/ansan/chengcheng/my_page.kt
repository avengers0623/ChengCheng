package kr.ac.ansan.chengcheng

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class my_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Toast.makeText(this, "설정창 액티비티 실행", Toast.LENGTH_SHORT).show()
    }
}
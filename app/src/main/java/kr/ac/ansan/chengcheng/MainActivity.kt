package kr.ac.ansan.chengcheng

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //뷰가 화면에 그려질때에
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "메인액티비티 실행", Toast.LENGTH_SHORT).show()




    }
    
    fun test() {
        Toast.makeText(this, "불러옴", Toast.LENGTH_SHORT).show()
    }

}


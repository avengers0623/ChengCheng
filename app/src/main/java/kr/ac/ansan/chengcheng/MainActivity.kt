@file:Suppress("DEPRECATION")

package kr.ac.ansan.chengcheng

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlinx.android.synthetic.main.fragment_home.recycler_list as recycler_list1

class MainActivity : AppCompatActivity() {
    private val items: ArrayList<item> = ArrayList()
    //뷰가 화면에 그려질때에
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "메인액티비티 실행", Toast.LENGTH_SHORT).show()

        initDataset()
        val recyclerView: RecyclerView = recycler_list
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter(this, items)

        recyclerView.adapter = adapter
    }

    private fun initDataset() {
        items.clear()
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
    }
}


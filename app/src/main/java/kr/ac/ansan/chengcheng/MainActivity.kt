package kr.ac.ansan.chengcheng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"


    //데이터를 담을 그릇 즉 배열
    var modelList = ArrayList<MyModel>()
    private lateinit var myRecyclerAdapter: MyRecyclerAdapter
    
    //뷰가 화면에 그려질때에
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"MainActivity - onCreate() called")
        Log.d(TAG,"MainActivity - 반복문 돌리기 전 this.modelList.size : ${this.modelList.size}")

        for(i in 1..10)
        {
            val myModel = MyModel(title = "가평여행 $i",profileImage = "@drawable/ic_person")
            this.modelList.add(myModel)
        }

        Log.d(TAG,"MainActivity - 반복문 돌리고 난후 this.modelList.size : ${this.modelList.size}")
        // 어답터 인스턴스 생성
        myRecyclerAdapter = MyRecyclerAdapter()

        myRecyclerAdapter.submitList(this.modelList)
        // 리사이클러뷰 설정
        recycler_list.apply{
            // 리사이클러뷰 방향 설정
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,false)
            // 어답터 장착
            adapter = myRecyclerAdapter


        }




    }


}


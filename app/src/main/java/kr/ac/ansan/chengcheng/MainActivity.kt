
package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*

import java.util.*


class MainActivity : AppCompatActivity() {
    private val items: ArrayList<items> = ArrayList()
    var ProfileImg:String? = null
    //뷰가 화면에 그려질때에
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addItem = Intent(this,add_item::class.java)
        val mypage = Intent(this,My_page::class.java)
        Toast.makeText(this, "메인액티비티 실행", Toast.LENGTH_SHORT).show()


        initDataset()
        val recyclerView: RecyclerView = recycler_list
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter(this, items)
        recyclerView.setAdapter(adapter)

        recyclerView.setOnClickListener{

                    val dlg = Dialog(this)

                    dlg.start("메인의 내용을 변경할까요?")
        }

        add_button.setOnClickListener {
            startActivity(addItem)
        }

        my_page.setOnClickListener {
            startActivity(mypage)
        }

        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    ContentValues.TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"+
                        "\n성별: ${user.kakaoAccount?.gender}")
               // 프로필사진 변수로 받아오기
                 ProfileImg = user.kakaoAccount?.profile?.thumbnailImageUrl
                // 이미지뷰에 프로필사진 띄우기
                Glide.with(this)
                    .load(ProfileImg)
                    .into(my_page)

            }
        }
        // 검색버튼 눌렀을때 리스트에서 검색하게 해야함
        search.setOnClickListener {
        var search2 = search_box.text


        Log.i("dkad", search2.toString())
        if (search2 == items)
        {
            Toast.makeText(this,"찾았다",Toast.LENGTH_LONG).show()
        }


        }

    }




    private fun initDataset() {
        items.clear()
        items.add(items(R.drawable.iron_man, "가평여행1"))
        items.add(items(R.drawable.iron_man, "가평여행2"))
        items.add(items(R.drawable.iron_man, "가평여행3"))
        items.add(items(R.drawable.iron_man, "가평여행4"))
        items.add(items(R.drawable.iron_man, "가평여행5"))
        items.add(items(R.drawable.iron_man, "가평여행6"))
        items.add(items(R.drawable.iron_man, "가평여행7"))
        items.add(items(R.drawable.iron_man, "가평여행8"))
        items.add(items(R.drawable.iron_man, "가평여행9"))
        items.add(items(R.drawable.iron_man, "가평여행10"))

    }





}


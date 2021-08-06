package kr.ac.ansan.chengcheng

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode.valueOf

import java.util.*


class MainActivity : AppCompatActivity() {
    private val Items: ArrayList<Data_items> = ArrayList()
    private lateinit var database: FirebaseDatabase
    //private var myRef = database?.getReference("User")
    private var listCnt = 0
    private var title: String? = null
    private var preTitle = ""
    private var nickName: String? = null
    private var userId: String? = null
    private var ProfileImg: String? = null

    //뷰가 화면에 그려질때에
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // 화면 세로로 고정 시키기
        val addItem = Intent(this, add_item::class.java)
        val mypage = Intent(this, My_page::class.java)
        Toast.makeText(this, "메인액티비티 실행", Toast.LENGTH_SHORT).show()


       // initDataset()
        val recyclerView: RecyclerView = recycler_list
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter(this, Items)
        recyclerView.adapter = adapter


        //추가버튼눌름
        main_btn_add.setOnClickListener {
            listCnt++
            title= "가평여행${listCnt}"
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()


            //DB연동
            database = FirebaseDatabase.getInstance()
            //val myRef = database.getReference("User")

            Items.add(Data_items(R.drawable.iron_man, title!!))
            Log.d("preTitle", title!!)
            writeUserData(Data_items(), title!!, preTitle)
            adapter?.notifyDataSetChanged()

            //***DB 읽어오기***
            /*myRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Items.clear()
                    for(snapshot: DataSnapshot in dataSnapshot.children){
                        val info: Data_items? = snapshot.getValue(Data_items::class.java)
                        Items.add(info!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainActivity", valueOf(error.toException().toString()).toString());
                }

            })*/
            //DB
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
            } else if (user != null) {
                Log.i(
                    ContentValues.TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                            "\n성별: ${user.kakaoAccount?.gender}" +
                            "\n나이 :${user.kakaoAccount?.ageRange}"
                )
                // 프로필사진 변수로 받아오기
                ProfileImg = user.kakaoAccount?.profile?.thumbnailImageUrl
                userId = user.id.toString()
                nickName = user.kakaoAccount?.profile?.nickname
                // 이미지뷰에 프로필사진 띄우기
                Glide.with(this)
                    .load(ProfileImg)
                    .into(my_page)

            }
        }
        // 검색버튼 눌렀을때 리스트에서 검색하게 해야함
        search.setOnClickListener {
            var search2 = search_box.text
            var title = title

            Toast.makeText(this, "찾은건 $title", Toast.LENGTH_LONG).show()
//            if (search2 == title) {
//                Toast.makeText(this, "찾았다", Toast.LENGTH_LONG).show()
//            }


        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("테스트요", token.toString())
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })




    }

    fun writeUserData(data: Data_items, title: String, preTitle: String){
        if(preTitle == title) {
            Toast.makeText(this, "이미 목록에 있습니다.", Toast.LENGTH_SHORT).show()
        }
        else {
            database.getReference("User").child("${userId!!},${nickName!!}").child("이미지").setValue(data.getImage())
            database.getReference("User").child("${userId!!},${nickName!!}").child("제목${listCnt}").setValue(title)
            this.preTitle = title
        }
    }

    private fun initDataset() {
        Items.clear()
        Items.add(Data_items(R.drawable.iron_man, "가평여행1"))
    }


}


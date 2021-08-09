package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*
import java.math.RoundingMode.valueOf
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var dlg_view: View

    private var key: String? = null

    private var title: String? = null

    private var ProfileImg: String? = null

    private var progressBar: ProgressBar? = null

    companion object {
        val Items: ArrayList<Data_items> = ArrayList()
        var context_main: Context? = null
        lateinit var database: FirebaseDatabase
        var adapter: RecyclerViewAdapter? = null
        var userId: String? = null
        var nickName: String? = null
        var listName: String? = null
        var listCntInt = 0
        var listCnt: String? = null
    }

    //날짜 포맷: SimpleDateFormat("yyyy-mm-dd hh:mm:ss")

    //뷰가 화면에 그려질때에
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // 화면 세로로 고정 시키기
        val addItem = Intent(this, add_item::class.java)
        val mypage = Intent(this, My_page::class.java)
        Toast.makeText(this, "메인액티비티 실행", Toast.LENGTH_SHORT).show()

        context_main = this

        //initDataset() //수동으로 데이터 불러오기

        //리사이클러뷰 연결
        val recyclerView: RecyclerView = recycler_list
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerViewAdapter(this, Items)
        recyclerView.adapter = adapter


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

        //DB연동
        database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User")//.child("${userId},${nickName}")


        //***DB 읽어오기***
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("접근", "함수 진입")
                Items.clear()
                Log.e("접근", "반복문 실행전")

                Log.e(
                    "접근",
                    "키키키:${snapshot.child("${userId},${nickName}").child("titleList").value}"
                )

                if (snapshot.child("${userId},${nickName}").exists()) {
                    Log.d("ReadDB", "계정정보 찾음")
                    for (snapshot: DataSnapshot in snapshot.child("${userId},${nickName}")
                        .child("titleList").children) {
                        Log.e("접근", "키키키(for문내부):${snapshot.child("title").value}")

                        //val info: Data_items? = snapshot.child("title").getValue(Data_items::class.java)
                        val info: String? = snapshot.child("title").value as String?
                        //Log.d("info", info.toString())
                        //val infoString: String? = info!!.getTitle()
                        Items.add(Data_items(info!!))
                        Log.d("ReadDB", "카운트")
                    }

                    //목록개수 가져오는 곳
                    listCnt =
                        snapshot.child("${userId},${nickName}")
                            .child("listCnt").value.toString()
                    Log.e("접근", "성공적(개수가져오고바로의값)${listCnt}")
                    //listCnt = "0"
                    clickAndBinding(listCnt!!.toInt())
                } else {
                    Log.d("ReadDB", "계정정보가 없습니다")
                    clickAndBinding(0)
                }

                adapter!!.notifyDataSetChanged()
                Log.e("접근", "새로고침 완료")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", valueOf(error.toException().toString()).toString())
            }

        })
        ////////////////DB

        Log.e("접근", "성공적(빠져나와서)${listCnt}")


        add_button.setOnClickListener {
            startActivity(addItem)
        }

        my_page.setOnClickListener {
            startActivity(mypage)
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

//        dlg_view = layoutInflater.inflate(R.layout.dialog, null, false)

    }

    //***DB쓰기***
    fun clickAndBinding(listCnt: Int) {
        Log.d("접근", "클릭바인딩함수내:${listCnt}")
        listCntInt = listCnt
        main_btn_add.setOnClickListener {
            listName = listname.text.toString()
            if (listName.isNullOrEmpty()) {
                Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                //title= "가평여행${listCnt}"
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()

                database.getReference("User")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (snapshot: DataSnapshot in snapshot.child("${userId},${nickName}")
                                .child("titleList").children) {
                                val info: String? = snapshot.child("title").value as String?
                                if (info == listName) {
                                    Toast.makeText(context_main, "중복된 리스트(이름)가(이) 있습니다", Toast.LENGTH_SHORT).show()
                                    Log.d("중복", "중복된 리스트(이름)가(이) 있습니다")
                                    return
                                }
                            }
                            setData(listName!!, listCntInt)
                            //목록개수 입력
                            listCntInt++
                            database.getReference("User").child("${userId!!},${nickName!!}")
                                .child("listCnt")
                                .setValue(listCntInt)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            }
        }
    }

    private fun setData(listName: String, listCntInt: Int) {
        //추가하는 부분
        //.push() 랜덤 키값 생성
        Items.add(Data_items(listName))
        database.getReference("User").child("${userId!!},${nickName!!}")
            .child("titleList")
            .push()
            .child("title").setValue(listName)
        Log.d("접근", "리스트추가체크${listCntInt}")
        adapter?.notifyDataSetChanged()

    }

    fun listCounting() {
        //리스트카운팅 일괄 작성
        //근데 일일히 카운팅 하는 것 보다
        //일괄로 작성하는 것이 비효율적 일수도 있음..
    }

    private fun makeKey(): String? { //랜덤 키값 구하는 함수
        key = database.getReference("User").child("${userId!!},${nickName!!}").child("title")
            .push().key.toString()
        return key
    }

//    private fun initDataset() {
//        Items.clear()
//        Items.add(Data_items(R.drawable.iron_man, "가평여행1"))
//    }


}


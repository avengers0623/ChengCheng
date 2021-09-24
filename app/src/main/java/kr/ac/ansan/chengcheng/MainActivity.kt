package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import java.math.RoundingMode.valueOf
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    private lateinit var dlg_view: View
    private var ProfileImg: String? = null
    private var dlgItems: ArrayList<Int> = arrayListOf()

    companion object {
        val Items: ArrayList<Data_items> = ArrayList()
        var context_main: Context? = null
        lateinit var database: FirebaseDatabase
        var adapter: RecyclerViewAdapter? = null
        var userId: String? = null
        var nickName: String? = null
        var listName: String? = null
        var itemBox: MutableSet<Int>? = null //이거 필요없을듯
        var dlgItemsMap: HashMap<Int,ArrayList<Int>> = hashMapOf()
    }

    //날짜 포맷: SimpleDateFormat("yyyy-mm-dd hh:mm:ss")

    //뷰가 화면에 그려질때에
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var kakao = (loginSignup as login_signup).kakao
        //Toast.makeText(this, "테스트$kakao", Toast.LENGTH_SHORT).show()
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // 화면 세로로 고정 시키기
        val addItem = Intent(this, addItem::class.java)
        val mypage = Intent(this, My_page::class.java)
        Toast.makeText(this, "메인액티비티 실행", Toast.LENGTH_SHORT).show()

        context_main = this

        //additem 아이템 배열 초기화
        itemBox = mutableSetOf()



        //**************************************************************
        //리스트 눌렀을때 처음에만 서버에서 불러오고 아이템들 (임시)배열에 저장
        //두번째 눌렀을때 부터는 서버에서 불러오지 않고 배열 이용해서 띄어줌.
        //**************************************************************


        //리사이클러뷰 연결

        val recyclerView: RecyclerView = recycler_list
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerViewAdapter(this, Items)
        recyclerView.adapter = adapter

        adapter?.notifyDataSetChanged()

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

        var cnt = 0
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
                        Log.e("접근", "키키키(for문내부):${snapshot.child("item").value}")

                        //val info: Data_items? = snapshot.child("title").getValue(Data_items::class.java)
                        val info: String? = snapshot.child("title").value as String?
                        //Log.d("info", info.toString())
                        //val infoString: String? = info!!.getTitle()
                        Items.add(Data_items(info!!))
                        dlgItems = snapshot.child("item").value as ArrayList<Int>
                        dlgItemsMap[cnt] = dlgItems
                        cnt++
                        Log.d("DialogTitle", info)
                        Log.d("DialogTitle", dlgItemsMap.toString())
                        Log.d("DialogTitle", dlgItems.javaClass.name)

                        Log.d("ReadDB", "카운트")
                    }

                    /*//목록개수 가져오는 곳
                    listCnt =
                        snapshot.child("${userId},${nickName}")
                            .child("listCnt").value.toString()
                    Log.e("접근", "성공적(개수가져오고바로의값)${listCnt}")
                    //(context_additem as add_item).clickAndBinding(listCnt!!.toInt())*/
                } else {
                    Log.d("ReadDB", "계정정보가 없습니다")
                    //(context_additem as add_item).clickAndBinding(0)
                }

                adapter!!.notifyDataSetChanged()
                Log.e("접근", "새로고침 완료")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", valueOf(error.toException().toString()).toString())
            }

        })
        ////////////////DB

//        Log.e("접근", "성공적(빠져나와서)${listCnt}")


        add_button.setOnClickListener {
            startActivity(addItem)
        }

        my_page.setOnClickListener {
            startActivity(mypage)
        }

    }

    fun listCounting() {
        //리스트카운팅 일괄 작성
        //근데 일일히 카운팅 하는 것 보다
        //일괄로 작성하는 것이 비효율적 일수도 있음..
    }



//    private fun initDataset() {
//        Items.clear()
//        Items.add(Data_items(R.drawable.iron_man, "가평여행1"))
//    }


}


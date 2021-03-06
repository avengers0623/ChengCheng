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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.ansan.chengcheng.Main_Dialog.Companion.itemList
import kr.ac.ansan.chengcheng.Main_Dialog.Companion.itemListName
import java.math.RoundingMode.valueOf
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    private lateinit var dlg_view: View
    private var ProfileImg: String? = null
    private var dlgItems: ArrayList<Int> = arrayListOf()
    private var dlgItemsName : ArrayList<String> = arrayListOf()

    companion object {
        val Items: ArrayList<Data_items> = ArrayList()
        var context_main: Context? = null
        var database = FirebaseDatabase.getInstance()
        var adapter: RecyclerViewAdapter? = null
        var platformFlag: String? = null
        var userId: String? = null
        var nickName: String? = null
        var listName: String? = null
        var age : String? = null
        var social_name: String? = null
        var social_platform : String? = null
        var itemBox: MutableSet<Int>? = null //이거 필요없을듯
        var dlgItemsMap: HashMap<Int, ArrayList<Int>> = hashMapOf()
        var dlgItemNameMap : HashMap<Int, ArrayList<String>> = hashMapOf()

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


        // 사용자 정보 요청 (카카오!!!)
        if(AuthApiClient.instance.hasToken()){
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
                        .into(main_img)

                }
            }
        }


        // 사용자 정보 요청 (구글!!!)
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser?.uid != null) {
            userId = firebaseAuth.currentUser!!.uid
            nickName = firebaseAuth.currentUser!!.displayName
            ProfileImg = firebaseAuth.currentUser!!.photoUrl.toString()
            Log.d("이미지테스트", "$profileImage")
            Glide.with(this)
                .load(profileImage)
                .into(main_img)
        }


        //DB연동
        val myRef = database.getReference("User")//.child("${platformFlag},${userId},${nickName}")
    //platform 값 가져와야함
        var cnt = 0
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("접근", "함수 진입")
                Items.clear()
                Log.e("접근", "반복문 실행전")

                Log.e(
                    "접근",
                    "키키키:${snapshot.child("platform").child("$social_platform").value}"
                )

                if (snapshot.child("platform").child("$social_platform").child("$userId").exists()) {
                    Log.d("ReadDB", "계정정보 찾음")

                    //별명 불러오는 부분
                    listNameSubmit(snapshot)

                    for (snapshot: DataSnapshot in snapshot.child("platform").child("$social_platform")
                        .child("$userId")
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
                        dlgItemsName = snapshot.child("itemName").value as ArrayList<String>
                        dlgItemNameMap[cnt] = dlgItemsName
                        cnt++
                        Log.d("DialogTitle", info)
                        Log.d("DialogTitle", dlgItemsMap.toString())
                        Log.d("DialogTitle", dlgItems.javaClass.name)

                        Log.d("ReadDB", "카운트")
                    }

                    /*//목록개수 가져오는 곳
                    listCnt =
                        snapshot.child("${platformFlag},${userId},${nickName}")
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
            finish()
        }

        main_img.setOnClickListener {
            startActivity(mypage)
        }



    }

    private fun listNameSubmit(snapshot: DataSnapshot) {
        if (social_name != null) { //한번만 실행됨
            var user_nickname = social_name
            listOfNickname1.text = user_nickname
        } else {
            //데이터베이스 값 가져오기
            val nickname = snapshot.child("platform").child("$social_platform").child("$userId").child("nickname").value
            Log.d("nickName", nickname as String)
            listOfNickname1.text = nickname
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

    override fun onResume() {
        super.onResume()
            itemList.clear()
            itemListName.clear()
        Log.d("Listtest4", "${itemList.size} + ${itemListName.size}")
    }

}


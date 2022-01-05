package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_page.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.context_main
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.platformFlag
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_name
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_platform

class My_page : AppCompatActivity(){
    private lateinit var firebaseAuth: FirebaseAuth
    // [END declare_auth]
    private lateinit var googleSignInClient: GoogleSignInClient
    private val mAuth = FirebaseAuth.getInstance()
    private var kakaoImg : String? = null
    private var googleImg : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_page)

        val loginSignup = Intent(this,login_signup::class.java)
        var database = FirebaseDatabase.getInstance()

        //kakao logout
/*        logout.setOnClickListener {
            //카카오 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(ContentValues.TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    startActivity(loginSignup)
                }
            }
        }*/

        // 사용자 정보 요청 (카카오!!!)
        if(AuthApiClient.instance.hasToken()){
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
                } else if (user != null) {
                    // 프로필사진 변수로 받아오기
                    kakaoImg = user.kakaoAccount?.profile?.thumbnailImageUrl
                    // 이미지뷰에 프로필사진 띄우기
                    Glide.with(this)
                        .load(kakaoImg)
                        .into(my_pageImg)

                }
            }
        }

        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser?.uid != null) {
            googleImg = firebaseAuth.currentUser!!.photoUrl.toString()
            Glide.with(this)
                .load(googleImg)
                .into(my_pageImg)
        }

        my_page_Nickname.text = nickName
        when(social_platform){
            "kakao" -> {
                my_page_platform.text = "카카오 로그인 중"
            }
            "google" -> {
                my_page_platform.text = "구글 로그인 중"
            }
        }


        google_logout.setOnClickListener {
            if(FirebaseAuth.getInstance().currentUser?.uid != null){
                signOut()
                val mainActivity : MainActivity = context_main as MainActivity
                Toast.makeText(this,"구글 탈퇴 성공",Toast.LENGTH_LONG).show()
                startActivity(loginSignup)
                mainActivity.finish()
                finish()
            } else {
                Toast.makeText(this, "카카오 로그인중", Toast.LENGTH_SHORT).show()
            }
            
        }
        
        exit.setOnClickListener {
            if(AuthApiClient.instance.hasToken()){
                // 연결 끊기
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "연결 끊기 실패", error)
                    }
                    else {
                        val mainActivity : MainActivity = context_main as MainActivity
                        Log.i(ContentValues.TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        startActivity(loginSignup)
                        mainActivity.finish()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "구글 로그인중", Toast.LENGTH_SHORT).show()
            }
            
        }



        Version.setOnClickListener {
            Version2.text = getString(R.string.app_version) // getString을 붙여줘야함
            Version2.visibility = View.VISIBLE
        }

        QnA.setOnClickListener {
            Version2.text = "avengers0623@gmail.com"
            Version2.visibility = View.VISIBLE
        }

        source.setOnClickListener {
            Version2.text = getString(R.string.sources)
            Version2.visibility = View.VISIBLE
        }






    }
   /// 구글 로그아웃
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
// 구글 탈퇴
    private fun revokeAccess() {
        mAuth.currentUser?.delete()
    }
}
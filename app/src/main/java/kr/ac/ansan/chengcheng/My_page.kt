package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.my_page.*

class My_page : AppCompatActivity(){
    private lateinit var firebaseAuth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_page)

        Toast.makeText(this, "설정창 액티비티 실행", Toast.LENGTH_SHORT).show()
        val loginSignup = Intent(this,login_signup::class.java)

        logout.setOnClickListener {
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
        }

        google_logout.setOnClickListener {
            signOut()
            Toast.makeText(this,"구글 로그아웃 성공",Toast.LENGTH_LONG).show()
            startActivity(loginSignup)

        }
        exit.setOnClickListener {
            // 연결 끊기
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "연결 끊기 실패", error)
                }
                else {
                    Log.i(ContentValues.TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                    startActivity(loginSignup)
                }
            }
        }



        Version.setOnClickListener {
            Version2.text = "버전 정보 : 1.0.0"
            Version2.visibility = View.VISIBLE
        }

        QnA.setOnClickListener {
            Version2.text = "avengers0623@gmail.com"
            Version2.visibility = View.VISIBLE
        }

        source.setOnClickListener {
            Version2.text = "카카오 firebase 네이버"
            Version2.visibility = View.VISIBLE
        }






    }
   /// 구글 로그아웃
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }


}
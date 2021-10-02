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
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.my_page.*

class My_page : AppCompatActivity(){
    private lateinit var firebaseAuth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient
   private val mAuth = FirebaseAuth.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_page)

        Toast.makeText(this, "설정창 액티비티 실행", Toast.LENGTH_SHORT).show()
        val loginSignup = Intent(this,login_signup::class.java)

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

        google_logout.setOnClickListener {
            if(FirebaseAuth.getInstance().currentUser?.uid != null){
                signOut()
                Toast.makeText(this,"구글 탈퇴 성공",Toast.LENGTH_LONG).show()
                startActivity(loginSignup)
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
                        Log.i(ContentValues.TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        startActivity(loginSignup)
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
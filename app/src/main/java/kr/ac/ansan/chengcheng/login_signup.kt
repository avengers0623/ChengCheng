    package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_item.*
import kotlinx.android.synthetic.main.login_signup.*

class login_signup : AppCompatActivity(), View.OnClickListener {
    // [START declare_auth]
    private lateinit var firebaseAuth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 99
    var kakao = 0
    var google = 0
    companion object {
        var loginSignup: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup)
        val context: Context = applicationContext
        loginSignup = this

        val intent = Intent(context, social_signup::class.java)

        val selfsignup = Intent(this, self_signup::class.java)
        val activitymain = Intent(this, MainActivity::class.java)




        Glide.with(this)
            .load("https://img.hankyung.com/photo/202103/BF.25772861.1.jpg")
            .into(logo)


        button_google_login.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            //'R.string.default_web_client_id' 에는 본인의 클라이언트 아이디를 넣어주시면 됩니다.
            //저는 스트링을 따로 빼서 저렇게 사용했지만 스트링을 통째로 넣으셔도 됩니다.
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()

        button_kakao_login.setOnClickListener {
            Toast.makeText(context, "dadsdasdasd", Toast.LENGTH_SHORT).show()
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                    Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                    kakao++
                    //intent = Intent(Intent.ACTION_VIEW,  Uri.parse(KakaoTalkLinkProtocol.TALK_MARKET_URL_PREFIX_2 + makeReferrer()))
                    startActivity(intent)

                }
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }


        }
        //회원가입 창
        TextView_signup.setOnClickListener {
            startActivity(selfsignup)


        }


        // 임시로 로그인 버튼 눌렸을때 메인 화면으로
        login_button.setOnClickListener {
            startActivity(activitymain)
        }
    }

        public override fun onStart() {
            super.onStart()
            val account = GoogleSignIn.getLastSignedInAccount(this)
            /*if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
                toMainActivity(firebaseAuth.currentUser)
            }*/
        } //onStart End

        // onActivityResult
        public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)

                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("LoginActivity", "Google sign in failed", e)
                }
            }
        } // onActivityResult End

        // firebaseAuthWithGoogle
        private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
            val socialSignup = Intent(this, social_signup::class.java)

            Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

            //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                        toMainActivity(firebaseAuth?.currentUser)
                        var google=2
                        startActivity(socialSignup)
                    } else {
                        Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)

                    }

                }
        }// firebaseAuthWithGoogle END


        // toMainActivity
        fun toMainActivity(user: FirebaseUser?) {
            if(user !=null) { // MainActivity 로 이동
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } // toMainActivity End

        // signIn
        private fun signIn() {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        // signIn End






        private fun revokeAccess() { //회원탈퇴
            // Firebase sign out
            firebaseAuth.signOut()
            googleSignInClient.revokeAccess().addOnCompleteListener(this) {

            }
        }














    override fun onClick(v: View?) {

    }


}
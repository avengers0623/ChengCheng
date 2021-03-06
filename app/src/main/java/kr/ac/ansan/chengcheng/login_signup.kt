package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.login_signup.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.database
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.platformFlag
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_platform
import kr.ac.ansan.chengcheng.MainActivity.Companion.userId


class login_signup : AppCompatActivity(), View.OnClickListener {
    // [START declare_auth]
    private lateinit var firebaseAuth: FirebaseAuth

    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient
    private val FB_SIGN_IN = 64206

    private val RC_SIGN_IN = 99
    var kakao = 0
    var google = 0
    private var backpressedTime: Long = 0
    companion object {
        var loginSignup: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup)
        val context: Context = applicationContext
        loginSignup = this

        var ka = resources.getString(R.string.platform_KAKAO)
        var go = resources.getString(R.string.platform_GOOGLE)

        Log.d("www","$ka")
        Log.d("www","$go")
        val socialSignup = Intent(context, social_signup::class.java)

        val activitymain = Intent(this, MainActivity::class.java)

        //?????? ?????????
        button_google_login.setOnClickListener {
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            //'R.string.default_web_client_id' ?????? ????????? ??????????????? ???????????? ??????????????? ?????????.
            //?????? ???????????? ?????? ?????? ????????? ??????????????? ???????????? ????????? ???????????? ?????????.
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth ??????
        firebaseAuth = FirebaseAuth.getInstance()

        //????????? ?????????
        button_kakao_login.setOnClickListener {
            Toast.makeText(context, "dadsdasdasd", Toast.LENGTH_SHORT).show()
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (error != null) {
                    Log.e(TAG, "????????? ??????", error)
                    Toast.makeText(context, "??????", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    Log.i(TAG, "????????? ?????? ${token.accessToken}")
                    kakao++
                    //intent = Intent(Intent.ACTION_VIEW,  Uri.parse(KakaoTalkLinkProtocol.TALK_MARKET_URL_PREFIX_2 + makeReferrer()))
                    //startActivity(intent)
                    userExists(ka, activitymain, socialSignup)
                }
            }

            // ??????????????? ???????????? ????????? ?????????????????? ?????????, ????????? ????????????????????? ?????????
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }


        }

        // ????????? ????????? ?????? ???????????? ?????? ????????????
        login_button.setOnClickListener {
            startActivity(activitymain)
        }
    }

    private fun userExists(platform: String, activitymain: Intent, socialSignup: Intent) {
        Log.d("ddd", "?????????")

        if (platform == "kakao") {
            //kakao
                social_platform = "kakao"
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {

                    } else if (user != null) {
                        val currentUserId = user.id.toString()
                        userCheck(currentUserId, platform, activitymain, socialSignup)
                    }
                }
            }
        } else if (platform == "google") {
            //google
            social_platform = "google"
            val firebaseAuth = FirebaseAuth.getInstance()
            if (firebaseAuth.currentUser?.uid != null) {
                val currentUserId = firebaseAuth.currentUser!!.uid
                userCheck(currentUserId, platform, activitymain, socialSignup)
            }
        } else {
            Toast.makeText(this, "??????: ????????? ???????????????", Toast.LENGTH_SHORT)
            // ?????? ?????????..
        }

    }

    private fun userCheck(
        currentUserId: String,
        platform: String,
        activitymain: Intent,
        socialSignup: Intent
    ) {
        Log.d("ddd", currentUserId)

        val myRef =
            database.getReference("User").child("platform").child(platform).child(currentUserId)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("platformFlag", snapshot.toString())
                if (snapshot.exists()) {
                    startActivity(activitymain)
                    finish()
                } else {
                    startActivity(socialSignup)
                    finish()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        /*if(account!==null){ // ?????? ????????? ??????????????? ?????? ?????? ??????????????? ??????
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
        val activitymain = Intent(this, MainActivity::class.java)
        val socialSignup = Intent(this, social_signup::class.java)


        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount ???????????? ID ????????? ???????????? Firebase Auth??? ???????????? Firebase??? ??????
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle ??????", task.exception)
                    userExists("google", activitymain, socialSignup)
                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle ??????", task.exception)

                }

            }
    }// firebaseAuthWithGoogle END




    // signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // signIn End


    override fun onClick(v: View?) {

    }
// ?????????????????? ?????????????????? ???????????? ?????????????????? ??????????????? **************************************
   /* override fun onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis()
            Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ?????? ?????????.", Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish()
        }
    }*/


}

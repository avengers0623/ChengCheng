package kr.ac.ansan.chengcheng

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.splash.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.platformFlag
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_platform

class SplashActivity : Activity() {
    private var backKeyPressedTime : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)


        val loginSignup = Intent(this, login_signup::class.java)
        val mainActivity = Intent(this, MainActivity::class.java)
        var splashFlag = false

        if (AuthApiClient.instance.hasToken()) {
            social_platform = "kakao"
        }
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser?.uid != null) {
            social_platform = "google"
        }

        if (FirebaseAuth.getInstance().currentUser?.uid != null || AuthApiClient.instance.hasToken()) {
            animationView.setOnClickListener {
                splashFlag = true
                accountAvailable(mainActivity, splashFlag)
            } //-------!!보류!!-------
        } else {
            accountAvailable(loginSignup, splashFlag)
        }
    }

    private fun accountAvailable(intent: Intent, splashFlag: Boolean) {
        Log.d("dddddd","$splashFlag")
        var duration: Long = if(splashFlag) 100 else 3000
        Log.d("dddddd","$duration")

        Handler().postDelayed({
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            overridePendingTransition(anim.fade_in, anim.fade_out)
            finish()
        }, duration)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show()
            return
        }

        // 2초 이내에 뒤로가기 버튼을 한번더 클릭시 Finish()(앱 종료)
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000)
        {
            finish()
        }
    }

//    startActivity(
//        Intent(this, MainActivity::class.java)
//            .setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
//            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//    )

}
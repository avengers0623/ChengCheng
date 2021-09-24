package kr.ac.ansan.chengcheng

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.splash.*

class SplashActivity : Activity() {
    val DURATION: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)


        val loginSignup = Intent(this, login_signup::class.java)
        val mainActivity = Intent(this, MainActivity::class.java)

        if (FirebaseAuth.getInstance().currentUser?.uid != null || AuthApiClient.instance.hasToken()) {
            accountAvailable(mainActivity)
        } else {
            accountAvailable(loginSignup)
        }

        animationView.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser?.uid != null || AuthApiClient.instance.hasToken()) {
                clickAccountAvailable(mainActivity)
            } else {
                clickAccountAvailable(loginSignup)
            }
        }
    }

    private fun accountAvailable(intent: Intent) {
        Handler().postDelayed({
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            overridePendingTransition(anim.fade_in, anim.fade_out)
            finish()
        }, DURATION)
    }

    private fun clickAccountAvailable(intent: Intent) {
        Handler().postDelayed({
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            overridePendingTransition(anim.fade_in, anim.fade_out)
            finish()
        }, 100)
    }
    override fun onBackPressed() {
        // We don't want the splash screen to be interrupted
    }
}
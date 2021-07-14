package kr.ac.ansan.chengcheng

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "33ec8453c4320ad8871dad69d36cd570")
    }
}
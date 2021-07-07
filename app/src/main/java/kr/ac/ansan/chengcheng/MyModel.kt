package kr.ac.ansan.chengcheng

import android.util.Log

class MyModel (var title: String? = null, var profileImage: String? = null){
    val TAG: String = "로그"

        //기본 생성자
    init {
        Log.d(TAG,"MyModel - init() called")
    }

}
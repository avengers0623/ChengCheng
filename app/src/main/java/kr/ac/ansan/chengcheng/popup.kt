package kr.ac.ansan.chengcheng

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import java.security.AccessControlContext

class popup (context: Context) {
    private val dialog = Dialog(context)

    fun myDig() {
        dialog.show()

    }

    fun MyDig() {
        dialog.setContentView(R.layout.custom_dialog)

        //Dialog 크기 설정
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        //다이얼로그 영역 밖을 터치했을때 사라지도록 하는 부분
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
    }
}
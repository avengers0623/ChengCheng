package kr.ac.ansan.chengcheng

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class AddItemDig(context : Context)  {
    private val dlg = Dialog(context) // 부모 액티비티의 context 가 들어감
    private lateinit var itemInput : EditText
    private lateinit var itemFind : TextView
    private lateinit var close : Button
    private lateinit var buttonFind : Button

    fun start(content : String)
    {
       // dlg.requestWindowFeature(Window.FEATURE_NO_TITLE) //타이틀바 제거
        dlg.setContentView(R.layout.add_item_dig) // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false) // 다이얼로그이 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        itemInput = dlg.findViewById(R.id.add_item_dig_item)
        itemFind = dlg.findViewById(R.id.add_item_dig_item_find)
        close = dlg.findViewById(R.id.button_Close)
        buttonFind = dlg.findViewById(R.id.button_find)


        close.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }


}
package kr.ac.ansan.chengcheng

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import kr.ac.ansan.chengcheng.add_item.Companion.context_additem
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.HOUR_OF_DAY

class Alarm_dialog (context : Context) :TimePickerDialog.OnTimeSetListener{
    private val dia = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button

//    private lateinit var listeners : MyDialogOKClickedListeners
    private var hour = 0
    private var minute = 0
    private var savehour = 0
    private var saveminute = 0

    fun start(content : String) {
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dia.setContentView(R.layout.alarm_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dia.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        lblDesc = dia.findViewById(R.id.content)
        lblDesc.text = content

        btnOK = dia.findViewById(R.id.ok)
        btnOK.setOnClickListener {

            getTime()
//            listeners.onOKClicked("확인을 눌렀습니다")
            dia.dismiss()
        }



        btnCancel = dia.findViewById(R.id.cancel)
        btnCancel.setOnClickListener {
            dia.dismiss()
        }

        dia.show()
    }

    private fun getTime(){
        val cal:Calendar=Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
        (context_additem as add_item).getTime(hour, minute)
    }
//Toast는 사용못함
//    fun setOnOKClickedListener(listener: (String) -> Unit) {
//        this.listeners = object: MyDialogOKClickedListeners {
//            override fun onOKClicked(content: String) {
//                listener(content)
//            }
//        }
//    }




    interface MyDialogOKClickedListeners {
        fun onOKClicked(content : String)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savehour = hourOfDay
        saveminute = minute


    }

}

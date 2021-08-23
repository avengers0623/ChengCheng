package kr.ac.ansan.chengcheng

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class User_dialog(context : Context) {
    private val dia = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnChange : Button
    private lateinit var btnCancel : Button
    private lateinit var spinner: Spinner

    private val builder = AlertDialog.Builder(context)
    private val inflater = context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val view = inflater.inflate(R.layout.user_dialog, null)



//    private lateinit var listeners : MyDialogOKClickedListeners

    fun start(context: String, context_display: Context) {
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거


        val spinner = view.findViewById<Spinner>(R.id.spinner)

        var spinner_List = ArrayList<String>()
        spinner_List.add("종류를 선택 하세요")
        spinner_List.add("의류")
        spinner_List.add("전자기기")
        spinner_List.add("세면도구")
        spinner_List.add("여가활동")
        spinner_List.add("차량용품")
        spinner_List.add("기타")



        val array_Adapter = ArrayAdapter<String>(context_display, android.R.layout.simple_spinner_dropdown_item, spinner_List)
        spinner.adapter = array_Adapter
        spinner.setSelection(0)

        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }

        builder.setView(view)

        val dlg = builder.create()
        dlg.setTitle(context)
        dlg.show()
        dlg.setCancelable(false)


        btnOK = dlg.findViewById(R.id.ok)
        btnOK.setOnClickListener {
            dlg.dismiss()

        }




//        dia.setContentView(R.layout.user_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
//        dia.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
//
//
//
//        btnOK = dia.findViewById(R.id.ok)
//        btnOK.setOnClickListener {
//
//
////            listeners.onOKClicked("확인을 눌렀습니다")
//            dia.dismiss()
//        }
//
//
//
//        btnChange = dia.findViewById(R.id.change)
//        btnChange.setOnClickListener {
//            dia.dismiss()
//        }
//
//        btnCancel = dia.findViewById(R.id.cancel)
//        btnCancel.setOnClickListener {
//            dia.dismiss()
//        }
//
//
//
//
//
//
//        dia.show()
    }


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



}

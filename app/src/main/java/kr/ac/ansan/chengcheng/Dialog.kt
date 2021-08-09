package kr.ac.ansan.chengcheng

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.Items
import kr.ac.ansan.chengcheng.MainActivity.Companion.adapter
import kr.ac.ansan.chengcheng.MainActivity.Companion.context_main
import kr.ac.ansan.chengcheng.MainActivity.Companion.database
import kr.ac.ansan.chengcheng.MainActivity.Companion.listCnt
import kr.ac.ansan.chengcheng.MainActivity.Companion.listCntInt
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.userId

class Dialog(context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc: TextView
    private lateinit var btnOK: Button
    private lateinit var btnChange: Button

    companion object {
        //        var context_dlg =
        lateinit var btnDelete: Button
    }

    private lateinit var listener: MyDialogOKClickedListener

    fun start(content: String) {
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lblDesc = dlg.findViewById(R.id.main_dlg_content)
        lblDesc.text = content
        //adapter = context_main?.let { RecyclerViewAdapter(it, Items) }

        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnOK = dlg.findViewById(R.id.ok)
        btnOK.setOnClickListener {

            dlg.dismiss()
        }

        btnChange = dlg.findViewById(R.id.change)
        btnChange.setOnClickListener {
            dlg.dismiss()
        }


        btnDelete = dlg.findViewById(R.id.delete)
        database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User")
        btnDelete.setOnClickListener {
            Log.d("제거", "제거버튼 클릭됨")
            //삭제기능
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("실험", "${userId},${nickName}")
                    Log.d("실험", "${listCnt}, ${listCntInt}")
                    for (snapshot: DataSnapshot in snapshot.child("${userId},${nickName}")
                        .child("titleList").children) {

                        val info: String? = snapshot.child("title").value as String?
                        if (info == content) {
                            Log.d("제거대상 찾음:", info)
                            val key = snapshot.key
                            myRef.child("${userId!!},${nickName!!}")
                                .child("titleList")
                                .child(key!!).setValue(null)

                            Items.remove(Data_items(info))
                            break
                        }
                    }
                    //목록개수 가져오는 곳(카운트 다운)
                    listCnt =
                        snapshot.child("${userId},${nickName}")
                            .child("listCnt").value.toString()
                    listCntInt = listCnt!!.toInt()
                    val listCntResult = listCntInt - 1
                    myRef.child("${userId},${nickName}")
                        .child("listCnt").setValue(listCntResult)
                    (context_main as MainActivity).clickAndBinding(listCntResult)
                    Log.d("제거listCnt:", "성공적:${listCntInt}")
                    adapter!!.notifyDataSetChanged()
                }


                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            dlg.dismiss()
        }
        dlg.show()
    }


    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object : MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(content: String)
    }
}

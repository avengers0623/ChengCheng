package kr.ac.ansan.chengcheng

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.Items
import kr.ac.ansan.chengcheng.MainActivity.Companion.adapter
import kr.ac.ansan.chengcheng.MainActivity.Companion.database
import kr.ac.ansan.chengcheng.MainActivity.Companion.dlgItemNameMap
import kr.ac.ansan.chengcheng.MainActivity.Companion.dlgItemsMap
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.platformFlag
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_platform
import kr.ac.ansan.chengcheng.MainActivity.Companion.userId
import kr.ac.ansan.chengcheng.RecyclerViewAdapter.MyViewHolder.Companion.dlgPosition
import kr.ac.ansan.chengcheng.databinding.DialogBinding

class Main_Dialog(context: Context) : AppCompatActivity() {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc: TextView
    private lateinit var btnOK: Button
    private lateinit var btnDelete: Button
    private lateinit var adapterRV: AdditemRVAdapterDialog
    private lateinit var rvData: MutableList<DialogItems>
    private lateinit var binding : DialogBinding
    private lateinit var listener: MyDialogOKClickedListener

    companion object{
         var itemList : ArrayList<Int> = arrayListOf()
         var itemListName : ArrayList <String> = arrayListOf()
    }
    fun start(content: String) {
//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lblDesc = dlg.findViewById(R.id.main_dlg_content)
        lblDesc.text = content


        //****************************
       // binding = DialogBinding.inflate(layoutInflater)
      //  setContentView(binding.root)
        //**************


        //adapter = context_main?.let { RecyclerViewAdapter(it, Items) }


        //아이템 데이터들 불러오기
        adapterRV = AdditemRVAdapterDialog()
        rvData = mutableListOf()
        var rv = dlg.findViewById<RecyclerView>(R.id.dlg_rv)
        rv.setHasFixedSize(true)
        rv.adapter = adapterRV
        rvData.let {
            adapterRV.data = it
        }
        rv.layoutManager = GridLayoutManager(dlg.context, 5, RecyclerView.VERTICAL, false)


        //DB연동
        database = FirebaseDatabase.getInstance()
        val myRef2 = database.getReference("User")//.child("${platformFlag},${userId},${nickName}")

        val mainActivity = MainActivity()
        Log.d("Dialog", dlgItemsMap.toString())


        dlgItemsMap[dlgPosition]!!.forEach {
            itemList.add(it)
        }

        dlgItemNameMap[dlgPosition]!!.forEach {
            itemListName.add(it)
        }
        Log.d("Listtest", "${itemList[0]} + ${itemListName[0]}")
        Log.d("Listtest2", "${itemList.size} + ${itemListName.size}")

        for (i in itemList.indices)
        {
            rvData.add(DialogItems(itemList[i],itemListName[i]))
        }

        adapterRV.setItemClickListener(object : AdditemRVAdapterDialog.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                Log.d("test333","$position")
            }

        })

        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnOK = dlg.findViewById(R.id.ok)
        btnOK.setOnClickListener {

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
                    Log.d("실험", "${platformFlag},${userId},${nickName}")
                    //Log.d("실험", "${listCnt}, ${listCntInt}")
                    for (snapshot: DataSnapshot in snapshot.child("platform").child("$social_platform")
                        .child("$userId")
                        .child("titleList").children) {
                        val info: String? = snapshot.child("title").value as String?
                        if (info == content) {
                            Log.d("제거대상 찾음:", info)
                            val key = snapshot.key
                            myRef.child("platform").child("$social_platform").child("$userId")
                                .child("titleList")
                                .child(key!!).setValue(null)

                            Items.remove(Data_items(info))
                            break
                        }
                    }
/*                    //목록개수 가져오는 곳(카운트 다운)
                    listCnt =
                        snapshot.child("${platformFlag},${userId},${nickName}")
                            .child("listCnt").value.toString()
                    listCntInt = listCnt!!.toInt()
                    val listCntResult = listCntInt - 1
                    myRef.child("${platformFlag},${userId},${nickName}")
                        .child("listCnt").setValue(listCntResult)
                    (context_main as add_item).clickAndBinding(listCntResult)
                    Log.d("제거listCnt:", "성공적:${listCntInt}")*/
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

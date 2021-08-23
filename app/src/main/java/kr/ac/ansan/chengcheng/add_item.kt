package kr.ac.ansan.chengcheng

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_item.*
import kotlinx.android.synthetic.main.alarm_dialog.*
import kotlinx.android.synthetic.main.item_add.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.Items
import kr.ac.ansan.chengcheng.MainActivity.Companion.adapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kr.ac.ansan.chengcheng.MainActivity.Companion.database
import kr.ac.ansan.chengcheng.MainActivity.Companion.listCnt
import kr.ac.ansan.chengcheng.MainActivity.Companion.listCntInt
import kr.ac.ansan.chengcheng.MainActivity.Companion.listName
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.userId


class add_item :  AppCompatActivity() {

    private val items: ArrayList<Data_addItem> = ArrayList()
    private var rv1Data: MutableList<Data_addItem_1>? = null
//    private val builder = AlertDialog.Builder(this)
//    private val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    private val view = inflater.inflate(R.layout.user_dialog, null)

    //서브 grid뷰
    //private var gridview: GridView? = null

    companion object {
        var context_additem: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)

        context_additem = this

        val mainActivity = Intent(this, MainActivity::class.java)

//        initDataset()
//
//        val recyclerView: RecyclerView = recycler_list_add
//        recyclerView.setHasFixedSize(true)
//
//        val layoutManager = LinearLayoutManager(this)
//
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        recyclerView.layoutManager = layoutManager
//
//        val adapter = RecyclerViewAdapter_addItem(this, items)
//
//        recyclerView.setAdapter(adapter)



        rv1Data = mutableListOf()
        createData()

        val adapterRV1 = AdditemRVAdapter(this)
        rv1.setHasFixedSize(true)
        rv1.adapter = adapterRV1
        rv1Data?.let {
            //배열에 데이터가 있는지 확인 후, 어댑터의 data에 설정
            adapterRV1.data = it
        }
        rv1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        val dig = User_dialog(this)
        val alarm = Alarm_dialog(this)



        alarm_im.setOnClickListener{
              //alarm.start("알람")
            var cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                //cal.set(Calendar.HOUR_OF_DAY, hour)
                //cal.set(Calendar.MINUTE, minute)
                Toast.makeText(this, "$hour : $minute",Toast.LENGTH_SHORT).show()
                //            alarm.start("알람") //${SimpleDateFormat("HH:mm").format(cal.time)}
                Log.d("ㅎ", "$hour, $minute")
            }
            TimePickerDialog(this, timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }



        custom_button.setOnClickListener {
            dig.start("사용자 아이템 추가 창", context_additem as add_item)

        }




        button_save.setOnClickListener {
            listCntInt
            val listName = additem_title.text.toString()
            if (listName.isNullOrEmpty()) {
                Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                //title= "가평여행${listCnt}"
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()

                database.getReference("User")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (snapshot: DataSnapshot in snapshot.child("${userId},${nickName}")
                                .child("titleList").children) {
                                val info: String? = snapshot.child("title").value as String?
                                if (info == listName) {
                                    Toast.makeText(
                                        context_additem,
                                        "중복된 리스트(이름)가(이) 있습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("중복", "중복된 리스트(이름)가(이) 있습니다")
                                    return
                                }
                            }
                            setData(listName!!, listCntInt)
                            startActivity(mainActivity)
                            //목록개수 입력
                            listCntInt++
                            database.getReference("User").child("${userId!!},${nickName!!}")
                                .child("listCnt")
                                .setValue(listCntInt)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            }

        }




    }

    private fun setData(listName: String, listCntInt: Int) {
        //추가하는 부분
        //.push() 랜덤 키값 생성
        Items.add(Data_items(listName))
        database.getReference("User").child("${userId!!},${nickName!!}")
            .child("titleList")
            .push()
            .child("title").setValue(listName)
        Log.d("접근", "리스트추가체크${listCntInt}")
        adapter?.notifyDataSetChanged()
        additem_title.setText("")
    }

    private fun createData() {
        val item1 = Data_addItem_2(R.drawable.thor, "가나다")
        val item2 = Data_addItem_2(R.drawable.thor, "가나다")
        val item3 = Data_addItem_2(R.drawable.thor, "가나다")
        val item4 = Data_addItem_2(R.drawable.thor, "가나다")
        val item5 = Data_addItem_2(R.drawable.thor, "가나다")

        val arrFroRv2:MutableList<Data_addItem_2> = mutableListOf()
        arrFroRv2.add(item1)
        arrFroRv2.add(item2)
        arrFroRv2.add(item3)
        arrFroRv2.add(item4)
        arrFroRv2.add(item5)

        val arrFroRv3:MutableList<Data_addItem_2> = mutableListOf()
        repeat(30) { itemIndex ->
            arrFroRv3.add(Data_addItem_2(R.drawable.iron_man, "${itemIndex}")) }

        val data_addItem_1 = Data_addItem_1("의류", arrFroRv2)
        val data_addItem_2 = Data_addItem_1("전자기기", arrFroRv2)
        val data_addItem_3 = Data_addItem_1("테스트", arrFroRv3)

        rv1Data?.add(data_addItem_1)
        rv1Data?.add(data_addItem_2)
        rv1Data?.add(data_addItem_3)
    }


    fun getTime(hour: Int, minute: Int, cal: Calendar) {

        Toast.makeText(this, "${hour}시 ${minute}분", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, " ${SimpleDateFormat("HH:mm").format(cal.time)}",Toast.LENGTH_SHORT).show()
    }


    private fun initDataset() {
        items.clear()
        items.add(Data_addItem(R.drawable.ic_clothing, "의류"))
        items.add(Data_addItem(R.drawable.spider_man, "전자기기"))
        items.add(Data_addItem(R.drawable.black_panther, "세면용품"))
        items.add(Data_addItem(R.drawable.doctor, "여가활동"))
        items.add(Data_addItem(R.drawable.hulk, "차량용품"))
        items.add(Data_addItem(R.drawable.thor, "기타"))
        items.add(Data_addItem(R.drawable.thor, "예시5"))
        items.add(Data_addItem(R.drawable.thor, "예시6"))
        items.add(Data_addItem(R.drawable.thor, "예시7"))

    }

}


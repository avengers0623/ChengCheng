package kr.ac.ansan.chengcheng

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.additem.*
import kr.ac.ansan.chengcheng.MainActivity.Companion.itemBox
import java.util.*
import kotlin.collections.ArrayList


class addItem : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var adapterRV: AdditemRVAdapter
    private lateinit var adapterRVChecked: AdditemRVAdapterChecked
    var rv2Data: MutableList<Data_addItem_2>? = null
    var rv3Data: MutableList<CheckedItems>? = null
    var spinner_List_test = ArrayList<String>()

    companion object {
        var context_additem: Context? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.additem)

        context_additem = this

        //  카테고리 덩어리
        //나중에 클래스든 어디든 모아놓기
        val chengIcon: TypedArray = resources.obtainTypedArray(R.array.chengIcon)
        val chengList: Array<String> = resources.getStringArray(R.array.chengList)
        val clothesIcon: TypedArray = resources.obtainTypedArray(R.array.clothesIcon)
        val clothesList: Array<String> = resources.getStringArray(R.array.clothesList)
        val electronicIcon: TypedArray = resources.obtainTypedArray(R.array.electronicIcon)
        val electronicList: Array<String> = resources.getStringArray(R.array.electronicList)


        spinner = findViewById(R.id.spinner)
        adapterRV = AdditemRVAdapter()

        //큰카테고리
        //나중에 스트링배열로 저장하기
        val categoryList: Array<String> = resources.getStringArray(R.array.categoryListName)
        categoryList.forEach {
            spinner_List_test.add(it)
        }

        Log.d("spinner", spinner_List_test.toString())

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            @SuppressLint("ResourceType")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinner_List_test[position]
                when (selectedItem) {
                    spinner_List_test[0] -> selectedItemSet(chengIcon, chengList)
                    spinner_List_test[1] -> selectedItemSet(clothesIcon, clothesList)
                    spinner_List_test[2] -> selectedItemSet(electronicIcon, electronicList)
//                    spinner_List_test[3] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
//                    spinner_List_test[4] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
//                    spinner_List_test[5] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
//                    spinner_List_test[6] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

        val array_Adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, spinner_List_test
        )
        spinner.adapter = array_Adapter
        spinner.setSelection(0)

        // 아이템들 어댑터
        rv2Data = mutableListOf()

        var rv2 = findViewById<RecyclerView>(R.id.rv2)
        rv2.setHasFixedSize(true)
        rv2.adapter = adapterRV
        rv2Data?.let {
            //배열에 데이터가 있는지 확인 후, 어댑터의 data에 설정
            adapterRV.data = it
        }
        rv2.layoutManager = GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false)

        // 아이템 체크 어댑터
        adapterRVChecked = AdditemRVAdapterChecked()
        rv3Data = mutableListOf()
        itemcheck_recyclerView.setHasFixedSize(true)
        itemcheck_recyclerView.adapter = adapterRVChecked
        rv3Data?.let {
            adapterRVChecked.data = it
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        itemcheck_recyclerView.layoutManager = layoutManager


        val alarm = Alarm_dialog(this)
        val mainActivity = Intent(this, MainActivity::class.java)

        //저장
        button_save.setOnClickListener {
            clickAndBinding(mainActivity)
        }

        //알람
        alarm_im.setOnClickListener {
            //alarm.start("알람")
            var cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                //cal.set(Calendar.HOUR_OF_DAY, hour)
                //cal.set(Calendar.MINUTE, minute)
                Toast.makeText(this, "$hour : $minute", Toast.LENGTH_SHORT).show()
                //            alarm.start("알람") //${SimpleDateFormat("HH:mm").format(cal.time)}
                Log.d("ㅎ", "$hour, $minute")
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }
    }

    fun clickAndBinding(intent: Intent) {
//        var listCntInt = listCntInt
        val listName = additem_title.text.toString()
        if (listName.isNullOrEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            //title= "가평여행${listCnt}"
            // Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()

            MainActivity.database.getReference("User")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot: DataSnapshot in snapshot.child("${MainActivity.userId},${MainActivity.nickName}")
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
                        setData(listName)
                        startActivity(intent)
                        /*//목록개수 입력
                        listCntInt++
                        database.getReference("User").child("${userId!!},${nickName!!}")
                            .child("listCnt")
                            .setValue(listCntInt)*/
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }

    private fun setData(listName: String) {
        //추가하는 부분
        //.push() 랜덤 키값 생성
        MainActivity.Items.add(Data_items(listName))
        MainActivity.database.getReference("User")
            .child("${MainActivity.userId!!},${MainActivity.nickName!!}")
            .child("titleList")
            .push()
            .child("title").setValue(listName)
        //Log.d("접근", "리스트추가체크${listCntInt}")
        MainActivity.adapter?.notifyDataSetChanged()
        additem_title.setText("")
    }

    private fun selectedItemSet(Icon: TypedArray, List: Array<String>) {
        rv2Data!!.clear()

        for (i in List.indices) {
            rv2Data!!.add(Data_addItem_2(Icon.getResourceId(i, 0), List[i]))
        }

        adapterRV.setItemClickListener(object : AdditemRVAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                Log.d("SSS", "${List[position]}")
                //스크롤뷰에 아이템 추가 해야함
                val iconIndex = Icon.getResourceId(position, 0)
                itemBox?.add(iconIndex) //DB 에서 쓸라고
                addImageScrollView(iconIndex, itemBox!!) // 리사이클러뷰(밑에있는거)에서 쓸라고
                Log.d("SSS", itemBox!!.toString())
            }

        })
        adapterRV.notifyDataSetChanged()
    }

    private fun addImageScrollView(iconIndex: Int, itemBox: MutableSet<Int>) {

//        itemBox.forEach {
//            if(it == iconIndex){
//                Toast.makeText(this, "이미 중복된 항목", Toast.LENGTH_SHORT).show()
//            } else{
//                rv3Data!!.add(CheckedItems(iconIndex))
//                adapterRVChecked.notifyDataSetChanged()
//            }
//        }

        rv3Data!!.add(CheckedItems(iconIndex))
        adapterRVChecked.notifyDataSetChanged()

    }

    override fun onDestroy() {
        super.onDestroy()
        itemBox!!.clear()
    }
}



package kr.ac.ansan.chengcheng

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.additem.*
import kr.ac.ansan.chengcheng.CustomDialog.Companion.alarmManager
import kr.ac.ansan.chengcheng.CustomDialog.Companion.dayOfMonth
import kr.ac.ansan.chengcheng.CustomDialog.Companion.hourOfDay
import kr.ac.ansan.chengcheng.CustomDialog.Companion.minute
import kr.ac.ansan.chengcheng.CustomDialog.Companion.monthOfYear
import kr.ac.ansan.chengcheng.CustomDialog.Companion.pendingIntent
import kr.ac.ansan.chengcheng.CustomDialog.Companion.year
import kr.ac.ansan.chengcheng.MainActivity.Companion.Items
import kr.ac.ansan.chengcheng.MainActivity.Companion.adapter
import kr.ac.ansan.chengcheng.MainActivity.Companion.database
import kr.ac.ansan.chengcheng.MainActivity.Companion.itemBox
import kr.ac.ansan.chengcheng.MainActivity.Companion.nickName
import kr.ac.ansan.chengcheng.MainActivity.Companion.platformFlag
import kr.ac.ansan.chengcheng.MainActivity.Companion.social_platform
import kr.ac.ansan.chengcheng.MainActivity.Companion.userId
import kr.ac.ansan.chengcheng.databinding.AdditemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class addItem : AppCompatActivity(){
    private lateinit var spinner: Spinner
    private lateinit var adapterRV: AdditemRVAdapter
    private lateinit var adapterRVChecked: AdditemRVAdapterChecked
    private lateinit var binding : AdditemBinding
    private lateinit var addItemDlg : ImageView
    private lateinit var alarmIm : ImageView
    private var key: String? = null
    private var backpressedTime: Long = 0
    private lateinit var mainActivity : Intent



    var rv2Data: MutableList<Data_addItem_2>? = null
    var rv3Data: MutableList<CheckedItems>? = null
    var spinner_List_test = ArrayList<String>()


    companion object {
        var context_additem: Context? = null
        var indexForDelete: ArrayList<Int> = arrayListOf()
        var itemName : ArrayList<String> = arrayListOf()
    }


    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.additem)

        context_additem = this
        binding = AdditemBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //  카테고리 덩어리
        //나중에 클래스든 어디든 모아놓기
        val chengIcon: TypedArray = resources.obtainTypedArray(R.array.chengIcon)
        val chengList: Array<String> = resources.getStringArray(R.array.chengList)
        val clothesIcon: TypedArray = resources.obtainTypedArray(R.array.clothesIcon)
        val clothesList: Array<String> = resources.getStringArray(R.array.clothesList)
        val electronicIcon: TypedArray = resources.obtainTypedArray(R.array.electronicIcon)
        val electronicList: Array<String> = resources.getStringArray(R.array.electronicList)
        val campIcon: TypedArray = resources.obtainTypedArray(R.array.campIcon)
        val campList: Array<String> = resources.getStringArray(R.array.campList)
        val keyIcon: TypedArray = resources.obtainTypedArray(R.array.keyIcon)
        val keyList: Array<String> = resources.getStringArray(R.array.keyList)
        val washIcon: TypedArray = resources.obtainTypedArray(R.array.washIcon)
        val washList: Array<String> = resources.getStringArray(R.array.washList)
        val emgIcon: TypedArray = resources.obtainTypedArray(R.array.emgIcon)
        val emgList: Array<String> = resources.getStringArray(R.array.emgList)
        val babyIcon: TypedArray = resources.obtainTypedArray(R.array.babyIcon)
        val babyList: Array<String> = resources.getStringArray(R.array.babyList)
        val dogIcon: TypedArray = resources.obtainTypedArray(R.array.dogIcon)
        val dogList: Array<String> = resources.getStringArray(R.array.dogList)
        val etcIcon: TypedArray = resources.obtainTypedArray(R.array.etcIcon)
        val etcList: Array<String> = resources.getStringArray(R.array.etcList)



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
                    spinner_List_test[3] -> selectedItemSet(campIcon, campList)
                    spinner_List_test[4] -> selectedItemSet(keyIcon,keyList )
                    spinner_List_test[5] -> selectedItemSet(washIcon,washList)
                    spinner_List_test[6] -> selectedItemSet(emgIcon,emgList)
                    spinner_List_test[7] -> selectedItemSet(babyIcon,babyList)
                    spinner_List_test[8] -> selectedItemSet(dogIcon,dogList)
                    spinner_List_test[9] -> selectedItemSet(etcIcon,etcList)
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
        val layoutManager = GridLayoutManager(this, 5, RecyclerView.VERTICAL, false)
     //   layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        itemcheck_recyclerView.layoutManager = layoutManager



        mainActivity = Intent(this, MainActivity::class.java)


        // 검색 다이얼로그 시작
        addItemDlg = findViewById(R.id.search_im)
        val dlg = AddItemDig(this)
        addItemDlg.setOnClickListener {
            dlg.start("테스트")
        }


        alarmIm = findViewById(R.id.alarm_im)
      //알람
      alarmIm.setOnClickListener {

          var dialog = CustomDialog()



          Log.d("dialog","알람 버튼 눌림")
          dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener{
              override fun onButtonClicked1() {
                  Log.d("DialogFragment", "DialogFragment111")
                  //resultBts.text = "BTS"

              }
              override fun onButtonClicked2() {
                  Log.d("DialogFragment", "DialogFragment222")
              }

          })

          dialog.show(supportFragmentManager,"CustomDialog")

      }


        //저장
        button_save.setOnClickListener {
            // 아이템들 데이터베이스에 저장
            clickAndBinding(mainActivity)
        }

        /*//알람
        binding.alarmIm.setOnClickListener {
            val dialog = CustomDialog()

            dialog.setButtonClickListener(object : CustomDialog.OnButtonClickListener{
                override fun onButtonClicked1() {
                    Log.d("DialogFragment", "DialogFragment111")
                    //resultBts.text = "BTS"
                }
                override fun onButtonClicked2() {
                    Log.d("DialogFragment", "DialogFragment222")
                }

            })

            dialog.show(supportFragmentManager, "CustomDialog")




        }*/
    }

    private fun clickAndBinding(intent: Intent) {
//        var listCntInt = listCntInt
        val listName = additem_title.text.toString()
        if (listName.isNullOrEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            //title= "가평여행${listCnt}"
            // Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()

            database.getReference("User")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot: DataSnapshot in snapshot.child("platform").child(social_platform!!).child("$userId")
                            .child("titleList").children) {
                            val info: String? = snapshot.child("title").value as String?
                            Log.d("qwe","$snapshot")
                            Log.d("qwe2","$info")
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
                        if(!CustomDialog.check)
                        {
                            Toast.makeText(context_additem, "알람 날짜/시간을 설정해 주세요", Toast.LENGTH_SHORT).show()
                            return
                        }
                        setAlarm( year!!,
                            monthOfYear!!,
                            dayOfMonth!!,
                            hourOfDay!!,
                            minute!!,
                            alarmManager!!,
                            pendingIntent!!)
                        setData(listName)
                        indexForDelete.clear()
                        itemName.clear()
                        startActivity(intent)
                        finish()
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
        val key2 = makeKey()
        Items.add(Data_items(listName))
        database.getReference("User")
            .child("platform")
            .child(social_platform!!)
            .child("$userId")
            .child("titleList")
            .child(key2!!)
            .child("title")
            .setValue(listName)


        database.getReference("User")
            .child("platform")
            .child(social_platform!!)
            .child("$userId")
            .child("titleList")
            .child(key2)
            .child("item")
            .setValue(indexForDelete)

        database.getReference("User")
            .child("platform")
            .child(social_platform!!)
            .child("$userId")
            .child("titleList")
            .child(key2)
            .child("itemName")
            .setValue(itemName)


        Items.add(Data_items())
        //Log.d("접근", "리스트추가체크${listCntInt}")
        adapter?.notifyDataSetChanged()
        additem_title.setText("")
    }

    private fun selectedItemSet(Icon: TypedArray, List: Array<String>) {
        rv2Data!!.clear()

        for (i in List.indices) {
            rv2Data!!.add(Data_addItem_2(Icon.getResourceId(i, 0), List[i]))
        }

        // 서브RV에 아이템 추가
        adapterRV.setItemClickListener(object : AdditemRVAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                //Log.d("SSS", )
                //스크롤뷰에 아이템 추가 해야함
                val iconRes = Icon.getResourceId(position, 0) // 이미지 소스 값
                val iconName = List[position]
                addImageScrollView(iconRes,iconName) // 리사이클러뷰(밑에있는거)에서 쓸라고
                Log.d("SSS", itemBox!!.toString())
            }

        })
        adapterRV.notifyDataSetChanged()

        // 서브RV 누르면 삭제
        adapterRVChecked.setItemClickListener(object : AdditemRVAdapterChecked.ItemClickListener {
            override fun onClick(view: View, position: Int) {

                val iconRes = indexForDelete[position]  // 이미지 소스 값
                val iconName = itemName[position]
                Log.d("testICON", "pos: ${position}, $iconRes")
                delImageScrollView(iconRes,iconName)

            }
        })
    }


    private fun addImageScrollView(iconRes: Int , iconName : String) {

        var flag = true

        if (itemBox!!.contains(iconRes)) {
            Log.d("inddex", " 빠져나감 ")
            Log.d("inddexBBBBBBBBB111111", flag.toString())
            flag = false
        }

        if (flag) {
            rv3Data!!.add(CheckedItems(iconRes,iconName))
            adapterRVChecked.notifyDataSetChanged()
            itemBox!!.add(iconRes) //DB 에서 쓸라고
            itemName!!.add(iconName)
            indexForDelete.add(iconRes)
            Log.d("indexDelete1", indexForDelete.toString())
            Log.d("indexDelete2", itemName.toString())

        }

    }

    private fun delImageScrollView(iconRes: Int, iconName: String) {
        indexForDelete.remove(iconRes)
        itemName.remove(iconName)
        itemBox!!.remove(iconRes)
        Log.d("test", itemBox!!.toString())
        rv3Data!!.remove(CheckedItems(iconRes,iconName))
        Log.d("test", rv3Data.toString())
        adapterRVChecked.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()

        //이제 필요 없을듯.,.
        itemBox!!.clear()
        indexForDelete.clear()
        itemName.clear()
        // additem 액티비티 나갈때 파괴해야함
    }

    fun makeKey(): String? { //랜덤 키값 구하는 함수
        key = database.getReference("User").child("${platformFlag},${userId},${nickName}").child("title")
            .push().key.toString()
        return key
    }


     @RequiresApi(Build.VERSION_CODES.O)
    fun setAlarm(
         year: Int,
         monthOfYear: Int,
         dayOfMonth: Int,
         hourOfDay: Int,
         minute: Int,
         alarmManager: AlarmManager,
         pendingIntent: PendingIntent
    ) {
        //alarm.start("알람")

        val from = "$year-$monthOfYear-$dayOfMonth $hourOfDay:$minute:00"
        Log.d("datetest",from)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var datetime: Date? = null

        try {
            datetime = dateFormat.parse(from)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val calendar = Calendar.getInstance()
        calendar.time = datetime


        alarmManager[AlarmManager.RTC, calendar.timeInMillis] = pendingIntent
        Log.d("datetest2",calendar.timeInMillis.toString())

       // val triggerTime = (SystemClock.elapsedRealtime() // 기기가 부팅된 후 경과한 시간 사용
             //   + 2000) // ms 이기 때문에 초단위로 변환 (*1000)

         /*  alarmManager.set(
               AlarmManager.ELAPSED_REALTIME_WAKEUP,
               triggerTime,
               pendingIntent
           ) // set : 일회성 알림*/
        Toast.makeText(this, "${hourOfDay}시 : ${minute}분에 알람 설정 완료!", Toast.LENGTH_SHORT).show()


       /*       1. ELAPSED_REALTIME : ELAPSED_REALTIME 사용. 절전모드에 있을 때는 알람을 발생시키지 않고 해제되면 발생시킴.
              2. ELAPSED_REALTIME_WAKEUP : ELAPSED_REALTIME 사용. 절전모드일 때도 알람을 발생시킴.
              3. RTC : Real Time Clock 사용. 절전모드일 때는 알람을 발생시키지 않음.
              4. RTC_WAKEUP : Real Time Clock 사용. 절전모드 일 때도 알람을 발생시킴.*/

        //            alarm.start("알람") //${SimpleDateFormat("HH:mm").format(cal.time)}

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis()
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 메인으로 이동합니다.", Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            startActivity(mainActivity)
            finish()
        }
    }
}



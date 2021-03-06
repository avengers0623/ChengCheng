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



        //  ???????????? ?????????
        //????????? ???????????? ????????? ????????????
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

        //???????????????
        //????????? ?????????????????? ????????????
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

        // ???????????? ?????????
        rv2Data = mutableListOf()

        var rv2 = findViewById<RecyclerView>(R.id.rv2)
        rv2.setHasFixedSize(true)
        rv2.adapter = adapterRV
        rv2Data?.let {
            //????????? ???????????? ????????? ?????? ???, ???????????? data??? ??????
            adapterRV.data = it
        }
        rv2.layoutManager = GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false)

        // ????????? ?????? ?????????
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


        // ?????? ??????????????? ??????
        addItemDlg = findViewById(R.id.search_im)
        val dlg = AddItemDig(this)
        addItemDlg.setOnClickListener {
            dlg.start("?????????")
        }


        alarmIm = findViewById(R.id.alarm_im)
      //??????
      alarmIm.setOnClickListener {

          var dialog = CustomDialog()



          Log.d("dialog","?????? ?????? ??????")
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


        //??????
        button_save.setOnClickListener {
            // ???????????? ????????????????????? ??????
            clickAndBinding(mainActivity)
        }

        /*//??????
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
            Toast.makeText(this, "????????? ??????????????????", Toast.LENGTH_SHORT).show()
        } else {
            //title= "????????????${listCnt}"
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
                                    "????????? ?????????(??????)???(???) ????????????",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("??????", "????????? ?????????(??????)???(???) ????????????")
                                return
                            }
                        }
                        if(!CustomDialog.check)
                        {
                            Toast.makeText(context_additem, "?????? ??????/????????? ????????? ?????????", Toast.LENGTH_SHORT).show()
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
                        /*//???????????? ??????
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
        //???????????? ??????
        //.push() ?????? ?????? ??????
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
        //Log.d("??????", "?????????????????????${listCntInt}")
        adapter?.notifyDataSetChanged()
        additem_title.setText("")
    }

    private fun selectedItemSet(Icon: TypedArray, List: Array<String>) {
        rv2Data!!.clear()

        for (i in List.indices) {
            rv2Data!!.add(Data_addItem_2(Icon.getResourceId(i, 0), List[i]))
        }

        // ??????RV??? ????????? ??????
        adapterRV.setItemClickListener(object : AdditemRVAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                //Log.d("SSS", )
                //??????????????? ????????? ?????? ?????????
                val iconRes = Icon.getResourceId(position, 0) // ????????? ?????? ???
                val iconName = List[position]
                addImageScrollView(iconRes,iconName) // ??????????????????(???????????????)?????? ?????????
                Log.d("SSS", itemBox!!.toString())
            }

        })
        adapterRV.notifyDataSetChanged()

        // ??????RV ????????? ??????
        adapterRVChecked.setItemClickListener(object : AdditemRVAdapterChecked.ItemClickListener {
            override fun onClick(view: View, position: Int) {

                val iconRes = indexForDelete[position]  // ????????? ?????? ???
                val iconName = itemName[position]
                Log.d("testICON", "pos: ${position}, $iconRes")
                delImageScrollView(iconRes,iconName)

            }
        })
    }


    private fun addImageScrollView(iconRes: Int , iconName : String) {

        var flag = true

        if (itemBox!!.contains(iconRes)) {
            Log.d("inddex", " ???????????? ")
            Log.d("inddexBBBBBBBBB111111", flag.toString())
            flag = false
        }

        if (flag) {
            rv3Data!!.add(CheckedItems(iconRes,iconName))
            adapterRVChecked.notifyDataSetChanged()
            itemBox!!.add(iconRes) //DB ?????? ?????????
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

        //?????? ?????? ?????????.,.
        itemBox!!.clear()
        indexForDelete.clear()
        itemName.clear()
        // additem ???????????? ????????? ???????????????
    }

    fun makeKey(): String? { //?????? ?????? ????????? ??????
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
        //alarm.start("??????")

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

       // val triggerTime = (SystemClock.elapsedRealtime() // ????????? ????????? ??? ????????? ?????? ??????
             //   + 2000) // ms ?????? ????????? ???????????? ?????? (*1000)

         /*  alarmManager.set(
               AlarmManager.ELAPSED_REALTIME_WAKEUP,
               triggerTime,
               pendingIntent
           ) // set : ????????? ??????*/
        Toast.makeText(this, "${hourOfDay}??? : ${minute}?????? ?????? ?????? ??????!", Toast.LENGTH_SHORT).show()


       /*       1. ELAPSED_REALTIME : ELAPSED_REALTIME ??????. ??????????????? ?????? ?????? ????????? ??????????????? ?????? ???????????? ????????????.
              2. ELAPSED_REALTIME_WAKEUP : ELAPSED_REALTIME ??????. ??????????????? ?????? ????????? ????????????.
              3. RTC : Real Time Clock ??????. ??????????????? ?????? ????????? ??????????????? ??????.
              4. RTC_WAKEUP : Real Time Clock ??????. ???????????? ??? ?????? ????????? ????????????.*/

        //            alarm.start("??????") //${SimpleDateFormat("HH:mm").format(cal.time)}

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis()
            Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            startActivity(mainActivity)
            finish()
        }
    }
}



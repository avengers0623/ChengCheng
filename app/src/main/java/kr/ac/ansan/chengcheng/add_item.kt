package kr.ac.ansan.chengcheng

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_item.*
import kotlinx.android.synthetic.main.alarm_dialog.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class add_item :  AppCompatActivity() {

    private val items: ArrayList<Data_addItem> = ArrayList()
//    private val builder = AlertDialog.Builder(this)
//    private val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    private val view = inflater.inflate(R.layout.user_dialog, null)

    companion object {
        var context_additem: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)

        context_additem = this





//        val spinner = view.findViewById<Spinner>(R.id.spinner)
//        var spinner_List = ArrayList<String>()
//
//        val array_Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinner_List)
//        spinner.adapter = array_Adapter
//        spinner.setSelection(0)
//
//        builder.setView(view)
//
//        //val listener = DialogInterface.OnClickListener()
//
//        val dlg = builder.create()
//        dlg.setTitle("편집 대상 레이어")
//        dlg.show()






        initDataset()
        val recyclerView: RecyclerView = recycler_list_add
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter_addItem(this, items)

        recyclerView.setAdapter(adapter)

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


package kr.ac.ansan.chengcheng

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_item.*
import java.util.*
import kotlin.collections.ArrayList

class add_item :  AppCompatActivity(){


    private val items: ArrayList<Data_addItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)






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
            alarm.start("알람")
        }




        custom_button.setOnClickListener {
            dig.start("사용자 아이템 추가 창")
        }





    }



    private fun initDataset() {
        items.clear()
        items.add(Data_addItem(R.drawable.ic_clothing, "의류"))
        items.add(Data_addItem(R.drawable.spider_man, "세면도구"))
        items.add(Data_addItem(R.drawable.black_panther, "예시1"))
        items.add(Data_addItem(R.drawable.doctor, "예시2"))
        items.add(Data_addItem(R.drawable.hulk, "예시3"))
        items.add(Data_addItem(R.drawable.thor, "예시4"))
        items.add(Data_addItem(R.drawable.thor, "예시5"))
        items.add(Data_addItem(R.drawable.thor, "예시6"))
        items.add(Data_addItem(R.drawable.thor, "예시7"))

    }
}


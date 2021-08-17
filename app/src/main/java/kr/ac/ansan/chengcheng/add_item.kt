package kr.ac.ansan.chengcheng

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_item.*
import kotlinx.android.synthetic.main.alarm_dialog.*
import kotlinx.android.synthetic.main.item_add.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class add_item :  AppCompatActivity() {

    private val items: ArrayList<Data_addItem> = ArrayList()
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


        //서브 grid뷰
        val gridAdapter = GridViewAdapter()

        gridAdapter.addItem(Data_addItem_Sub("1", "파랑이", R.drawable.thor))
        gridAdapter.addItem(Data_addItem_Sub("1", "파랑이", R.drawable.thor))
        gridAdapter.addItem(Data_addItem_Sub("1", "파랑이", R.drawable.thor))
        gridAdapter.addItem(Data_addItem_Sub("1", "파랑이", R.drawable.thor))

        gridview.adapter = gridAdapter
        //
        gridview.numColumns = 2
        gridview.horizontalSpacing = 15
        gridview.verticalSpacing = 15
        gridview.stretchMode = GridView.STRETCH_COLUMN_WIDTH


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


    /* 그리드뷰 어댑터 */
    internal class GridViewAdapter : BaseAdapter() {
        var items: ArrayList<Data_addItem_Sub> = ArrayList<Data_addItem_Sub>()
        override fun getCount(): Int {
            return items.size
        }

        fun addItem(item: Data_addItem_Sub) {
            items.add(item)
        }

        override fun getItem(position: Int): Any {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View? {
            var convertView: View? = convertView
            val context = viewGroup.context
            val subItem: Data_addItem_Sub = items[position]
            if (convertView == null) {
                val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.gridview_list_item, viewGroup, false)
                val tv_num = convertView.findViewById<View>(R.id.tv_num) as TextView
                val tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
                val iv_icon = convertView.findViewById<View>(R.id.iv_icon) as ImageView
                tv_num.setText(subItem.num)
                tv_name.setText(subItem.name)
                iv_icon.setImageResource(subItem.resId)
                //Log.d(TAG, "getView() - [ " + position.toString() + " ] " + subItem.getName())
            } else {
                var view: View? = View(context)
                view = convertView
            }

            //각 아이템 선택 event
            convertView!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    Toast.makeText(
                        context,
                        subItem.num.toString() + " 번 - " + subItem.name + " 입니당! ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            return convertView //뷰 객체 반환
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


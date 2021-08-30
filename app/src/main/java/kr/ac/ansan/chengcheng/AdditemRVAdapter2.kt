package kr.ac.ansan.chengcheng

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.containsKey
import androidx.core.util.keyIterator
import androidx.core.util.valueIterator
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.user.model.User
import kotlinx.android.synthetic.main.rv1_item.view.*
import kotlinx.android.synthetic.main.rv2_item.view.*

class AdditemRVAdapter2(position1: Int, checkboxStatus: HashMap<Int, Boolean>, compareList: ArrayList<Int>) : RecyclerView.Adapter<AdditemRVAdapter2.Rv2Holder>() {

    var data = mutableListOf<Data_addItem_2>()
    var checkboxStatus = checkboxStatus
    var compareList = compareList
    var position1 = position1

    companion object {
        var list = arrayListOf<Data_addItem_2>()
        var checkboxList = arrayListOf<checkboxData>()
        lateinit var selectCheckBoxPosition:HashMap<Int, Int>
        private set
    }

    inner class Rv2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var onViewHolderItemClickListener: OnViewHolderItemClickListener? = null
        private var frameLayout: FrameLayout? = null


        init {
            frameLayout = itemView.frameLayout_addItem

            frameLayout!!.setOnClickListener {
                onViewHolderItemClickListener!!.onViewHolderItemClick()
            }
        }


        fun setData(data: Data_addItem_2) {
            itemView.img_cover.setImageResource(data.getImage())
            itemView.tv_name.text = data.imgName
        }

        @JvmName("setOnViewHolderItemClickListener2")
        fun setOnViewHolderItemClickListener(onViewHolderItemClickListener: OnViewHolderItemClickListener?) {
            this.onViewHolderItemClickListener = onViewHolderItemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemRVAdapter2.Rv2Holder {
        return Rv2Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv2_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Rv2Holder, position: Int) {
        Log.d("어댑터띵", "띵")
        val item = data[position]
        val img = holder.itemView.findViewById<ImageView>(R.id.img_cover)
        val campList2: Array<String> =
            holder.itemView.resources.getStringArray(R.array.campNameList)
        val campList3: ArrayList<String> = ArrayList()
        var max = campList2.size

        holder.apply {
            setData(item)
        }

        /*override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(yourList[position])
            val seqCheckBox = holder.itemView.findViewById<CheckBox>(R.id.seq)
            seqCheckBox.setOnCheckedChangeListener(null)
            seqCheckBox.isChecked = yourActivity.selectCheckBoxPosition.containsValue(position)
            seqCheckBox.setOnClickListener {
                val seq = seqCheckBox.tag.toString().toInt()
                if (seqCheckBox.isChecked) {
                    yourActivity.selectCheckBoxPosition[seq] = position
                } else {
                    yourActivity.selectCheckBoxPosition.remove(seq)
                }
            }
        }*/

            Log.d("시발 받아오고나서", "1번: ${position1}, 2번: ${position}")
            var posMap: HashMap<Int, Int> = hashMapOf()
            posMap.put(position1, position)
            Log.d("pos", posMap.toString())
            Log.d("pos123", "키값: ${position1}, value값: ${checkboxStatus.keys}")

            var compareMap: HashMap<Int, MutableList<Int>> = HashMap()
            compareMap[position1] = compareList
            Log.d("체크123", position1.toString())






            val checkboxUser = holder.itemView.findViewById<CheckBox>(R.id.itemCheckBox)

            checkboxUser.isChecked = checkboxStatus[position] == true
            Log.d("시발포지션", checkboxStatus.toString())

            checkboxUser.setOnClickListener {
                if (!checkboxUser.isChecked) {
                    checkboxStatus.put(position, false)
                    Log.d("시발결과", "체크상태: ${checkboxStatus.toString()}")

                    var positionCheck = position
                    compareList.remove(positionCheck)

                } else {
                    checkboxStatus.put(position, true)
                    Log.d("시발결과", "체크상태: ${checkboxStatus.toString()}")
                    var positionCheck = position
                    compareList.add(positionCheck)
                    Log.d("맵", compareList.toString())


                    //  notifyItemChanged(position)
                }
            }






//33333333
//        selectCheckBoxPosition = hashMapOf()
//        selectCheckBoxPosition[0] = 1
//        val seqCheckBox = holder.itemView.findViewById<CheckBox>(R.id.itemCheckBox)
//        seqCheckBox.setOnCheckedChangeListener(null)
//        seqCheckBox.isChecked = AdditemRVAdapter2.selectCheckBoxPosition.containsValue(position)
//        seqCheckBox.setOnClickListener {
//            val seq = seqCheckBox.tag.toString().toInt()
//            if (seqCheckBox.isChecked) {
//                AdditemRVAdapter2.selectCheckBoxPosition[seq] = position
//            } else {
//                AdditemRVAdapter2.selectCheckBoxPosition.remove(seq)
//            }
//        }

//22222222222
//        Log.d("띵", "position: ${position}, listSize: ${checkboxList.size}")
//        if (position >= checkboxList.size) {
//            checkboxList.add(position, checkboxData(data.size, false))
//        }
//
//        val checkItem: checkboxData = checkboxList[position]
//
//        holder.itemCheckBox.setOnCheckedChangeListener(null)
//
//        holder.itemCheckBox.isChecked = checkItem.getSelected()
//
//        holder.itemCheckBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
//            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//                checkItem.setSelected(isChecked)
//            }
//
//        })
//11111111
////        if (position >= checkboxList.size) {
//            checkboxList.add(position, checkboxData(data.size, false))
////        }
//
//        holder.itemCheckBox.isChecked = checkboxList[position].checked
//
//        holder.itemCheckBox.setOnClickListener {
//            Log.d("체크박스", "확인")
//            checkboxList[position].checked = holder.itemCheckBox.isChecked
//        }

        holder.setOnViewHolderItemClickListener {

            //img.setBackgroundColor(Color.RED)
            Log.d("어댑터위치", "$position")

            /*for (i in 0 until max) {
                if(position.toString() == campList2[i]){
                    campList3.add(campList2[i])
                }
            }
            for (i in 0 until max) {
                Log.d("어댑터값","${campList3[i]}")
            }
            Log.d("어댑터", "작동함")
            Log.d("어댑터위치", "$position")*/


            //notifyItemChanged(position)

        }

    }


    override fun getItemCount(): Int {
        return data.size
    }

}

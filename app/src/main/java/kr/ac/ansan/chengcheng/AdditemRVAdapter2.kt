package kr.ac.ansan.chengcheng

import android.content.res.TypedArray
import android.icu.text.Edits
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv2_item.view.*
import java.util.function.Predicate

class AdditemRVAdapter2(
    position1: Int,
    checkboxStatus: HashMap<Int, Boolean>,
    compareList: MutableSet<Int>,
    saveMap: checkboxData
) : RecyclerView.Adapter<AdditemRVAdapter2.Rv2Holder>() {

    var data = mutableListOf<Data_addItem_2>()
    var checkboxStatus = checkboxStatus
    var compareList = compareList
    var position1 = position1
    var saveMap = saveMap.saveMaper
    var com: MutableSet<Int> = mutableSetOf()

    companion object {

    }

    inner class Rv2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var onViewHolderItemClickListener: OnViewHolderItemClickListener? = null
        private var frameLayout: FrameLayout? = null

        init {
            frameLayout = itemView.frameLayout_addItem

            //오류나서 일단 주석처리(아마 중복이지 않을까)
//            frameLayout!!.setOnClickListener {
//                onViewHolderItemClickListener!!.onViewHolderItemClick()
//            }
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

        holder.apply {
            setData(item)
        }

        val checkboxUser = holder.itemView.findViewById<CheckBox>(R.id.itemCheckBox)

        val typedArray = add_item.typedArray



        checkboxUser.setOnClickListener {

        }

//        checkboxUser.setOnClickListener {
//            Log.d("큰카테고리", position1.toString())
//            if (!checkboxUser.isChecked) {
//                removeCheckedList(position)
//            } else {
//                addCheckedList(position)
//            }
//
//        }


//        Log.d("객체1", saveMap[position1].toString())
//        saveMap[position1]?.forEach {
//            Log.d("마실험it", it.toString())
//            checkboxStatus.put(it, true)
//            Log.d("마실험status", "$checkboxStatus")
//            Log.d("마실험save", saveMap.toString())
//            if (checkboxStatus[it] == true) {
//                Log.d("마실험status_position", "$position")
//                checkboxUser.isChecked = checkboxStatus[position] == true
//            }
//        }




        //compareList = arrayListOf()
        Log.d("객체2", saveMap[position1].toString())

    }

    //큰카테고리에 체크상태를 담아줌
    private fun addCheckedList(position: Int) {
        Log.d("aaa saveMap1", "position1: ${position1} ,map: ${saveMap}")
//        if (saveMap.containsKey(position1)) {
        //com은 임시 리스트
        var it = saveMap[position1]!!.iterator()

        while (it.hasNext()) {
            val item = it.next()
            Log.d("ititit", item.toString())
            com.add(item)
        }

        Log.d("aaa Iterator", com.toString())
//        }


        var positionCheck = position
        com.add(positionCheck)
        Log.d("aaa comapreList추가후", "position1: ${position1} ,list: ${com}")
        saveMap.put(position1, com)
        Log.d("aaa saveMap put", saveMap.toString())

        saveMap[position1]!!.addAll(com)
        //com = mutableSetOf()

        Log.d("aaa compareList 초기화", com.toString())

    }

    private fun removeCheckedList(position: Int) {

        if (saveMap.containsKey(position1)) {
            var it: Iterator<Int> = saveMap[position1]!!.iterator()

            while (it.hasNext()) {
                val item = it.next()
                compareList.add(item)
            }
            Log.d("saveMap", "values: ${compareList}")
        }

        //checkboxStatus.put(position, false)
        Log.d("시발결과", "체크상태: ${checkboxStatus.toString()}")
        Log.d("맵제거", compareList.toString())

        var positionCheck = position
        compareList.remove(positionCheck)

        saveMap.put(position1, compareList)
        compareList = mutableSetOf()
        Log.d("saveMap", saveMap.toString())
    }


    override fun getItemCount(): Int {
        return data.size
    }

}


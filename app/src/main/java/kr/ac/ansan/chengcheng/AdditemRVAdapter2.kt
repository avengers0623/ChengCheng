package kr.ac.ansan.chengcheng

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv2_item.view.*

class AdditemRVAdapter2(
    position1: Int,
    checkboxStatus: HashMap<Int, Boolean>,
    compareList: ArrayList<Int>,
    saveMap: HashMap<Int,ArrayList<Int>>
) : RecyclerView.Adapter<AdditemRVAdapter2.Rv2Holder>() {

    var data = mutableListOf<Data_addItem_2>()
    var checkboxStatus = checkboxStatus
    var compareList = compareList
    var position1 = position1
    var saveMap = saveMap

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
        checkboxUser.setOnClickListener {

            if (!checkboxUser.isChecked) {
                checkboxStatus.put(position, false)
                Log.d("시발결과", "체크상태: ${checkboxStatus.toString()}")
                Log.d("맵제거", compareList.toString())


                removeCheckedList(position, compareList)

                saveMap.put(position1, compareList)
                Log.d("saveMap", saveMap.toString())

            } else {
                checkboxStatus.put(position, true)
                Log.d("시발결과", "큰카테고리:${position1} ,: ${compareList.toString()}")
                Log.d("맵추가", "큰카테고리:${position1} ,: ${compareList.toString()}")


                addCheckedList(position, compareList)

                saveMap.put(position1, compareList)
                Log.d("saveMap", saveMap.toString())
            }
        }


        val checkboxData = checkboxData(saveMap)
        Log.d("마실험", checkboxData.saveMap[position1].toString())
        checkboxData.saveMap[position1]?.forEach {
            checkboxStatus.put(it, true)
            Log.d("마실험", it.toString())
            if(checkboxStatus[it] == true){
                checkboxUser.isChecked = checkboxStatus[position] == true
            }
        }

        compareList = arrayListOf()

    }

    //큰카테고리에 체크상태를 담아줌
    private fun addCheckedList(position: Int, compareList: ArrayList<Int>){

        var positionCheck = position
        compareList.add(positionCheck)
    }

    private fun removeCheckedList(position: Int, compareList: ArrayList<Int>){

        var positionCheck = position
        compareList.remove(positionCheck)
    }


    override fun getItemCount(): Int {
        return data.size
    }

}


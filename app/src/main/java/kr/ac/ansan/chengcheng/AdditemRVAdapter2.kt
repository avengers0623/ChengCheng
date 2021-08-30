package kr.ac.ansan.chengcheng

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv2_item.view.*

class AdditemRVAdapter2(
    position1: Int,
    checkboxStatus: HashMap<Int, Boolean>,
    compareList: ArrayList<Int>,
    saveMap: HashMap<Int, MutableList<Int>>
) : RecyclerView.Adapter<AdditemRVAdapter2.Rv2Holder>() {

    var data = mutableListOf<Data_addItem_2>()
    var checkboxStatus = checkboxStatus
    var compareList = compareList
    var position1 = position1
    var saveMap = saveMap

    companion object {
        var list = arrayListOf<Data_addItem_2>()
        var checkboxList = arrayListOf<checkboxData>()
        lateinit var selectCheckBoxPosition: HashMap<Int, Int>
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
        val checkboxUser = holder.itemView.findViewById<CheckBox>(R.id.itemCheckBox)

        Log.d("시발 받아오고나서", "1번: ${position1}, 2번: ${position}")
        var posMap: HashMap<Int, Int> = hashMapOf()
        posMap.put(position1, position)
        Log.d("pos", posMap.toString())

        var compareMap: HashMap<Int, MutableList<Int>> = HashMap()
        compareMap[position1] = compareList

        //큰카테고리 추가하면 추가해줘야함 ㅄ아 아니면 생각을해

//        when(position1){
//            0 -> {
//
//            }
//            1 ->
//                2 ->
//            3 ->
//            4 ->
//            5 ->
//            6 ->
//        }


        checkboxUser.isChecked = checkboxStatus[position] == true
        Log.d("시발포지션", checkboxStatus.toString())
        //var compareList2 = ArrayList<Int>()
        checkboxUser.setOnClickListener {
            when (position1) {
                0 -> {  }
                1 -> {  }
            }
            if (!checkboxUser.isChecked) {
                checkboxStatus.put(position, false)
                Log.d("시발결과", "체크상태: ${checkboxStatus.toString()}")
                var positionCheck = position
                compareList.remove(positionCheck)
                Log.d("맵제거", compareList.toString())


                saveMap.put(position1, compareList)
                Log.d("saveMap", saveMap.toString())

            } else {
                checkboxStatus.put(position, true)
                Log.d("시발결과", "큰카테고리:${position1} ,: ${compareList.toString()}")
                var positionCheck = position
                compareList.add(positionCheck)
                Log.d("맵추가", "큰카테고리:${position1} ,: ${compareList.toString()}")


                saveMap.put(position1, compareList)
                Log.d("saveMap", saveMap.toString())

                //  notifyItemChanged(position)
            }
        }


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

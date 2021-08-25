package kr.ac.ansan.chengcheng

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv1_item.view.*

class AdditemRVAdapter(context: Context) : RecyclerView.Adapter<AdditemRVAdapter.Rv1Holder>() {
    val context = context
    var data = mutableListOf<Data_addItem_1>()


    inner class Rv1Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(data: Data_addItem_1){
            itemView.tv_subject.text =  data.title

            //AdditemRVAdapter2의 어댑터 객체를 만들어 데이터를 넘겨준다
            val additemRVAdapter2 = AdditemRVAdapter2()
            val r2Data = data.itemList
            additemRVAdapter2.data = r2Data
            //AdditemRVAdapter2의 레이아웃 설정
            //itemView.rv2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemView.rv2.layoutManager = GridLayoutManager(context, 3, RecyclerView.HORIZONTAL, false)
            //AdditemRVAdapter2는 첫 번째 RecyclerView의 ID, 어댑터를 설정한다
            itemView.rv2.adapter = additemRVAdapter2
            additemRVAdapter2.notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemRVAdapter.Rv1Holder {
        return Rv1Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv1_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AdditemRVAdapter.Rv1Holder, position: Int) {
        var item = data[position]
        holder.apply {
            setData(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }



}
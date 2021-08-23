package kr.ac.ansan.chengcheng

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv1_item.view.*
import kotlinx.android.synthetic.main.rv2_item.view.*

class AdditemRVAdapter2 : RecyclerView.Adapter<AdditemRVAdapter2.Rv2Holder>() {
    var data = mutableListOf<Data_addItem_2>()

    inner class  Rv2Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(data: Data_addItem_2) {
            itemView.img_cover.setImageResource(data.getImage())
            itemView.tv_name.text =  data.imgName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemRVAdapter2.Rv2Holder {
        return Rv2Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv2_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AdditemRVAdapter2.Rv2Holder, position: Int) {
        var item = data[position]
        holder.apply {
            setData(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

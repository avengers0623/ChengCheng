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

class test_AdditemRVAdapter2(
) : RecyclerView.Adapter<test_AdditemRVAdapter2.Rv2Holder>() {
    var data = mutableListOf<Data_addItem_2>()
    inner class Rv2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var onViewHolderItemClickListener: OnViewHolderItemClickListener? = null


        fun setData(data: Data_addItem_2) {
            itemView.img_cover.setImageResource(data.getImage())
            itemView.tv_name.text = data.imgName
        }

        @JvmName("setOnViewHolderItemClickListener2")
        fun setOnViewHolderItemClickListener(onViewHolderItemClickListener: OnViewHolderItemClickListener?) {
            this.onViewHolderItemClickListener = onViewHolderItemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): test_AdditemRVAdapter2.Rv2Holder {
        return Rv2Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv2_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Rv2Holder, position: Int) {
        val item = data[position]
        holder.apply {
            setData(item)
        }
    }



    override fun getItemCount(): Int {
        return data.size
    }

}


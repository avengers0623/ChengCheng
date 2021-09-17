package kr.ac.ansan.chengcheng

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.additem.view.*
import kotlinx.android.synthetic.main.checked_items.view.*
import kotlinx.android.synthetic.main.rv2_item.view.*

class AdditemRVAdapterChecked(
) : RecyclerView.Adapter<AdditemRVAdapterChecked.Rv3Holder>() {
    var data = mutableListOf<CheckedItems>()
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    inner class Rv3Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(data: CheckedItems) {
            itemView.itemview_img.setImageResource(data.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemRVAdapterChecked.Rv3Holder {
        return Rv3Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.checked_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Rv3Holder, position: Int) {
        val item = data[position]

        holder.apply {
            setData(item)
        }

        holder.itemView.setOnClickListener {
            notifyItemChanged(position)
            itemClickListener.onClick(it, position)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}


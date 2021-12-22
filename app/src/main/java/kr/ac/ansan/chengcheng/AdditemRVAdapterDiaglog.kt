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
import kotlinx.android.synthetic.main.rv2_item.view.*
import kotlinx.android.synthetic.main.rv_dlg.view.*

class AdditemRVAdapterDialog(
) : RecyclerView.Adapter<AdditemRVAdapterDialog.Rv2Holder>() {
    var data = mutableListOf<DialogItems>()
    var dataName = mutableListOf<DialogItems>()
    private lateinit var itemClickListener: ItemClickListener


    interface ItemClickListener{
        fun onClick(view : View, position: Int)
    }


    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    inner class Rv2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(data: DialogItems) {
            itemView.img_cover_dlg.setImageResource(data.img)
            itemView.itemName_dlg.text= data.digItemName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemRVAdapterDialog.Rv2Holder {
        return Rv2Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_dlg, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Rv2Holder, position: Int) {
        val item = data[position]

        holder.apply {
            setData(item)
        }

        holder.itemView.setOnClickListener {
            notifyItemChanged(position)
            itemClickListener.onClick(it,position)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}


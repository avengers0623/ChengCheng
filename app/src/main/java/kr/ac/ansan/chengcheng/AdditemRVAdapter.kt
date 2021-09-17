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

class AdditemRVAdapter(
) : RecyclerView.Adapter<AdditemRVAdapter.Rv2Holder>() {
    var data = mutableListOf<Data_addItem_2>()
    private lateinit var itemClickListener: ItemClickListener
    companion object{

    }


    interface ItemClickListener{
        fun onClick(view : View, position: Int)
    }


    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    inner class Rv2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var onViewHolderItemClickListener: OnViewHolderItemClickListener? = null
        private var frameLayout: FrameLayout? = null

        fun setData(data: Data_addItem_2) {
            itemView.img_cover.setImageResource(data.getImage())
            itemView.tv_name.text = data.imgName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditemRVAdapter.Rv2Holder {
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

        holder.itemView.setOnClickListener {
            notifyItemChanged(position)
            itemClickListener.onClick(it,position)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}


package kr.ac.ansan.chengcheng

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_add.view.linearlayout_add
import kotlinx.android.synthetic.main.recycler_list_item.view.*


class RecyclerViewAdapter(context: Context, persons: ArrayList<Data_items>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listData: ArrayList<Data_items>? = persons
    private var mInflate: LayoutInflater? = null
    private var mContext: Context = context

    // Item의 클릭 상태를 저장할 array 객체
    private val selectedItems = SparseBooleanArray()



    init {
        this.mInflate = LayoutInflater.from(context)
    }

    @NonNull
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //연결할 레이아웃 설정
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_list_item, parent, false)
        )
    }

    @NonNull
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val myViewHolder = holder as MyViewHolder
        myViewHolder.onBind(listData!![position], position, selectedItems)
        myViewHolder.setOnViewHolderItemClickListener(object : OnViewHolderItemClickListener{
            //람다 가능?
            override fun onViewHolderItemClick() {
                // 해당 포지션의 변화를 알림
                notifyItemChanged(position)
            }

        })

    }

    override fun getItemCount(): Int {
        return listData!!.size
    }




//    fun addItem(data: items) {
//        // 외부에서 item을 추가시킬 함수입니다.
//        listData!!.add(data)
//    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var one: ImageView? = null
        var two: TextView? = null
        var linearlayout: LinearLayout? = null
        var onViewHolderItemClickListener: OnViewHolderItemClickListener? = null
        val dig = Dialog(itemView.context)
        init {
            one = itemView.one
            two = itemView.two
            linearlayout = itemView.linearlayout_add

            linearlayout!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    dig.start("${two!!.text}")

                    onViewHolderItemClickListener!!.onViewHolderItemClick()
                }


            }
            )
        }

        fun onBind(data: Data_items, position: Int, selectedItems: SparseBooleanArray){
            two!!.text = data.getTitle()
            one!!.setImageResource(data.getImage())
        }

        @JvmName("setOnViewHolderItemClickListener1")
        fun setOnViewHolderItemClickListener(onViewHolderItemClickListener: OnViewHolderItemClickListener?) {
            this.onViewHolderItemClickListener = onViewHolderItemClickListener
        }
    }






}
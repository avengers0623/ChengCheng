package kr.ac.ansan.chengcheng

import android.animation.ValueAnimator
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
import kotlinx.android.synthetic.main.item_add.view.*

class RecyclerViewAdapter_addItem(context: Context, persons: ArrayList<Data_addItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listData: ArrayList<Data_addItem>? = persons
    private var mInflate: LayoutInflater? = null
    private var mContext: Context = context

    // Item의 클릭 상태를 저장할 array 객체
    private val selectedItems = SparseBooleanArray()
    // 직전에 클릭됐던 Item의 position
    private var prePosition = -1



    init {
        this.mInflate = LayoutInflater.from(context)
    }

    @NonNull
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //연결할 레이아웃 설정
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add, parent, false)
        )
    }

    @NonNull
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val myViewHolder = holder as MyViewHolder
        myViewHolder.onBind(listData!![position], position, selectedItems)
        myViewHolder.setOnViewHolderItemClickListener(object : OnViewHolderItemClickListener{
            //람다 가능?
            override fun onViewHolderItemClick() {
                if(selectedItems[position]) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position)
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition)
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true)
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition)
                notifyItemChanged(position)
                // 클릭된 position 저장
                prePosition = position
            }

        })

    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

    fun addItem(data: Data_addItem) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData!!.add(data)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_movie: ImageView? = null
        var tv_movie_title: TextView? = null
        var iv_movie2: ImageView? = null
        var iv_movie3: ImageView? = null
        var linearlayout: LinearLayout? = null
        var onViewHolderItemClickListener: OnViewHolderItemClickListener? = null

        init {
            iv_movie = itemView.iv_movie
            tv_movie_title = itemView.tv_movie_title
            iv_movie2 = itemView.iv_movie2
            linearlayout = itemView.linearlayout_add
            iv_movie3 = itemView.iv_movie3


            linearlayout!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onViewHolderItemClickListener!!.onViewHolderItemClick()
                }
            }
            )
        }

        fun onBind(data: Data_addItem, position: Int, selectedItems: SparseBooleanArray){
            tv_movie_title!!.text = data.getTitle()
            iv_movie!!.setImageResource(data.getImage())
            iv_movie2!!.setImageResource(R.drawable.ic_hat)
            iv_movie3!!.setImageResource(R.drawable.ic_add)
            changeVisibility(selectedItems.get(position))
        }

        private fun changeVisibility(isExpanded: Boolean){
            val va = if (isExpanded) ValueAnimator.ofInt(0, 600) else ValueAnimator.ofInt(600, 0)
            // Animation이 실행되는 시간, n/1000초
            va.duration = 300
            va.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener{
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    // imageView의 높이 변경
                    iv_movie2!!.layoutParams.height = animation.animatedValue as Int
                    iv_movie2!!.requestLayout()
                    iv_movie3!!.layoutParams.height = animation.animatedValue as Int
                    iv_movie3!!.requestLayout()
                    // imageView가 실제로 사라지게하는 부분
                    iv_movie2!!.visibility = if (isExpanded) View.VISIBLE else View.GONE
                    iv_movie3!!.visibility = if (isExpanded) View.VISIBLE else View.GONE
                }
            })
            // Animation start
            va.start()
        }
        @JvmName("setOnViewHolderItemClickListener1")
        fun setOnViewHolderItemClickListener(onViewHolderItemClickListener: OnViewHolderItemClickListener?) {
            this.onViewHolderItemClickListener = onViewHolderItemClickListener
        }
    }
}
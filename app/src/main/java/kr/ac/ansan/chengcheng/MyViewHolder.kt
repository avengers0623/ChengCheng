package kr.ac.ansan.chengcheng

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_list_item.view.*
import kr.ac.ansan.chengcheng.R.drawable.ic_person

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val TAG: String = "로그"

    private val usernameTextView = itemView.textView_title
    private val profileImageView = itemView.profile_img

    //기본 생성자
    init {
        Log.d(TAG,"MyViewHolder - init() called")
    }

    // 데이터와 뷰를 묶는다.
    fun bind(myModel: MyModel)
    {
        Log.d(TAG,"MyViewHolder - bind() called")
        usernameTextView.text = myModel.title
        profileImageView.setImageResource(R.drawable.ic_person);

    }



}
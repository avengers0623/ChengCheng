package kr.ac.ansan.chengcheng

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerAdapter: RecyclerView.Adapter<MyViewHolder>(){

    private val TAG: String = "로그"
    private var modelList = ArrayList<MyModel>()

    // 뷰홀더 생성 되었을떄
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //연결할 레이아웃 설정
return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item,parent,false))
    }
    // 뷰와 뷰홀더가 묶였을때
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    Log.d(TAG,"MyRecyclerAdapter - onBindViewHolder() called / position: $position")
    holder.bind(this.modelList[position])
    }
    // 목록의 아이템수
    override fun getItemCount(): Int {
        return this.modelList.size
    }


    //외부에서 데이터 넘기기
    fun submitList(modelList: ArrayList<MyModel>)
    {
     this.modelList = modelList
    }

    fun setData(){}




}
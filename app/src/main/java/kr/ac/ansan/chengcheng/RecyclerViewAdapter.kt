package kr.ac.ansan.chengcheng

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerViewAdapter(context: Context, persons: ArrayList<item>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var mPersons: ArrayList<item>? = persons
    private var mInflate: LayoutInflater? = null
    private var mContext: Context = context

    init {
        this.mInflate = LayoutInflater.from(context)
    }

    @NonNull
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //연결할 레이아웃 설정
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    @NonNull
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //데이터로 뷰를 바인딩
        //val url = mPersons!![position].photo
        holder.name?.setText(mPersons!![position].name)
//        Glide.with(mContext) //이미지 처리
//            .load(url)
//            .centerCrop()
//            .crossFade()
//            .into(holder.imageView)
        holder.summary?.setText(mPersons!!.get(position).summary)

        //클릭시 웹검색
//        holder.search!!.setOnClickListener {
//            val term = mPersons!![position].name
//            val intent = Intent(Intent.ACTION_WEB_SEARCH)
//            intent.putExtra(SearchManager.QUERY, term)
//            mContext!!.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return mPersons!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        //var imageView: ImageView? = null
        var summary: TextView? = null
        var search: ImageView? = null

        init{
            name = itemView.tv_name
            //imageView = itemView.imageView
            summary = itemView.tv_summary
            search = itemView.bt_search
            search?.setColorFilter(0xFFFF0000.toInt(), PorterDuff.Mode.MULTIPLY)
        }
    }

}
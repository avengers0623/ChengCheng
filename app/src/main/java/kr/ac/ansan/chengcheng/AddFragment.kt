package kr.ac.ansan.chengcheng

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddFragment : Fragment() {
    private val items: ArrayList<Data_addItem> = ArrayList()

    var myButton: Button? = null
    var myView: View? = null
    var isUp: Boolean? = null
    var isUp2: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        Toast.makeText(context, "ADD프 실행", Toast.LENGTH_SHORT).show()
        var view = inflater.inflate(R.layout.add_item, container, false)

        myView = view.findViewById(R.id.my_view)
        myButton = view.findViewById(R.id.my_button)

        myView!!.visibility = View.INVISIBLE
        myButton!!.text = "Slide Up"
        isUp = false
        isUp2 = true

        initDataset()

        val context: Context = view.context
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_list_add)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter_addItem(context, items)

        recyclerView.setAdapter(adapter)


        view.findViewById<Button>(R.id.my_button).setOnClickListener{
            Toast.makeText(context, "버튼클릭", Toast.LENGTH_SHORT).show()
            onSlideViewButtonClick(view)
        }
        return view
    }

    fun test() {
        Toast.makeText(context, "실험~!~~~~~~~~~~~", Toast.LENGTH_SHORT).show()
    }

    fun onSlideViewButtonClick(view: View){
        if(isUp!!) {
            slideDown(myView!!)
            myButton!!.text = "Slide up"
        } else {
            slideUp(myView!!)
            myButton!!.text = "Slide down"
        }
        isUp = !isUp!!
    }

    fun slideDown(view: View){
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun slideUp(view: View){
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0F
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun initDataset() {
        items.clear()
        items.add(Data_addItem(R.drawable.iron_man, "의류"))
        items.add(Data_addItem(R.drawable.spider_man, "세면도구"))
        items.add(Data_addItem(R.drawable.black_panther, "예시1"))
        items.add(Data_addItem(R.drawable.doctor, "예시2"))
        items.add(Data_addItem(R.drawable.hulk, "예시3"))
        items.add(Data_addItem(R.drawable.thor, "예시4"))
        items.add(Data_addItem(R.drawable.thor, "예시5"))
        items.add(Data_addItem(R.drawable.thor, "예시6"))

    }

}
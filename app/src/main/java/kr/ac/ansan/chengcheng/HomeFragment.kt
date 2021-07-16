package kr.ac.ansan.chengcheng

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {
    private val items: ArrayList<item> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "Home프 실행", Toast.LENGTH_SHORT).show()
        //requireActivity().startActivity(Intent(activity, MainActivity::class.java))
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        initDataset()

        val context: Context = view.context
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_list)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter(context, items)

        recyclerView.setAdapter(adapter)

        return view
    }


    private fun initDataset() {
        items.clear()
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행1", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행2", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
        items.add(item("가평여행3", "http://images.khan.co.kr/article/2021/03/19/l_2021031902001158300196291.jpg", "하..."))
    }


    //******************************조건: container view 필요************************

    //액티비티 위에 그려진 프래그먼트를 교체할때는 supportFragmentManager.begineTransaction()을 사용하지만,
    //프래그먼트 위에 그린 프래그먼트를 교체할때는 childFragmentManager.beginTransaction()을 사용해야한다.
    // frontFragment로 전환하는 함수
//    fun front(){
//        Toast.makeText(context, "추가창 실행중", Toast.LENGTH_SHORT).show()
//        childFragmentManager.beginTransaction()
//            .replace(R.id.container_view_add, frontFragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()
//    }
//
//    // backFragment로 전환하는 함수
//    fun back(){
//        childFragmentManager.beginTransaction()
//            .replace(R.id.container_view_home, backFragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()
//    }
    /*
    (백스택)
    parentFragmentManager.beginTransaction().apply{
    replace(R.id.container, fragmment2)
    addToBackStack(null)
    commit()
    }
    */

}
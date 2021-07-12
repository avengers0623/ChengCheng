package kr.ac.ansan.chengcheng

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

}
package kr.ac.ansan.chengcheng

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //requireActivity().startActivity(Intent(activity, my_page::class.java)) 액티비티 이동
        // Inflate the layout for this fragment
        Toast.makeText(context, "설정창 프 실행", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.my_page, container, false)
    }

}
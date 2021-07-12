package kr.ac.ansan.chengcheng

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(context, "ADD프 실행", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_add, container, false)
    }


}
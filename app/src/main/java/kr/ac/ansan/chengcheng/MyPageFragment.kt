package kr.ac.ansan.chengcheng

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.my_page.*


class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //requireActivity().startActivity(Intent(activity, my_page::class.java)) //액티비티 이동
        // Inflate the layout for this fragment
        Toast.makeText(context, "설정창 프 실행", Toast.LENGTH_SHORT).show()
        val view: View = inflater.inflate(R.layout.my_page, container, false)
        val loginSignup = Intent(context,login_signup::class.java)

        view.findViewById<Button>(R.id.logout).setOnClickListener {
            //로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    startActivity(loginSignup)
                }
            }
        }

        return view
    }

}
package kr.ac.ansan.chengcheng

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.ac.ansan.chengcheng.databinding.MainBinding


class MainPage : AppCompatActivity(){

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var binding: MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "MainPage액티비티 실행", Toast.LENGTH_SHORT).show()
        initBinding()
        initNavigation()

//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(
//            R.id.listFrame,
//            HomeFragment()
//        )
//        transaction.commit()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.navController)
        bottomNavigationView = findViewById(R.id.main_nav)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.main)
        binding.lifecycleOwner = this
    }

}
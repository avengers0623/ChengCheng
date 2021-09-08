package kr.ac.ansan.chengcheng

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Test_add_item : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var adapterRV2: test_AdditemRVAdapter2
    var rv2Data: MutableList<Data_addItem_2>? = null
    var spinner_List_test = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_add_item)


        //  카테고리 덩어리
        //나중에 클래스든 어디든 모아놓기
        val chengIcon: TypedArray = resources.obtainTypedArray(R.array.chengIcon)
        val chengList: Array<String> = resources.getStringArray(R.array.chengList)
        val clothesIcon: TypedArray = resources.obtainTypedArray(R.array.clothesIcon)
        val clothesList: Array<String> = resources.getStringArray(R.array.clothesList)
        val electronicIcon: TypedArray = resources.obtainTypedArray(R.array.electronicIcon)
        val electronicList: Array<String> = resources.getStringArray(R.array.electronicList)




        spinner = findViewById(R.id.spinner_test)
        adapterRV2 = test_AdditemRVAdapter2()

        //큰카테고리
        //나중에 스트링배열로 저장하기
        val categoryList: Array<String> = resources.getStringArray(R.array.categoryListName)
        categoryList.forEach {
            spinner_List_test.add(it)
        }

        Log.d("spinner", spinner_List_test.toString())


        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            @SuppressLint("ResourceType")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinner_List_test[position]
                when (selectedItem) {
                    spinner_List_test[0] -> selectedItemSet(chengIcon, chengList)
                    spinner_List_test[1] -> selectedItemSet(clothesIcon, clothesList)
                    spinner_List_test[2] -> selectedItemSet(electronicIcon, electronicList)
//                    spinner_List_test[3] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
//                    spinner_List_test[4] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
//                    spinner_List_test[5] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
//                    spinner_List_test[6] -> selectedItemSet(itemIconList.chengIcon, itemIconList.chengList),
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

        val array_Adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, spinner_List_test
        )
        spinner.adapter = array_Adapter
        spinner.setSelection(0)

        rv2Data = mutableListOf()

        var rv2 = findViewById<RecyclerView>(R.id.rv2)
        rv2.setHasFixedSize(true)
        rv2.adapter = adapterRV2
        rv2Data?.let {
            //배열에 데이터가 있는지 확인 후, 어댑터의 data에 설정
            adapterRV2.data = it
        }
        rv2.layoutManager = GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false)


    }

    private fun selectedItemSet(Icon: TypedArray, List: Array<String>) {
        rv2Data!!.clear()

        for(i in List.indices){
            rv2Data!!.add(Data_addItem_2(Icon.getResourceId(i, 0), List[i]))
        }

        adapterRV2.notifyDataSetChanged()
    }
}


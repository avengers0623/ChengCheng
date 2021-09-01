package kr.ac.ansan.chengcheng

data class checkboxData(var saveMaper: HashMap<Int, MutableSet<Int>>, var saveCompareList: MutableSet<Int>){

    init {
        val category = add_item.typedArrayRecycler
        val max = category!!.length()
        for(i in 0 until max) {
            saveMaper[i] = mutableSetOf()
        }
        saveCompareList = mutableSetOf()
    }
}




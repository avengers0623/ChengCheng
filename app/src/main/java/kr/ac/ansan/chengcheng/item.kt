package kr.ac.ansan.chengcheng

import android.content.ClipData.Item


class item {

    var name: String? = null
    //var photo: String? = null
    var summary: String? = null


    constructor(name: String?, photo: String?, summary: String?) {
        this.name = name
        //this.photo = photo
        this.summary = summary
    }

    //annotation(주석) JvmName:
    //함수 이름과 인자, 리턴 타입이 동일하기 때문에 컴파일 안되는 경우가 있음
    //JvmName을 사용하여 시그니처를 변경하여 컴파일 되도록함
    //코틀린은 코틀린에서 정의된 함수이름을 사용하고 자바는 변경된 이름으로 호출해야함
    @JvmName("getName1")
    open fun getName(): String? {
        return name
    }

//    @JvmName("getPhoto1")
//    open fun getPhoto(): String? {
//        return photo
//    }

    @JvmName("getSummary1")
    open fun getSummary(): String? {
        return summary
    }

}
package kr.ac.ansan.chengcheng

data class Data_items(
    var image: Int = R.drawable.iron_man,
    var title: String = "titleName"){

//    init{
//        this.image = image
//        this.title = title
//    }

    @JvmName("getImage1")
    fun getImage(): Int {
        return image
    }

    @JvmName("setImage1")
    fun setImage(image: Int) {
        this.image = image
    }

    @JvmName("getTitle1")
    fun getTitle(): String? {
        return title
    }

    @JvmName("setTitle1")
    fun setTitle(title: String?) {
        this.title = title!!
    }
}

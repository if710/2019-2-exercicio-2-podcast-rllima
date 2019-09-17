package br.ufpe.cin.android.podcast

data class ItemFeed(val title: String, val link: String, val pubDate: String, val description: String, val downloadLink: String,val imgUrl:String, val durationTime:String) {

    override fun toString(): String {
        return title
    }
}

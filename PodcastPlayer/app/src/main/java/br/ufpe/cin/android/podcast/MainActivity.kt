package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ItemFeedAdapter(listOf(), this)


        doAsync {
            val db = ItemFeedDB.getDb(applicationContext)
            var itemFeedList = listOf<ItemFeed>()

            try{
                var xml = URL("https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml").readText()
                itemFeedList = Parser.parse(xml)

                db.itemFeedDao().addAll(itemFeedList)

            }catch (e:Throwable){
                itemFeedList = db.itemFeedDao().all()
                Log.e("ERRO",e.message.toString())
            }
            uiThread {
                list.adapter = ItemFeedAdapter(itemFeedList, applicationContext)
            }
        }
    }
}

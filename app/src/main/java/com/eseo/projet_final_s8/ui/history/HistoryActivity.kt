package com.eseo.projet_final_s8.ui.history

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.recyclical.datasource.DataSource
import com.afollestad.recyclical.datasource.emptyDataSource
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.eseo.projet_final_s8.R
import com.eseo.projet_final_s8.data.local_preferences.LocalPreferences
import com.eseo.projet_final_s8.databinding.ActivityHistoryBinding
import com.eseo.projet_final_s8.ui.main.MainActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
//    private val dataSource = dataSourceOf(
//        LocationItem("test1") {},
//        LocationItem("test2") {}
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.go_back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.eraseButton.setOnClickListener {
            LocalPreferences.getInstance(this).eraseHistory()
            finish()
            startActivity(intent)
        }

        binding.locationHistoryRecyclerView.setup {
            val dataSource: DataSource<Any> = emptyDataSource()

            for(str in LocalPreferences.getInstance(this@HistoryActivity).getHistory()!!){
                dataSource.add(LocationItem(str, fun () {
                    val uri: String = "geo:0,0?q="+Uri.encode(str)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(intent)
                }))
            }
            withDataSource(dataSource)
            withItem<LocationItem, LocationItemViewHolder>(R.layout.location_item_list) {
                onBind(::LocationItemViewHolder) { index, item ->
                    coord.text = item.coord
                }
                onClick { index ->
                    item.onClick()
                }
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(MainActivity.getStartIntent(this))
        finish()
        return true
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HistoryActivity::class.java)
        }
    }
}
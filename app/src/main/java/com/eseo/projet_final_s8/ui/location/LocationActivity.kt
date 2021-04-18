package com.eseo.projet_final_s8.ui.location

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eseo.projet_final_s8.R
import com.eseo.projet_final_s8.ui.history.HistoryActivity
import com.eseo.projet_final_s8.ui.main.MainActivity

class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HistoryActivity::class.java)
        }
    }
}
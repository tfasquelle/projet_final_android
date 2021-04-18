package com.eseo.projet_final_s8.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eseo.projet_final_s8.databinding.ActivityMainBinding
import com.eseo.projet_final_s8.ui.history.HistoryActivity
import com.eseo.projet_final_s8.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBSettings.setOnClickListener {
            startActivity(SettingsActivity.getStartIntent(this))
        }

        binding.mainBHistory.setOnClickListener {
            startActivity(HistoryActivity.getStartIntent(this))
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

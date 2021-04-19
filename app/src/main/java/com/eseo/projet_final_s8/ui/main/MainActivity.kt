package com.eseo.projet_final_s8.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eseo.projet_final_s8.data.local_preferences.LocalPreferences
import com.eseo.projet_final_s8.ui.location.LocationActivity
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

        binding.mainBLocation.setOnClickListener {
            startActivity(LocationActivity.getStartIntent(this))
        }

        binding.mainImage.setOnClickListener {
            val uri: String = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

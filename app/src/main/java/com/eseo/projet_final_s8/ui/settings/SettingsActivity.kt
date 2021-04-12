package com.eseo.projet_final_s8.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.eseo.projet_final_s8.R
import com.eseo.projet_final_s8.databinding.ActivitySettingsBinding
import com.eseo.projet_final_s8.ui.main.MainActivity

class SettingsActivity : AppCompatActivity() {

    private val dataSource =  dataSourceTypedOf(
        SettingsItem("Feedback", R.drawable.icon_feedback) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:theotim.fasquelle@reseau.eseo.fr")))
        },
        SettingsItem("location settings", R.drawable.icon_location_settings) {
            val intent =
                Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        },
        SettingsItem("application settings", R.drawable.icon_settings) {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        },
        SettingsItem("eseo website", R.drawable.logo_eseo) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr")))
        }
    )

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.go_back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.settingsRV.setup {
            withDataSource(dataSource)
            withItem<SettingsItem, SettingsItemViewHolder>(R.layout.settings_item_list) {
                onBind(::SettingsItemViewHolder) { index, item ->
                    // ViewHolder is `this` here
                    name.text = item.name
                    icon.setImageResource(item.icon)
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
            return Intent(context, SettingsActivity::class.java)
        }
        private val itemList: MutableList<String> = mutableListOf("plouf")
    }
}
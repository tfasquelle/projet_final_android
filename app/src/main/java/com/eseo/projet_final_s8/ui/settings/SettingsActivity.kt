package com.eseo.projet_final_s8.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.eseo.projet_final_s8.R
import com.eseo.projet_final_s8.databinding.ActivitySettingsBinding
import com.eseo.projet_final_s8.ui.main.MainActivity

class SettingsActivity : AppCompatActivity() {

    private val dataSource =  dataSourceTypedOf(
        // les strings sont en dur parce que les extraire produit des null pointer exception
        SettingsItem("Feedback", R.drawable.icon_feedback) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:theotim.fasquelle@reseau.eseo.fr")))
        },
        SettingsItem("location settings", R.drawable.icon_location_settings) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
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
        },
        SettingsItem("eseo address", R.drawable.logo_eseo) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:47.49308895618015, -0.5513870146020509")))
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
        // c'était du debug mais quand je l'ai vu à la fin ça m'a fait rire alors je le laisse
        Toast.makeText(this, "plouf", Toast.LENGTH_SHORT).show()

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
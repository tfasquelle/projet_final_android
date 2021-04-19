package com.eseo.projet_final_s8.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.eseo.projet_final_s8.BuildConfig
import com.eseo.projet_final_s8.R
import com.eseo.projet_final_s8.data.local_preferences.LocalPreferences
import com.eseo.projet_final_s8.databinding.ActivityLocationBinding
import com.eseo.projet_final_s8.ui.main.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.*

class LocationActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_LOCATION = 2121
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocationActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.locateButton.setOnClickListener {
            getLocation()
            binding.frontEndMeme.visibility=android.widget.ImageView.VISIBLE
        }
        supportActionBar?.apply {
            title = getString(R.string.go_back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(MainActivity.getStartIntent(this))
        finish()
        return true
    }


    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),  PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    // création du dialog de demande de permission
                    val dialog = MaterialDialog(this)
                            .title(R.string.need_permission_dialog_title)
                            .message(R.string.need_location_dialog_message)
                            .negativeButton(R.string.No) {
                                startActivity(MainActivity.getStartIntent(this))
                                finish()
                            }
                    //  Permission refusée de menière permanente -> renvoi vers les paramètres
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                        dialog.positiveButton(R.string.OK) {
                            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
                        }.show()
                    } else { // refusé temporairement
                        dialog.positiveButton(R.string.OK) {
                            requestPermission()
                        }.show()
                    }
                }
                return
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            // on utilise les play services pour toujours avoir un résultat
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                    .addOnSuccessListener {
                        if (it != null) {
                            // succcès
                            geoCode(it)
                        } else {
                            // echec (gps désactivé par exmeple)
                            Toast.makeText(this, "Localisation impossible", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Localisation impossible", Toast.LENGTH_SHORT).show()
                    }
        } else {
            requestPermission()
        }
    }




    private fun geoCode(location: Location){
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val loc_eseo = Location("")
        loc_eseo.latitude = 47.49321219360714
        loc_eseo.longitude = -0.5513870095535979
        
        val distance = location.distanceTo(loc_eseo) / 1000
        Toast.makeText(this, "vous êtes à $distance km de l'ESEO", Toast.LENGTH_SHORT).show()
        if (results.isNotEmpty()) {
            val text = results[0].getAddressLine(0)
            binding.locationText.text = text
//            LocalPreferences.getInstance(this).saveStringValue(text)
            LocalPreferences.getInstance(this).addToHistory(text)
        }
    }

}
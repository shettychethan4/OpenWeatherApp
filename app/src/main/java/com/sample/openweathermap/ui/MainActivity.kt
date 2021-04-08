package com.sample.openweathermap.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sample.openweathermap.R
import com.sample.openweathermap.common.*
import com.sample.openweathermap.databinding.ActivityMainBinding
import com.sample.openweathermap.remote.RemoteSource
import com.sample.openweathermap.remote.WeatherResponse
import com.sample.openweathermap.ui.weather.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, GetLocationHandler {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by viewModels()
    private var isConnected: Boolean = true

    @Inject
    lateinit var preference: SharedPreferencesRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkInternet()
        requestPermission()
        dataForLastLocation(preference.getLatitude(), preference.getLongitude())
        observerData()
    }

    private fun checkInternet() {
        isConnected = ConnectivityUtil.isConnected(this)
        if (!isConnected)
            Toast.makeText(
                this,
                getString(R.string.internet),
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun observerData() {
        viewModel.weatherResponse.observe(this, {
            when (it) {
                is RemoteSource.Failure -> {
                    binding.progressbar.hide()
                    binding.button.isEnabled = true
                }
                is RemoteSource.Success -> {
                    binding.progressbar.hide()
                    setData(it.value)
                    binding.button.isEnabled = true
                }
                RemoteSource.Loading -> {
                    binding.progressbar.show()
                }
            }
        })
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return
        }
        requestForPermissionAgain()
    }

    private fun requestForPermissionAgain() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.mandatory_permission),
            REQUEST_CODE_LOCATION_PERMISSION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    private fun saveToSharedPreference(latitude: String, longitude: String) {
        preference.setLatitude(KEY_LATITUDE, latitude)
        preference.setLongitude(KEY_LONGITUDE, longitude)
    }

    private fun subscribeUI(latitude: String, longitude: String) {
        viewModel.observeWeatherData(
            isConnected,
            latitude,
            longitude
        )

    }


    private fun setData(value: WeatherResponse) {
        binding.location.text = value.name
        binding.temp.text = resources.getString(
            R.string.temperature_unit, value.main?.temp?.getTemperatureInString()
        )
        binding.humidity.text = resources.getString(
            R.string.humidity_value,
            value.main?.humidity.toString()
        )
        binding.pressure.text = resources.getString(
            R.string.pressure_value,
            value.main?.pressure.toString()
        )
        binding.wind.text = resources.getString(
            R.string.wind_value,
            value.wind?.speed.toString()
        )
        binding.sunrise.text =
            resources.getString(
                R.string.sunrise_value,
                value.sys?.sunrise?.getTime()
            )
        binding.sunset.text =
            resources.getString(
                R.string.sunset_value,
                value.sys?.sunset?.getTime()
            )
    }

    @SuppressLint("MissingPermission")
    override fun getLocationData() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        binding.button.isEnabled = false
                        subscribeUI(location.latitude.toString(), location.longitude.toString())
                        saveToSharedPreference(
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                    }
                }
            return
        }
        requestForPermissionAgain()
    }

    fun dataForLastLocation(latitude: String?, longitude: String?) {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            if (latitude != null && longitude != null) {
                subscribeUI(latitude, longitude)
            }

            return
        }
        requestForPermissionAgain()

    }


}



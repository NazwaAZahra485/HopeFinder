package com.nazwa.hopefinder.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _hasUnreadNotification = MutableStateFlow(false)
    val hasUnreadNotification: StateFlow<Boolean> = _hasUnreadNotification.asStateFlow()

    private val _earthquakeDetected = MutableStateFlow(false)
    val earthquakeDetected: StateFlow<Boolean> = _earthquakeDetected.asStateFlow()

    private val _earthquakeLocation = MutableStateFlow("Halmahera Barat")
    val earthquakeLocation: StateFlow<String> = _earthquakeLocation.asStateFlow()

    private val _earthquakeDepthKm = MutableStateFlow(10)
    val earthquakeDepthKm: StateFlow<Int> = _earthquakeDepthKm.asStateFlow()

    private val _earthquakeMagnitude = MutableStateFlow(4.5)
    val earthquakeMagnitude: StateFlow<Double> = _earthquakeMagnitude.asStateFlow()

    private val _isAtAssemblyPoint = MutableStateFlow(false)
    val isAtAssemblyPoint: StateFlow<Boolean> = _isAtAssemblyPoint.asStateFlow()

    fun onMenuClick() {
        // TODO: open nav drawer or bottom sheet
    }

    fun onBellClick() {
        // TODO: navigate to NotificationScreen via AppNavigation
        _hasUnreadNotification.value = false
    }

    fun onAlreadyAtAssemblyPoint() {
        _isAtAssemblyPoint.value = true
        // TODO: report location to EarthquakeRepository
    }

    fun onSosClick() {
        // TODO: trigger SOS flow (call, SMS, or push via FirebaseHelper)
    }

    fun onZoomIn()     { /* delegate to MapsSDK controller */ }
    fun onZoomOut()    { /* delegate to MapsSDK controller */ }
    fun onSavePin()    { /* delegate to MapsSDK controller */ }
    fun onMyLocation() { /* delegate to MapsSDK controller */ }

    /** Called by FirebaseHelper or EarthquakeRepository when a new event arrives */
    fun onEarthquakeReceived(
        location: String,
        depthKm: Int,
        magnitude: Double,
    ) {
        _earthquakeLocation.value    = location
        _earthquakeDepthKm.value     = depthKm
        _earthquakeMagnitude.value   = magnitude
        _earthquakeDetected.value    = true
        _hasUnreadNotification.value = true
    }
}
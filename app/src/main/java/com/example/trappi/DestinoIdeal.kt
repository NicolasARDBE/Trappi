package com.example.trappi

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.example.trappi.databinding.ActivityDestinoIdealBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class DestinoIdeal : AppCompatActivity() {
    private lateinit var binding: ActivityDestinoIdealBinding
    private lateinit var geocoder: Geocoder
    lateinit var map : MapView
    var testLocation = GeoPoint(9.93, -84.08)
    val testText = "Costa Rica"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinoIdealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(this,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this))
        binding.destino.text = testText
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        geocoder = Geocoder(this)

        binding.search.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEND){
                val input = binding.search.text.toString()
                val location = findLocation(input)
                if (location != null) {
                    addMarker(location)
                    map.controller.setZoom(8.0)
                    map.controller.animateTo(location)
                }
                true
            }else{
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
        map.controller.setZoom(8.0)
        map.controller.animateTo(testLocation)
        addMarker(testLocation)
    }
    override fun onPause() {
        super.onPause()
        map.onPause()
    }
    fun addMarker(location: GeoPoint){
        //Add Marker
        val markerPoint = GeoPoint(location)
        val marker = Marker(map)
        marker.setTitle(testText)
        val myIcon =
            getResources().getDrawable(R.drawable.baseline_location_on_24, this.getTheme())
        marker.setIcon(myIcon)
        marker.setPosition(markerPoint)
        marker.setAnchor(Marker.ANCHOR_CENTER,
            Marker.ANCHOR_BOTTOM)
        map.getOverlays().add(marker)
    }

    fun findLocation(address : String):GeoPoint?{
        val addresses = geocoder.getFromLocationName(address, 2)
        if(addresses != null && !addresses.isEmpty()){
            val addr = addresses.get(0)
            val location = GeoPoint(addr.latitude, addr.longitude)
            return location
        }
        return null
    }
}
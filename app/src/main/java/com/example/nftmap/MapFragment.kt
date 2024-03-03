package com.example.nftmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import com.example.nftmap.api.PostsService
import com.example.nftmap.databinding.FragmentMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.net.URL


class MapFragment : Fragment(), MapListener, LocationListener {
    private lateinit var mMap: MapView
    private lateinit var controller: IMapController
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        Configuration.getInstance().load(
            requireActivity().applicationContext,
            requireActivity().getSharedPreferences(
                getString(R.string.app_name),
                AppCompatActivity.MODE_PRIVATE
            )
        )
        addMap(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.mapCenter?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.mapCenter?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

    fun addMap(mapFragment: MapFragment) {
    mapFragment.apply {

        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())


        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireActivity()), mMap)
        controller = mMap.controller

        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        val location = GeoPoint(53.647894000835585,10.023651123046877)
        controller.setCenter(location)
        controller.animateTo(location)

        mMyLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                controller.setCenter(mMyLocationOverlay.myLocation)
                controller.animateTo(mMyLocationOverlay.myLocation)
            }
        }
// val mapPoint = GeoPoint(latitude, longitude)
        controller.setZoom(6.0)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")

// controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)

        mMap.addMapListener(this@MapFragment)
        runBlocking {

            addMarker(mMap)
        }
    }
    }

    private suspend fun addMarker(mMap: MapView){

        withContext(Dispatchers.Default){
            val postService = PostsService.create()
            postService.getPosts().forEach(){
                Marker(mMap).apply {
                    this.position = GeoPoint(it.lat, it.long)
                    this.title = it.nftName
                    val url = URL(it.nftSourceImage)
                /*    val opt = BitmapFactory.Options()
                    opt.inMutable = true*/
                    val bitmap =  BitmapFactory.decodeStream(url.openConnection().getInputStream(),/*null, opt*/)
                   /* bitmap.scale(60,60)*/
                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap!!, 60, 60, false)

                    icon = BitmapDrawable(this@MapFragment.resources, scaledBitmap)

                     this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    mMap.overlays.add(this)
                }
            }
        }
    }
}



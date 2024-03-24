package com.example.nftmap.ui


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.nftmap.api.PostsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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


@Composable
fun MapScreen(){
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val controller = mapView.controller
    val mMyLocationOverlay = remember { MyLocationNewOverlay(GpsMyLocationProvider(context), mapView) }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                // Creates the view
               mapView.apply {
                    // Do anything that needs to happen on the view init here
                    // For example set the tile source or add a click listener
                   Configuration.getInstance().userAgentValue = "MyApp/1.0"
                    setTileSource(TileSourceFactory.MAPNIK)
                     setMultiTouchControls(true)
                     getLocalVisibleRect(Rect())

                   controller.setZoom(10.0)
                   addMapListener(
                       MyML()
                   )

                   MyLocationNewOverlay(GpsMyLocationProvider(context), this)
                   mMyLocationOverlay.enableMyLocation()
                   mMyLocationOverlay.enableFollowLocation()
                   mMyLocationOverlay.isDrawAccuracyEnabled = true

                   overlays.add(mMyLocationOverlay)


                   GlobalScope.launch(Dispatchers.IO) {

                       addMarker(this@apply)
                   }
                }
            },
            update = { view ->
                view.controller.setCenter(mMyLocationOverlay.myLocation)
                view.controller.animateTo(mMyLocationOverlay.myLocation)

            }
        )
}


class MyML:MapListener {
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

                icon = BitmapDrawable(Resources.getSystem(), scaledBitmap)


                this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                mMap.overlays.add(this)
            }
        }
    }
}

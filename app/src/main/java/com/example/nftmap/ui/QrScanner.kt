package com.example.nftmap.ui

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nftmap.api.NftMarker
import com.example.nftmap.api.PostsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch




@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun QrScanner(){
    lateinit var codeScanner: CodeScanner
    val context = LocalContext.current
    var readQr by remember { mutableStateOf(false) }
    var mintMarker by remember {
        mutableStateOf(""
        )
    }
    if(!readQr){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                 CodeScannerView(context).apply {
                     codeScanner = CodeScanner(context,this)
                     // Parameters (default values)
                     codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
                     codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
                     // ex. listOf(BarcodeFormat.QR_CODE)
                     codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
                     codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
                     codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
                     codeScanner.isFlashEnabled = false // Whether to enable flash or not
                     codeScanner.decodeCallback = DecodeCallback {

                             GlobalScope.launch(Dispatchers.IO) {
                                 // Perform network operation in a background thread
                                 val postService = PostsService.create()
                                 val nft = postService.getNft(it.text)
                                 if(nft != null) {
                                     val  mLocationManager =
                                         context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                                     val location: Location? = mLocationManager.getLastKnownLocation(
                                         LocationManager.GPS_PROVIDER)
                                     val latitude = location!!.latitude
                                     val longitude = location.longitude
                                     val dist: FloatArray = FloatArray(1)
                                     launch(Dispatchers.Main) {
                                         Location.distanceBetween(nft.long, nft.lat, longitude, latitude, dist)
                                         Toast.makeText(context,dist[0].toString(), Toast.LENGTH_LONG).show()
                                         if(dist[0]/1000 < 1){
                                             mintMarker = nft.nftSourceImage
                                                 readQr = !readQr
                                         } else {
                                             Toast.makeText(context, "Failure, please scan again", Toast.LENGTH_LONG).show()
                                         }
                                     }


                                 }

                                 // Update UI on the main thread

                             }

                 }

            }
            }, update = {
                codeScanner.startPreview()
            }
        )
    }
    } else {
        Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
            if(readQr){
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = mintMarker ,
                contentDescription = ""
            )}
        }
    }
}





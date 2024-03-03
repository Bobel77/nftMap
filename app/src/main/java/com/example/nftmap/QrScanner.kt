package com.example.nftmap

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.nftmap.api.PostsService
import com.example.nftmap.databinding.FragmentQrScannerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class QrScanner : Fragment(), LocationListener {
    private var _binding: FragmentQrScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var codeScanner: CodeScanner
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val activity = requireActivity()
        codeScanner = CodeScanner(activity, binding.scannerView)
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                if (ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissions(
                        arrayListOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,)
                    )
                }
                GlobalScope.launch(Dispatchers.IO) {
                    // Perform network operation in a background thread
                    val postService = PostsService.create()
                    val nft = postService.getNft(it.text)
                    if(nft != null) {
                        val  mLocationManager =
                            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val location: Location? = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        val latitude = location!!.latitude
                        val longitude = location.longitude
                        val dist: FloatArray = FloatArray(1)
                        launch(Dispatchers.Main) {
                            Location.distanceBetween(nft.long, nft.lat, longitude, latitude, dist)
                            Toast.makeText(requireContext(),dist[0].toString(), Toast.LENGTH_LONG).show()
                            if(dist[0]/1000 < 1){
                                   val intent = Intent(requireActivity(), QrNft::class.java)
                                   intent.putExtra("imageNft", nft.nftSourceImage)
                                   requireActivity().startActivity(intent)
                            } else {
                                Toast.makeText(requireContext(), "Failure, please scan again", Toast.LENGTH_LONG).show()
                            }
                        }


                    }

                    // Update UI on the main thread

                }


            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }
    private val PERMISSION_REQUEST_CODE = 133
    private fun checkPermissions(permissionsToRequest: ArrayList<String>) {
        val permissionsToBeRequested = ArrayList<String>()

        for (permission in permissionsToRequest) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToBeRequested.add(permission)
            }
        }

        if (permissionsToBeRequested.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToBeRequested.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            Toast.makeText(requireContext(), "Alle Berechtigungen erteilt", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                var allPermissionsGranted = true
                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false
                        break
                    }
                }
                if (allPermissionsGranted) {
                    Toast.makeText(requireContext(), "Alle Berechtigungen erteilt", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Nicht alle Berechtigungen erteilt", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}


package com.example.nftmap;


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nftmap.navigation.AppNavigation
/*import com.example.nftmap.nftwallet.test*/
import com.example.nftmap.nftwallet.web3m

class MainActivity : AppCompatActivity() {
    private val myPermissions = arrayListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.INTERNET
    )
    private val PERMISSION_REQUEST_CODE = 123

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web3m(this@MainActivity)

        setContent {

            checkPermissions(myPermissions)
                 AppNavigation()
           /*   test()*/

        }

    }

      private fun checkPermissions(permissionsToRequest: ArrayList<String>) {
        val permissionsToBeRequested = ArrayList<String>()

        for (permission in permissionsToRequest) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToBeRequested.add(permission)
            }
        }

        if (permissionsToBeRequested.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToBeRequested.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            Toast.makeText(this, "Alle Berechtigungen erteilt", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this, "Alle Berechtigungen erteilt", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Nicht alle Berechtigungen erteilt", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}



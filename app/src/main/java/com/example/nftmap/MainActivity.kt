package com.example.nftmap;

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.nftmap.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.nftmap.navigation.AppNavigation
import com.example.nftmap.nftwallet.web3m
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState
import com.walletconnect.web3.modal.ui.components.internal.Web3ModalComponent
import com.walletconnect.web3.modal.ui.openWeb3Modal
import kotlinx.coroutines.launch

/*
class MainActivity : AppCompatActivity() {

    private val myPermissions = arrayListOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.INTERNET
    )
    private lateinit var binding: ActivityMainBinding

    private val PERMISSION_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_web3, R.id.navigation_qrScan
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkPermissions(myPermissions)
        web3m(this)
   *//*    navController.openWeb3Modal(
            shouldOpenChooseNetwork = false

        )*//*

    }*/
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



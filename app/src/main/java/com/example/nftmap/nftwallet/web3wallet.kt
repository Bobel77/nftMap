package com.example.nftmap.nftwallet

import android.widget.Toast
import com.example.nftmap.MainActivity
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets

 fun web3m(mainActivity: MainActivity) {

    val recommendedWalletsIds = listOf<String>(
        "1ae92b26df02f0abca6304df07debccd18262fdf5fe82daa81593582dac9a369",
        "4622a2b2d6af1c9844944291e5e7351a6aa24cd7b23099efac1b2fd875da31a0",
        "c57ca95b47569778a828d19178114f4db188b89b763c899ba0be274e97267d96"
    )
    val connectionType = ConnectionType.AUTOMATIC
    val projectId = "8090b99a1d0a2239c932d9f769879066"
    val relayUrl = "relay.walletconnect.com"
    val serverUrl = "wss://$relayUrl?projectId=${projectId}"
    val appMetaData = Core.Model.AppMetaData(
        name = "Kotlin.Web3Modal",
        description = "Kotlin Web3Modal Implementation",
        url = "kotlin.walletconnect.com",
        icons = listOf("https://raw.githubusercontent.com/WalletConnect/walletconnect-assets/master/Icon/Gradient/Icon.png"),
        redirect = "kotlin-web3modal://request"
    )

    CoreClient.initialize(
        relayServerUrl = serverUrl,
        connectionType = connectionType,
        application = mainActivity.application,
        metaData = appMetaData,
        onError = {}
    )

    val initParams = Modal.Params.Init(
        core = CoreClient,
        recommendedWalletsIds = recommendedWalletsIds
    )
    Web3Modal.initialize(
        init = initParams,
        onSuccess = {
            Web3Modal.setChains(Web3ModalChainsPresets.ethChains.values.toList())
        },
        onError = {
            Toast.makeText(mainActivity, "Failed to initialize Web3Modal", Toast.LENGTH_LONG).show()
        }
    )
}
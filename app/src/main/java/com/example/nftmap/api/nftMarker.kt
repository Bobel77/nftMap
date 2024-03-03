package com.example.nftmap.api

import kotlinx.serialization.Serializable

@Serializable
data class NftMarker(
    val id: Int?,
    val lat: Double,
    val long: Double,
    val markerInfo: String,
    val nftSourceImage: String,
    val nftName: String,
    val key: String,
    val wallet: String,
    var country: String?)
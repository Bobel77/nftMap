package com.example.nftmap.api


import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.json


interface PostsService{
    suspend fun testPost(): String
    suspend fun getPosts(): List<NftMarker>
    suspend fun createPost(postRequest: NftMarker): NftMarker?
    suspend fun getNft(id: String): NftMarker?

    companion object {
        fun create(): PostsService{
            return PostsServiceImpl(
                client = HttpClient(Android){
                install(ContentNegotiation){
                    json()
                }
                }
            )
        }
    }
}
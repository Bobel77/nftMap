package com.example.nftmap.nftwallet



import android.R
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


interface UserNftsService{
   suspend fun fillNfts(): MutableList<NftsOwned>

    companion object {
        fun create(): UserNfts {
            return UserNfts (HttpClient(Android){
                install(ContentNegotiation){
                    json(Json {
                        ignoreUnknownKeys = true
                   /*     coerceInputValues = true*/
                    } )
                }
            })
        }
    }
}


class UserNfts(
    private val client: HttpClient
) : UserNftsService {


 override suspend fun fillNfts(
     /* user: String =  "0x341C7e53f7Eac085e5F3a22D87595CD906428b9d"*/
 ): MutableList<NftsOwned> {

     val user = "0x341C7e53f7Eac085e5F3a22D87595CD906428b9d"
     val apiKey = "oePN0h4P8Iff4BgZV9y3ZA0SGNdbtvK4"
     val apiUrl = "https://eth-mainnet.g.alchemy.com/nft/v3/$apiKey/getNFTsForOwner?owner=$user&withMetadata=true&pageSize=100"

     return runBlocking {
      val listNfts = mutableListOf<NftsOwned>()
     val response = client.get  {url(apiUrl)
     }.bodyAsText()

         val gson = Gson()

         // JSON-String in JsonObject konvertieren
         val jsonObject = gson.fromJson(response.trimIndent(), JsonObject::class.java)

         // Extrahiere die gewünschten Felder
         val ownedNfts = jsonObject.getAsJsonArray("ownedNfts")
         ownedNfts.forEach { nft ->
        try {
              nft.asJsonObject.run {


                  listNfts.add(

                    NftsOwned(
                        name =  get("raw").asJsonObject.get("metadata").asJsonObject.get("name").asString?: "fail",
                        source = get("image").asJsonObject.get("cachedUrl").asString?: "fail"
                    )
                )
              }

        } catch (e:Exception){}

         }
          return@runBlocking listNfts
     }
     /*try {
         client.get {url(  apiUrl)
         }.body<OwnedNftsResponse>().apply {
             Log.d("API Answer",this@apply.toString())
         }
     }
     catch (e: Exception) {
         Log.e("API_ERROR", "Error fetching NFTs", e)
         null
     }*/
 /*    val user: String =  "0x341C7e53f7Eac085e5F3a22D87595CD906428b9d"
       val APIKey = "oePN0h4P8Iff4BgZV9y3ZA0SGNdbtvK4"
       var data:  OwnedNftsResponse? = null
*//*https://eth-mainnet.g.alchemy.com/nft/v3/oePN0h4P8Iff4BgZV9y3ZA0SGNdbtvK4/getNFTsForOwner?owner=0x341C7e53f7Eac085e5F3a22D87595CD906428b9d&withMetadata=true&pageSize=100"*//*
   return try {
       client.get {url(
           "https://eth-mainnet.g.alchemy.com/nft/v3/$APIKey/getNFTsForOwner?owner=$user&withMetadata=true&pageSize=100")
        }.body<OwnedNftsResponse>() }
        catch (_:Exception){null}*/
 }
}

package com.example.nftmap.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostsServiceImpl(
    private val client: HttpClient
) : PostsService {
    override suspend fun testPost(): String {
        return try {
            client.get {url(HttpRoutes.POSTS) }.bodyAsText()
        }catch (e:Exception){
            "fail"
        }
    }
    override suspend fun getPosts(): List<NftMarker> {
       return try { client.get {url(HttpRoutes.POSTS) }.body<List<NftMarker>>()

       } catch (e:RedirectResponseException){
           //3xx
           println("Error: ${e.response.status.description}")
           emptyList()
       }
       catch (e:ClientRequestException){
           //4xx
           println("Error: ${e.response.status.description}")
           emptyList()
       }
        catch (e:Exception) {
        println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getNft(id: String): NftMarker? {
        return try { client.get {url(HttpRoutes.GETNFT + "$id") }.body<NftMarker>()

        } catch (e:RedirectResponseException){
            //3xx
            println("Error: ${e.response.status.description}")
            null
        }
        catch (e:ClientRequestException){
            //4xx
            println("Error: ${e.response.status.description}")
            null
        }
        catch (e:Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun createPost(postRequest: NftMarker): NftMarker? {
       return try {
            client.post {
                url(HttpRoutes.UPLOAD)
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }
           null
       } catch (e:RedirectResponseException){
           //3xx
           println("Error: ${e.response.status.description}")
          null
       }
       catch (e:ClientRequestException){
           //4xx
           println("Error: ${e.response.status.description}")
           null
       }
       catch (e:Exception) {
           println("Error: ${e.message}")
           null
       }
    }
}
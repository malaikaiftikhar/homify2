package com.example.homify

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// 🔹 Replace this with your Cloudinary cloud name
private const val CLOUD_NAME = "dt4u5zmmr"

// 🔹 Your unsigned upload preset from Cloudinary settings
const val UPLOAD_PRESET = "Malaikafatima"

interface CloudinaryApi {
    @Multipart
    @POST("v1_1/$CLOUD_NAME/image/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): Call<CloudinaryResponse>
}

// 🔹 Cloudinary upload success response
data class CloudinaryResponse(
    val secure_url: String // This is the URL you’ll use to display the image
)

// 🔹 Retrofit client to call Cloudinary API
object RetrofitClient {
    val api: CloudinaryApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.cloudinary.com/")  // Added https://
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApi::class.java)
    }

}

package com.example.homify


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.homify.RetrofitClient
import com.example.homify.UPLOAD_PRESET
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun uploadImageToCloudinary(file: File, context: Context, onSuccess: (String) -> Unit) {
    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
    val preset = RequestBody.create("text/plain".toMediaTypeOrNull(), UPLOAD_PRESET)

    val call = RetrofitClient.api.uploadImage(body, preset)

    call.enqueue(object : Callback<com.example.homify.CloudinaryResponse> {
        override fun onResponse(
            call: Call<com.example.homify.CloudinaryResponse>,
            response: Response<com.example.homify.CloudinaryResponse>
        ) {
            if (response.isSuccessful) {
                val imageUrl = response.body()?.secure_url
                if (imageUrl != null) {
                    onSuccess(imageUrl)
                } else {
                    Toast.makeText(context, "Failed to get image URL", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<com.example.homify.CloudinaryResponse>, t: Throwable) {
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            Log.e("Cloudinary", "Upload error", t)
        }
    })
}

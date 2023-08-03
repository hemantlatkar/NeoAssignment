package com.example.neoassignment.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns

class FileNameFromUri {

    companion object{

        @SuppressLint("Range")
        fun getFileNameFromUri(contentResolver: ContentResolver, uri: Uri): String? {
            var fileName: String? = null
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
            return fileName
        }
    }
}
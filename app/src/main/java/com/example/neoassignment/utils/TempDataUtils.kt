package com.example.neoassignment.utils

import android.content.Context
import com.example.neoassignment.model.ResponseData
import com.example.neoassignment.model.ResponseDataItem
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.*
import java.nio.charset.Charset

class TempDataUtils {

    companion object {

        fun readJsonFile(context: Context): ResponseData? {
            var jsonObj: ResponseData? = null // Change 'val' to 'var' to allow reassignment
            val gson = Gson()
            val fileName = "questions_json.json"

            try {
                val inputStream: InputStream = context.assets.open(fileName)
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val jsonString = String(buffer, Charset.forName("UTF-8"))
                jsonObj = gson.fromJson(jsonString, ResponseData::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return jsonObj
        }
    }
}
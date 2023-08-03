package com.example.neoassignment.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.neoassignment.api.QuestionService
import com.example.neoassignment.model.ResponseData
import com.example.neoassignment.utils.NetworkUtils
import com.example.neoassignment.utils.TempDataUtils

class QuestionRepository(private val questionService: QuestionService,
    private val applicationContext: Context) {

    private val questionsLiveData = MutableLiveData<ResponseData>()

    val questions: MutableLiveData<ResponseData>
    get() = questionsLiveData

    /***
     * Network condition handle to fetch questionList
     * @getQuestions will Fetch from web service when network available
     * And Fetch from asset folder when offline
     */
    suspend fun getQuestions() : ResponseData? {
        if(NetworkUtils.isInternetAvailable(applicationContext)){
            return questionService.getQuestions().body()
        } else{
            return TempDataUtils.readJsonFile(applicationContext)
        }
    }
}
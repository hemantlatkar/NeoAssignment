package com.example.neoassignment.api

import com.example.neoassignment.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET

interface QuestionService {

    @GET("api/v2/customerQuestionnaireMaster")
    suspend fun getQuestions() : Response<ResponseData>

}
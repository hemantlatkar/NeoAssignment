package com.example.neoassignment

import android.app.Application
import com.example.neoassignment.api.QuestionService
import com.example.neoassignment.api.RetrofitHelper
import com.example.neoassignment.repository.QuestionRepository

class DemoApplication : Application() {

    lateinit var questionRepository: QuestionRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val questionService = RetrofitHelper.getInstance().create(QuestionService::class.java)
        questionRepository = QuestionRepository(questionService, applicationContext)
    }
}
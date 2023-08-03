package com.example.neoassignment.viewmodels

import com.example.neoassignment.model.ResponseData

sealed class DataState {
    object Loading : DataState()
    data class Success(val data: ResponseData) : DataState()
    data class Error(val message: String) : DataState()
}
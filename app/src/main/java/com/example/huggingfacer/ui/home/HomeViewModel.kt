package com.example.huggingfacer.ui.home

import HuggingFaceModelResponse
import HuggingFaceService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class HomeViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://huggingface.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(HuggingFaceService::class.java)

    private val _models = MutableStateFlow<List<HuggingFaceModelResponse>>(emptyList())
    val models: StateFlow<List<HuggingFaceModelResponse>> = _models

    init {
        fetchModels()
    }

    private fun fetchModels() {
        viewModelScope.launch {
            try {
                _models.value = apiService.fetchModels()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

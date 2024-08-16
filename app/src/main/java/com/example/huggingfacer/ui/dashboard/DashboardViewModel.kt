package com.example.huggingfacer.ui.dashboard

import HuggingFaceService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huggingfacer.Services.HuggingFaceDailyPaper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://huggingface.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(HuggingFaceService::class.java)

    private val _dailyPapers = MutableStateFlow<List<HuggingFaceDailyPaper>>(emptyList())
    val dailyPapers: StateFlow<List<HuggingFaceDailyPaper>> = _dailyPapers

    init {
        fetchDailyPapers()
    }

    private fun fetchDailyPapers() {
        viewModelScope.launch {
            try {
                _dailyPapers.value = apiService.getDailyPapers()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

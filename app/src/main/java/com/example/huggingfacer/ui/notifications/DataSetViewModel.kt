package com.example.huggingfacer.ui.notifications
import HuggingFaceDataSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataSetViewModel : ViewModel() {

    private val _dataSets = MutableStateFlow<List<HuggingFaceDataSet>>(emptyList())
    val dataSets: StateFlow<List<HuggingFaceDataSet>> = _dataSets

    init {
        fetchDataSets()
    }

    private fun fetchDataSets() {
        viewModelScope.launch {
            try {
                val fetchedDataSets = HuggingFaceApi.service.fetchDataSets(
                    search = null,
                    author = null,
                    filter = null,
                    sort = null,
                    direction = null,
                    limit = null,
                    full = null,
                    config = null
                )
                _dataSets.value = fetchedDataSets
            } catch (e: Exception) {
                // Handle errors here
            }
        }
    }
}

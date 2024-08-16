package com.example.huggingfacer.ui.home
import HuggingFaceModelResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HuggingFaceModelsPage(homeViewModel)
            }
        }
    }
}

@Composable
fun HuggingFaceModelsPage(homeViewModel: HomeViewModel = viewModel()) {
    val models by homeViewModel.models.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Hugging Face Models") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (models.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                ModelsList(models = models)
            }
        }
    }
}

@Composable
fun ModelsList(models: List<HuggingFaceModelResponse>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(models) { model ->
            ModelItem(model)
        }
    }
}

@Composable
fun ModelItem(model: HuggingFaceModelResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = model.name ?: "Unknown Name",  // Handle null case
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "ID: ${model.id ?: "N/A"}",  // Handle potential null case for ID as well
                style = MaterialTheme.typography.body2
            )
        }
    }
}

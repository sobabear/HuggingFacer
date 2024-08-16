package com.example.huggingfacer.ui.home
import HuggingFaceModelResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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
    if (models.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        ModelsList(models = models)
    }
}

@Composable
fun ModelsList(models: List<HuggingFaceModelResponse>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.background(Color.Red)
    ) {
        items(models) { model ->
            ModelItem(model)
        }
    }
}

@Composable
fun ModelItem(model: HuggingFaceModelResponse) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formattedDate = model.createdAt?.let { createdAt ->
        dateFormat.parse(createdAt)?.let { date ->
            outputFormat.format(date)
        } ?: "N/A"
    } ?: "N/A"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(10.dp))
            .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = model.modelId ?: "Unknown Model ID",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = model.tags.joinToString(", ") { it },
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            Text(
                text = model.libraryName ?: "Unknown Library",
                style = MaterialTheme.typography.body2,
                color = Color.DarkGray
            )
            Text(
                text = "Updated: $formattedDate",
                style = MaterialTheme.typography.body2,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Downloads",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${model.downloads} downloads",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Likes",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${model.likes ?: 0} likes",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

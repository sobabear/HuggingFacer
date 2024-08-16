package com.example.huggingfacer.ui.notifications
import HuggingFaceDataSet
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DataSetListScreen(viewModel: NotificationsViewModel = viewModel()) {
    val dataSets by viewModel.dataSets.collectAsState()

    if (dataSets.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dataSets) { dataSet ->
                DataSetItem(dataSet)
            }
        }
    }
}

@Composable
fun DataSetItem(dataSet: HuggingFaceDataSet) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = dataSet.id,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            dataSet.tags?.let {
                Text(
                    text = it.joinToString(", "),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = dataSet.description ?: "No description available",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Downloads"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${dataSet.downloads ?: 0} downloads",
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Likes"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${dataSet.likes ?: 0} likes",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

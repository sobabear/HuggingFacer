package com.example.huggingfacer.ui.dashboard

import HuggingFacePaperCard
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huggingfacer.databinding.FragmentDashboardBinding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.huggingfacer.R
import com.example.huggingfacer.Services.HuggingFaceDailyPaper

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                DashboardScreen(dashboardViewModel) { url ->
                    openPaperInWebView(url)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openPaperInWebView(url: String) {
        val bundle = Bundle().apply {

            putString("url", url) // You can rename this key if needed
        }
        findNavController().navigate(R.id.webViewFragment, bundle)
    }
}

@Composable
fun DashboardScreen(dashboardViewModel: DashboardViewModel, onPaperClick: (String) -> Unit) {
    val papers by dashboardViewModel.dailyPapers.collectAsState()

    if (papers.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        PaperList(papers = papers, onPaperClick = onPaperClick)
    }
}

@Composable
fun PaperList(papers: List<HuggingFaceDailyPaper>, onPaperClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(papers) { paper ->
            HuggingFacePaperCard(paper = paper, onClick = {
                val id = paper.paper.id
                onPaperClick("https://arxiv.org/pdf/$id")
            })
        }
    }
}

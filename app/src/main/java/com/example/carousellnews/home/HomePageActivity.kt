package com.example.carousellnews.home

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.carousellapp.databinding.ActivityMainBinding
import com.example.carousellnews.util.gone
import com.example.carousellnews.util.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : ComponentActivity() {

    private val homePageViewModel by viewModels<HomePageViewModel>()
    private val hpNewsAdapter by lazy { HPNewsAdapter() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvNews.adapter = hpNewsAdapter
        subscribeUI()
    }

    private fun subscribeUI() {
        homePageViewModel.uiStateLd.observe(this) { uiState ->
            binding.apply {
                when(uiState) {
                    is UIState.Success ->  {
                        binding.rvNews.setVisible()
                        binding.viewStubPb.gone()
                        hpNewsAdapter.submitList(uiState.newsList)
                    }
                    is UIState.Error -> {
                        viewStubError.setVisible()
                        viewStubPb.gone()
                    }

                    is UIState.Loading -> {
                        viewStubPb.setVisible()
                        viewStubError.gone()
                    }
                }
            }
        }
    }
}

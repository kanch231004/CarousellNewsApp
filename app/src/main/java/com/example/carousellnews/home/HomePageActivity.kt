package com.example.carousellnews.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.carousellapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : ComponentActivity() {

    private val homePageViewModel by viewModels<HomePageViewModel>()
    private val hpNewsAdapter by lazy { HPNewsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvNews.adapter = hpNewsAdapter
        subscribeUI()
    }

    private fun subscribeUI() {
        homePageViewModel.uiStateLd.observe(this) { uiState ->
            when(uiState) {
                is UIState.Success ->  {
                    hpNewsAdapter.submitList(uiState.newsList)
                }
                is UIState.Error -> {
                    //show Error message
                }

                is UIState.Loading -> {
                    // showLoading
                }
            }
        }
    }
}

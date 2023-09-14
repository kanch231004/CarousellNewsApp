package com.example.carousellnews.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.carousellapp.R
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
        setUpStatusBar()
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

    private fun setUpStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.colorPrimaryDark, theme)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_recent -> {
                homePageViewModel.sortBy(SortType.RECENT)
                true
            }

            R.id.action_popular -> {
                homePageViewModel.sortBy(SortType.RANK)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

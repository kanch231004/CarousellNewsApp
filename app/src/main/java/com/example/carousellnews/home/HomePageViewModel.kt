package com.example.carousellnews.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carousellnews.api.Result
import com.example.carousellnews.data.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val homePageRepository: HomePageRepository) :
    ViewModel() {
    private lateinit var newsList: List<NewsItem>
    private var uiStateLiveData = MutableLiveData<UIState>()
    val uiStateLd: LiveData<UIState>
        get() = uiStateLiveData

    /** Made this as private and the val newsFlow as public so this can't be modified from outside */

    init {
        uiStateLiveData.postValue(UIState.Loading)
        viewModelScope.launch {
            when (val result = homePageRepository.getNews()) {
                is Result.Success -> {
                    //sorted by recent news
                    newsList = result.body.sortedByDescending { it.timeCreated }
                    uiStateLiveData.postValue(UIState.Success(newsList))
                }
                is Result.Error -> {
                    uiStateLiveData.postValue(UIState.Error(result.errorBody))
                }
            }
        }
    }

    fun sortBy(sortType: SortType) {
        newsList = when(sortType) {
            SortType.RANK -> {
                newsList.sortedBy { it.rank }
            }

            SortType.RECENT -> {
                newsList.sortedByDescending { it.timeCreated }
            }
        }
        uiStateLiveData.postValue(UIState.Success(newsList))
    }
}

sealed class UIState {
    data class Success(val newsList: List<NewsItem>) : UIState()
    data class Error(val errorMessage: String? = "") : UIState()
    object Loading : UIState()
}

enum class SortType {
    RANK, RECENT
}
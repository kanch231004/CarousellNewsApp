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
class HomePageViewModel @Inject constructor(private val homePageRepository: HomePageRepository): ViewModel() {
    private var uiStateLiveData = MutableLiveData<UIState>()
    val uiStateLd : LiveData<UIState>
        get() = uiStateLiveData

    /** Made this as private and the val newsFlow as public so this can't be modified from outside */

    init {
        viewModelScope.launch {
            when(val result = homePageRepository.getNews()) {
                is Result.Success -> uiStateLiveData.postValue(UIState.Success(result.body))
                is Result.Error -> uiStateLiveData.postValue(UIState.Error(result.errorBody))
            }
        }
    }
}

sealed class UIState {
    data class Success(val newsList: List<NewsItem>): UIState()
    data class Error(val errorMessage: String?): UIState()
    object Loading: UIState()
}
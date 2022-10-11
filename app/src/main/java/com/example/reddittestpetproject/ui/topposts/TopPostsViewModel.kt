package com.example.reddittestpetproject.ui.topposts

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reddittestpetproject.domain.Models
import com.example.reddittestpetproject.repository.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopPostsViewModel @Inject constructor(
    application: Application,
    private val repository: RedditRepository
) : AndroidViewModel(application) {

    private val _navigateToSelected = MutableLiveData<Models.Children>()
    val navigateToSelected: LiveData<Models.Children>
        get() = _navigateToSelected

    init {
        viewModelScope.launch {
            repository.refreshChildren()
        }
    }

    val childrenList = repository.childrenList

    fun displayDetail(children: Models.Children){
        _navigateToSelected.value = children
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun displayDetailComplete(){
        _navigateToSelected.value = null
    }
}
package com.thepascal.todolist.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.R

class MainViewModel: ViewModel() {
    //var navDrawerDisplaySelection = R.id.nav_home

    var navDrawerDisplaySelectionName =
        "com.thepascal.todoList.MainViewModel.navDrawerDisplaySelection"

    private val _navDrawerDisplaySelection = MutableLiveData<Int>().apply {
        value = R.id.nav_home
    }
    var navDrawerDisplaySelection: LiveData<Int> = _navDrawerDisplaySelection
}
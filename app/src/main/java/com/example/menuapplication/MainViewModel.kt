package com.example.menuapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val menuItems = arrayListOf(
        MenuItem(R.drawable.baseline_home_24),
        MenuItem(R.drawable.baseline_send_24),
        MenuItem(R.drawable.baseline_settings_24),
        MenuItem(R.drawable.baseline_access_time_24),
        MenuItem(R.drawable.baseline_find_in_page_24)
    )

    val recyclerViewVisible = MutableLiveData<Boolean>()
    fun getItems() = menuItems

}
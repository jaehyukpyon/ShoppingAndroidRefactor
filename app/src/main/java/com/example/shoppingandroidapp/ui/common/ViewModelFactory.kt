package com.example.shoppingandroidapp.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingandroidapp.AssetLoader
import com.example.shoppingandroidapp.repository.HomeAssetDataSource
import com.example.shoppingandroidapp.repository.HomeRepository
import com.example.shoppingandroidapp.ui.home.HomeViewModel
import kotlin.IllegalArgumentException

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val repository = HomeRepository(HomeAssetDataSource(AssetLoader(context)))
            return HomeViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }

}
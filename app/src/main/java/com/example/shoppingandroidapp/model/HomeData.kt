package com.example.shoppingandroidapp.model

import com.example.shoppingandroidapp.Banner
import com.google.gson.annotations.SerializedName

data class HomeData(
    val title: Title,
    @SerializedName("top_banners") val topBanners: List<Banner>
)
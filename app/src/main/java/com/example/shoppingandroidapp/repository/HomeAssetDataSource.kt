package com.example.shoppingandroidapp.repository

import com.example.shoppingandroidapp.AssetLoader
import com.example.shoppingandroidapp.model.HomeData
import com.google.gson.Gson

class HomeAssetDataSource(private val assetLoader: AssetLoader) : HomeDataSource {

    private val gson = Gson()

    override fun getHomeData(): HomeData? {
        // 실제로 data를 반환하는 구현을 해야 한다. 그럼 data는 assetloader를 통해서 불러와야 한다

        return assetLoader.getJsonString("home.json")?.let { homeJsonString ->
            gson.fromJson(homeJsonString, HomeData::class.java)
        }
    }

}
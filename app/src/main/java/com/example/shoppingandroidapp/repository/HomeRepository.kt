package com.example.shoppingandroidapp.repository

import com.example.shoppingandroidapp.model.HomeData

class HomeRepository(private val assetDataSource: HomeAssetDataSource) {

    /*
    * 홈 화면에 보여줄 데이터를 이 클래스에서 관리한다
    * Repository는 Data Source로부터 데이터를 받는다. */


    // HomeAssetDataSource로부터 데이터를 요청해보자
    fun getHomeData(): HomeData? {
        return assetDataSource.getHomeData()
    }
    // 그리고, HomeRepository를 HomeViewModel에서 참조해야 한다.

}
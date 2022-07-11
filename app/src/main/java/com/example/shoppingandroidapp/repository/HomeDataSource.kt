package com.example.shoppingandroidapp.repository

import com.example.shoppingandroidapp.model.HomeData

interface HomeDataSource {
    /*
    * 여기서 HomeDataSource가 Interface가 돼야 하는 이유는,
    * Data Source는 여러 유형이 존재할 수 있다. 예를 들어 File이 될 수도 있고, Network 통신의 결과가 될 수도 있고 DB에 저장했던 data가 될 수도 있다.
    * 이렇게 여러 유형의 Data source에게 내가 공통적으로 요청하는 것은, 원본 데이터이다.
    * 이 요청을 interface의 메서드 getHomeData로 만들고, 각 Data Source유형은 HomeDataSource interface를 구현하는 클래스로 만드는 것.
    * 이제, Assetloader에서 데이터를 불러오는 Data source를 추가해야 한다.*/

    fun getHomeData(): HomeData?

}
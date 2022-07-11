package com.example.shoppingandroidapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingandroidapp.Banner
import com.example.shoppingandroidapp.model.Title
import com.example.shoppingandroidapp.repository.HomeRepository

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    /*
    * UI 레이어에서 Status Holders가 구현해야 하는 내용을 ViewModel의 subclass로 이동시켜야 한다
    * >> 해당 부분은 HomeFragment의 코드에서 AssetLoader에 JSON format의 데이터를 요청하고, HomeData객체로 변환한 부분
    * Fragment는 lifecycle에 맞추어 layout을 inflate하고, 해당하는 view를 그리는 책임만을 갖도록 한다.
    * 그래서 ViewModel에 데이터를 추후에 요청하게 되고, 그 데이터를 받아서 뷰에 할당하는 코드만을 HomeFragment 코드 안에 남겨두자
    *
    * HomeViewModel 클래스는 홈화면을 그리는 데 필요한 데이터의 holder 역할을 하는 클래스
    * 그래서 HomeData의 Title 객체, List Banner 객체를 관리하는 변수를 추가해야 한다
    * 이를 위해 먼저 data를 요청해야 하는데, 데이터 요청을 하기 위해 loadHomeData 함수를 선언 >> but 실제 구현은 HomeViewModel 에서 하면 안 된다
    * 공식 문서에서는, Data Layer의 Repository 에게 그 작업을 요청해야 한다. (>> 즉, Data Layer의 Repository를 통해서 Data를 요청하는 형태가 된다.)
    *
    *
    * Activity의 여러 life cycle callback이 호출되는 동안, ViewModel은, 최초에 생성된 이후에 계속 같은 상태를 유지한다.
    * 이러한 이유때문에 ViewModel을 State Holder 역할을 하는 클래스로 사용 가능한 것. 계속 같은 상태를 유지하고 있기 때문에, 한 번만 데이터를 불러오면
    * 그 상태를 유지해서 Activity가 어느 상태이던간에, 그 데이터를 언제든지 요청해서 사용할 수 있기 때문
    * Activity가 최종적으로 종료될 때, 최종적으로 onCleared() 메서드가 호출되어서 ViewModel도 이전에 사용하고 있다면 그 데이터를 메모리에서 해제하는 작업을 할 수 있다.
    *
    * ViewModel에서 데이터를 관리하는 방법 >> ViewModel에서 데이터를 관리할 때 보통 LiveData라는 클래스를 함께 사용하게 된다.
    *
    * Activity나 Fragment에서 이 클래스를 사용하는 방법>>
    * 먼저 HomeViewModel을 생성해야 하는데, by viewModels()을 호출하여 HomeviewModel을 생성한다.
    *
    *
    * observe() 메서드를 호출하면, LiveData가 변경 되었을 때, Observer 객체의 구현체가 호출 될 것이므로, View를 업데이트 가능
    * */

    private val _title = MutableLiveData<Title>() // AppBar를 그리는데 필요한 Title type의 데이터 추가
    val title: LiveData<Title> = _title

    private val _topBanners = MutableLiveData<List<Banner>>()
    val topBanners: LiveData<List<Banner>> = _topBanners

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        val homeData = homeRepository.getHomeData()
        homeData?.let { homeData ->
            _title.value = homeData.title
            _topBanners.value = homeData.topBanners
        }
    }

}
package com.example.shoppingandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/*
* Activity를 구현하기 위해서는 반드시 AppCompatActivity를 상속 받아야 한다
* 안드로이드 sdk에서 compat이라는 prefix가 붙으면 이전 버전의 구현체와 호환성을 지원해준다는 의미
*
* 앱을 실행하고 이 화면을 액티비티와 레이아웃으로 구성된다.
* 앱을 실행시켜 화면을 보면, 이 화면은 activity가 setcontentview 함수의 인자로
* layout의 id를 전달하고, 이 전달받은 layout을 화면에 띄운 것.
* 그리고 activity를 사용자와 상호작용하기 위한 진입점이라고 표현>> 상호작용?? 사용자가 터치,스크롤, 앱에서 사용자에게 팝업을 띄워 앱의 상태를 알릴 수도 있고
* 이런 종합적으로 서로 communication을 하는 활동을 의미
* 즉 activity는 ui를 포함하는 사용자와 상호작용하는 화면
* */

private const val TAG = "ManActivity"

/*
* activity가 실행되면, oncreate, onstart, onresume이 빠르게 호출된다.
* onresume이 호출된 이후에는 사용자에게 화면이 보여진다.
* 사용자가 back버튼이나 home버튼을 눌러서 해당 화면에서 벗어날 때 onpause와 onstop이 순차적으로 호출된다.*
* 실행중인 앱 목록에서 완전히 제거될 때 ondestroy가 호출된다.
*
* back버튼이나 home버튼을 눌러서 화면을 종료 했지만, 아직 사용중인 앱 목록에 남아있는 경우, 다시 그 앱으로 돌아가면,
* onstop상태에서 onrestart가 호출된다. 그리고 onstart가 호출되고, onresume이 호출된다. 그럼 다시 화면이 사용자에게 보여지게 된다.
* */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /*Activity가 생성되고 최초 한 번 호출되는 콜백 onCreate... 최초 한 번만 해도 좋을 작업을 하면 됨
        * 예) layoutinflate과정, 데이터초기화 과정 등*/
        super.onCreate(savedInstanceState)

        /*layout을 띄운다?? >> setContentview의 인자로 layout의 id를 전달하고
        * 이 layout의 내부에 구현되어 있는 것을 위젯으로 바꾸는 이 과정을 layout inflate라고 표현한다.
        * layout inflate과정은 이러한 view hierarchy 정보를 받아서 실제로 위젯을 instance화시켜 화면을 그리는 것을 뜻함*/
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        // activity_main.xml에서 app:menu에 drawable을 할당해도 그 아이콘의 배경 색은 원본 사진(아이콘)의 색상이 적용되지 않고,
        // app theme의 colorprimary 값으로 설정했었던 보라빛 color 하나로만 보여진다. 이 부분은 xml 파일 자체에서 해결할 수 없고 아래의 코드를 사용해야 한다
        // BottomNavigationView에서 app theme의 color 를 사용하지 않기 위해 추가한 코드
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        bottomNavigationView.itemIconTintList = null // theme의 color를 참조 하던 것이 초기화 된다

        // bottom navigation view와 fragmentcontainerview를 연결시키는 작업
        // bottomnavigationview에서 제공하는 메서드인 setupWithNavController 메서드를 사용하여 bottom navigation view와 fragmentcontainerview의 navhostfragment를 연결할 수 있게 된다.
        // navcontroller는 무엇일까? >> navigation graph를 활용해 화면간의 이동을 시각적으로 구성했는데,
        // navigation component에서 화면간 이동을 관리하는 객체는 navcontroller >> activity나 fragment에서 화면간 이동을 처리해야 할 때에는, 매번 navcontroller에 대한 참조를 얻어서 처리
        // navcontroller는 navhostfragment에서 destination의 이동을 관리하는 객체. 화면 이동을 관리하는 navcontroller 객체를 bottomnavigationview에 할당할 수 있게 지원함으로써,
        // bottomnavigationview의 item을 클릭했을 때, 화면 이동이 이루어 지도록 처리할 수 있게 된다.
        // 이제 navhostfragment에 navcontroller에 대한 참조를 가지고 와야 한다
        // 그러기 위해서는 먼저 navhostfragment에 대한 참조가 필요하다. >> 이는 supportFragmentManager가 제공하는 findFragmentById를 사용해서 가지고 올 수 있다.
        // id로 정의했던 container_main을 전달하면, navhostfragment에 대해 접근을 할 수 있게 된다. >> navhostfragment는 findNavController라는 메서드를 지원하는데,
        // navhostfragment가 소유하고 있는 navcontroller에 대한 참조를 반환하는 것.
        // 이를 navController 변수에 저장하자
        val navController = supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        // null이 아닐 때만, setupWithNavController의 인자로 전달할 수 있게끔 let으로 확인한다.
        navController?.let {
            bottomNavigationView.setupWithNavController(it)
        }
    }

    override fun onStart() {
        /*Activity가 화면을 벗어났다가 다시 되돌아 왔을때 한 번 더 호출될 수 있으므로
        * 애니메이션의 실행, 데이터의 갱신과 관련된 작업을 실행할 수 있음*/
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        /*해당 activity가 화면에서 보여지고 있다 >> activity가 focus를 얻었다 라고 표현*/
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        /*focus를 잃었을 때 바로 호출되는 callback*/
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        /*홈버튼을 눌러서 activity를 종료하고 다른 앱을 사용하고 있는 경우...
        * 우리 앱의 애니메이션을 중단하거나, 데이터 갱신 처리를 중단할 수 있다*/
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        /*사용중인 앱 목록에서도 완전히 제거가 됐을 때, 호출되는 callback
        * 그래서 이전 단계인 onstop에서 마저 정리하지 않는 작업이 있다면 리소스를 해제하는 처리를 여기에서 한다*/
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}
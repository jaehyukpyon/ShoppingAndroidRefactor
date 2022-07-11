package com.example.shoppingandroidapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppingandroidapp.*
import com.example.shoppingandroidapp.ui.common.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    /*viewModel 변수를 추가한다.*/
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeFragment", "onCreate()")
    }

    // Fragment도 Activity와 동일하게 layout을 inflate하는 단계가 있음. fragment는 그 과정을 onCreateView callback에서 진행한다.
    // 이를 통해 fragment의 life cycle callback은 activity의 life cycle callback과 약간 차이점 존재
    // layout을 inflate 하기 위해서는 layout file을 먼저 생성해 둬야 한다. : fragment_home.xml
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("HomeFragment", "onCreateView()")
        // 여기에서 fragment_home.xml을 inflate 시킨다. layout을 inflate하는 과정은 onCreateView에 인자로 전달 받은 inflater를 활용한다.
        // inflater의 inflate 메서드에 resource id를 전달하고, root가 되는 ViewGroup은 onCreateView의 인자로 전달 받은 container를 전달하고,
        // 마지막 인자도 전달해야 하는데>>
        // attachToRoot는 바로 root view에 추가 할 것인지 묻는 인자
        // false를 할당하는 이유는 fragment는 host가 되는 activity 위에 layout을 inflate 하는 것이기 때문에
        // activity가 모두 구성 된 이후에 layout이 inflate 되어야 한다. 생성되는 시점을 늦추기 위해 false를 할당하는 것
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /*
    * Home Fragment에서 상품 상세 버튼을 클릭 시, 상품 상세화면으로 이동하는 구현을 할 것
    * 버튼을 클릭하면 상품 상세 화면으로 이동할 수 있도록 상품 상세 화면도 Activity 혹은 Fragment로 구현해야 한다.
    * 나는 Fragment로 구현할 것.
    * fragment_product_detail.xml을 inflate할 Fragment가 필요하다. >> ProductDetailFragment*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "onViewCreated()")

        // 상품 상세 버튼을 클릭 시, 그 이벤트 알림을 받으려면 버튼에 대한 참조를 받아와야 한다.
        // 버튼 위젯에 대한 참조는 버튼 위젯을 정의할 때 할당했던 id를 기준으로 할 수 있다.
        // 이 메서드의 인자로 전달받는 view는, onCreateView에서 생성된 view가 전달된다.
        // 그래서 그 뷰의 findviewbyid 메서드를 호출해서 버튼 위젯에 대한 참조를 얻을 수 있다.
        //val button: Button = view.findViewById<Button>(R.id.btn_enter_product_detail)

        //button.setOnClickListener {
            // setonclicklistener는 View.onclicklistener라는 인터페이스를 구현하는 객체를 전달해야 한다. 이럴 때 object 키워드를 사용해 객체를 만들 수 있다.
            // 람다로 할 경우, 람다 블록 내부에는 버튼 뷰에 대한 참조가 전달됩니다
            // 이 블록 내부에서 ProductDetailFragment로 이동을 해야 한다. 그러기 위해서는 FragmentManager가 필요하다.
            // FragmentManager가 fragment의 추가, 삭제, 교체를 담당한다.
            // FragmentManager는 코드에서 참조가 가능하다.
            // parentFragmentManager랑 childFragmentManager가 있는데 둘 중에 어떤 거를 참조해야 할까?
            // HomeFragment는 어디에 할당 됐었지? >> HostFragment 역할을 하는 FragmentContainerView에 처음 시작하는 fragment로 할당을 했다.
            // 그래서 Host 역할을 하는 Fragment는 host activity에 fragment manager를 참조해야 되므로, parentFragmentManager를 참조하는 게 맞다.

//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction() // beginTransaction은 transaction 객체를 반환하며, transaction 은 fragment의 추가 삭제 교체를 요청하는 것 의미합니다.
//            // transaction 객체가 제공하는 add 메서드를 이용하여 ProductDetailFragment를 추가 할 것임
//            transaction.add(R.id.container_main, ProductDetailFragment())
//            transaction.commit()

            // 섹션 6, 63강부터 변경된 코드
            // 직접 transaction을 발생시키는 것이 아닌, findNavController로 navigation component(화면간의 이동을 구성할 수 있는 jetpack의 navigation component)에서 화면간의 이동을 관리하는 객체에 대한 참조를 불러오고, 여기에서 지원하는 navigate라는 메서드를 통해서 화면간의 이동을 구현
            // navigate 메서드의 인자로 resource id를 받는데, 이 id는 방금 전에 만들었던 (navigation folder안의 main_navigation.xml의 화살표) action의 아이디를 전달하면 된다.
//            button.setOnClickListener {
//                findNavController().navigate(R.id.action_home_to_product_detail)
//            }
//        }

//            // JSON 데이터를 객체로 변환하는 작업
//            // assets 폴더 하위에 추가되는 file들은 project에서 접근할 수 있는 방법이 제공된다.
//            // home.json에 저장된 데이터는 HomeFragment에서 필요하므로, 여기서 작업한다
//            // 먼저, assets folder에 접근하려면 context 라는 객체에 접근해야 한다. context를 통해 application 전역에서 사용할 수 있는 정보에 접근할 수 있고, resource나 db와 같은 시스템 자원에 접근할 수도 있다
//            // 지금은 assets 폴더 하위의 file을 열고 싶으므로
//            context?.assets?.open("home.json")?.use { inputStream ->
//                val size: Int = inputStream.available()
//                val bytes = ByteArray(size) // inputstream에서 전달받는 데이터의 type이 bytearray이기 때문
//                inputStream.read(bytes)
//                val homeData:String = String(bytes)
//                Log.d("homeData", homeData)
//            } // open 함수의 반환 type은 InputStream


        // 섹션 7, 70번 강의
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_home_title) // eggjam82님, 이런\n상품 어때요?
        val toolbarIcon = view.findViewById<ImageView>(R.id.toolbar_home_icon)

        // viewpager2 구현 시 필요
        val viewpager = view.findViewById<ViewPager2>(R.id.viewpager_home_banner)
        val viewpagerIndicator = view.findViewById<TabLayout>(R.id.viewpager_home_banner_indicator)

//        val assetLoader = AssetLoader()
//        val homeJsonString: String? = assetLoader.getJsonString(requireContext(), "home.json") // HomeFragment에서 view가 생성된 시점에는 context가 존재하는 시점이므로, 항상 non null 타입의 context를 반환하는 메서드를 통해 전달
//        Log.d("homeData", homeJsonString ?: "no homeData!!")

//        if (!homeJsonString.isNullOrEmpty()) {
//            val jsonObject: JSONObject = JSONObject(homeJsonString)
//            Log.d("jsonObject", jsonObject.toString())
//
//            val title: JSONObject = jsonObject.getJSONObject("title")
//            Log.d("title", title.toString())
//            val text: String = title.getString("text")
//            Log.d("text", text)
//            val iconUrl: String = title.getString("icon_url")
//            Log.d("iconUrl", iconUrl)
//
//            val topBanners: JSONArray = jsonObject.getJSONArray("top_banners")
//            val firstBanner: JSONObject = topBanners.getJSONObject(0)
//            val label:String = firstBanner.getString("label")
//            val productDetail: JSONObject = firstBanner.getJSONObject("product_detail")
//            val price: Int = productDetail.getInt("price")
//
//            //val titleValue = Title(text, iconUrl)
//
//            //toolbarTitle.text = text
//
//            // toolbar_home_icon에 image resource 를 할당해야 하는데, Glide library 사용...
////            GlideApp.with(this)
////                .load(iconUrl)
////                .into(toolbarIcon)
//
//            // viewpager2 구현 시 필요
//            // HomeBannerAdapter가 이제 viewpager에 추가되는 데이터를 관리하는 객체가 된다. 나중에는 homebanneradapter에 데이터를 전달하는 과정을 조금 더 개선할 것. 이번에는 빠른 테스트를 위해 instance를
//            // 생성함과 동시에 데이터 전달... 데이터를 전달하는 방법은 ListAdapter가 제공하는 submitList 메서드를 통해 할 수 있다.
//
////            val size = topBanners.length()
////            for (index in 0 until size) {
////                val bannerObject = topBanners.getJSONObject(index)
////                val backgroundImageUrl = bannerObject.getString("background_image_url")
////                val badgeObject = bannerObject.getJSONObject("badge")
////                val badgeLabel = badgeObject.getString("label")
////                val badgeBackgroundColor = badgeObject.getString("background_color")
////                val bannerBadge = BannerBadge(badgeLabel, badgeBackgroundColor)
////
//////                val banner = Banner(
//////                    backgroundImageUrl,
//////                    bannerBadge,
//////                    bannerLabel,
//////                    bannerProductDetail
//////                )
////            }
//
//            // gson 사용 이후
//            val gson = Gson()
//            val homeData = gson.fromJson(homeJsonString, HomeData::class.java)
//
//            /*viewModel.title.observe(viewLifecycleOwner, object : Observer<Title> {
//                override fun onChanged(t: Title?) {
//                    toolbarTitle.text = title.text
//                    GlideApp.with(HomeFragment.this)
//                        .load(homeData.title.iconUrl)
//                        .into(toolbarIcon)
//                }
//            })*/
//
//            // 앞으로 ViewModel이 State Holder의 역할을 하면서, Data를 저장하고 관리해야 한다.
//            // 그래서 title은 fragment에서 직접 요청하는 것이 아니라, ViewModel의 title을 참조하고, 이를 LiveData가 제공하는 observer 메서드를 통해서,
//            // data가 변경 되었을 때 어떠한 처리를 할 것인지 HomeFragment에서 구현해야 한다. 먼저 첫 버째 인자로 lifecycleowner를 전달해야 하는데,
//            // fragment는 viewLifecycleOwner를 통해 참조를 얻을 수 있다. 두 번째 인자는 Observer를 구현한 클래스를 전달해야 한다
//            viewModel.title.observe(viewLifecycleOwner) { title -> // Title data가 변경되면, 현재 이 block이 호출 된다
//                // 그러면 이 block안에서 view를 update 하는 logic을 구현하면 OK
//                toolbarTitle.text = title.text
//                GlideApp.with(this)
//                    .load(title.iconUrl)
//                    .into(toolbarIcon)
//            }
//
//            /*toolbarTitle.text = homeData.title.text
//            GlideApp.with(this)
//                .load(homeData.title.iconUrl)
//                .into(toolbarIcon)*/
//
//            viewModel.topBanners.observe(viewLifecycleOwner) { banners ->
//                viewpager.adapter = HomeBannerAdapter().apply {
//                    this.submitList(banners)
//                }
//            }
//
////            viewpager.adapter = HomeBannerAdapter().apply {
////                // submitList의 인자로 Banner의 List를 전달하면 된다.
////                // Banner의 List data를 가지고 오기 위해서, home.json file의 구조를 보자 >> "top_banners" key로 json array를 가져와야 한다
////                this.submitList(homeData.topBanners)
////            }
//
//            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
//            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
//
//            val screenWidth = resources.displayMetrics.widthPixels
//            val offset = screenWidth - pageWidth - pageMargin
//
//            viewpager.offscreenPageLimit = 3
//            viewpager.setPageTransformer { page, position ->
//                page.translationX = position * -offset
//            }
//
//            TabLayoutMediator(viewpagerIndicator, viewpager, object : TabLayoutMediator.TabConfigurationStrategy {
//                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
//
//                }
//            }).attach()
//        }

        viewModel.title.observe(viewLifecycleOwner, { title ->
            toolbarTitle.text = title.text
            GlideApp.with(this)
                .load(title.iconUrl)
                .into(toolbarIcon)
        })

        //viewpager.adapter = HomeBannerAdapter()

        viewpager.adapter = HomeBannerAdapter().apply {
            viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
                Log.d("HomeViewModelObserver", "observer worked...")
                this.submitList(banners)
            })
        }

//        viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
//            Log.d("HomeViewModelObserver2", "observer worked...") // 화면 아래 navigation이 변경되고, 다시 Home으로 돌아올 때 작동되고, 처음 홈 화면을 load할 때도 작동되고,,,
//        })

        /*viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
            viewpager.adapter = HomeBannerAdapter().apply {
                this.submitList(banners)
            }
        })*/

        val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
        val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
        val screenWidth = resources.displayMetrics.widthPixels
        val offset = screenWidth - pageWidth - pageMargin

        viewpager.offscreenPageLimit = 3
        viewpager.setPageTransformer { page, position ->
            page.translationX = position * -offset
        }

        TabLayoutMediator(viewpagerIndicator, viewpager) { tab, position ->
            ;
        }.attach()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("HomeFragment", "onViewStateRestored()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeFragment", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeFragment", "onStop()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("HomeFragment", "onSaveInstanceState()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HomeFragment", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeFragment", "onDestroy()")
    }

}
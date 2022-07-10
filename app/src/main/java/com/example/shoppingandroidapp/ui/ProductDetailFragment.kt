package com.example.shoppingandroidapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoppingandroidapp.R

class ProductDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // activity가 생성된 이후에 view를 inflate 해야 함으로, false로 인자값을 할것
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    // 이제 HomeFragment에서 ProductDetailFragment로 이동하는 코드를 작성해야 한다...
    // 그 전에 앞서 HomeFragment를 main activity 에서 inflate 해야 합니다

}
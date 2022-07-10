package com.example.shoppingandroidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CategoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // activity가 inflate 된 이후에 fragment를 inflate 시켜야 하니, 그 시점을 뒤로 늦추는 false값 할당
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

}
package com.example.shoppingandroidapp

import com.google.gson.annotations.SerializedName

/*
* Banner 클래스는 HomeFragment에서 json 데이터를 객체로 변환할 때 사용될 예정
* 또한 HomeBannerAdapter의 객체 type으로도 사용된다. */
data class Banner(
    @SerializedName("background_image_url") val backgroundImageUrl: String,
    val badge: BannerBadge,
    val label: String,
    @SerializedName("product_detail") val productDetail: ProductDetail
)

data class BannerBadge(
    val label: String,
    @SerializedName("background_color") val backgroundColor: String
)

data class ProductDetail(
    @SerializedName("brand_name") val brandName: String,
    val label: String,
    @SerializedName("discount_rate") val discountRate: Int,
    val price: Int,
    @SerializedName("thumbnail_image_url") val thumbnailImageUrl: String,
    @SerializedName("product_id") val productId: String
)

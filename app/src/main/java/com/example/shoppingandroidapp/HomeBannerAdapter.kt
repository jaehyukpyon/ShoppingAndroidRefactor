package com.example.shoppingandroidapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import kotlin.math.roundToInt

/*
* 클래스 명에 Adapter가 붙은 이유는, ViewPager2에 추가할 layout을 구현하기 위해서 ListAdapter 클래스를 상속받을 것이라...
* ListAdapter<T, VH>  -->> T는 layout에 표현할 data의 type을 의미하고, VH는 RecyclerView.ViewHolder 클래스의 subclass를 생성해서 전달해야 함
* >> HomeBannerAdapter는 전달해야 되는 객체의 type은 home.json으로 돌아가서 확인해 보면, top_banners 배열(array)에 있는, JSON object 객체 (Banner 클래스를 직접 생성하야 함)...*/
class HomeBannerAdapter : ListAdapter<Banner, HomeBannerAdapter.HomeBannerViewHolder>(BannerDiffCallback()) {
    /*
    * ListAdapter의 역할은, data의 list를 받아서, 0번째부터 순차적으로 view holder와 binding을 한다
    * 그럼 이때, layout은 그대로 유지한 채로, data만 업데이트 된다면 성능상의 이점 존재
    * 이를 지원하는 것이 ListAdapter.
    * DiffUtil.ItemCallback 클래스를 상속받게 되면, 스크롤이 변경됨에 따라서 실제로 데이터가 변경되는지를 확인하고, 데이터가 변경됐음이 판명되면, 그때서야 layout을 update하게 된다
    * 이때 어떠한 id를 기준으로, 같다, 다르다를 구분 할 것인지 iffUtil.ItemCallback을 상속 받으면서 정의를 해야 하는 것*/

    class HomeBannerViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        // RecyclerView.ViewHolder의 생성자로 view를 전달해야 하는데,
        // 생성자의 매개변수 view는, HomeBanner에서 inflate 시킬 layout을 의미

        private val bannerImageView = view.findViewById<ImageView>(R.id.iv_banner_image)
        private val bannerBadgeTextView = view.findViewById<TextView>(R.id.tv_banner_badge)
        private val bannerTitleTextView = view.findViewById<TextView>(R.id.tv_banner_title)
        private val bannerDetailThumbnailImageView = view.findViewById<ImageView>(R.id.iv_banner_detail_thumbnail)
        private val bannerDetailBrandLabelTextView = view.findViewById<TextView>(R.id.tv_banner_detail_brand_label)
        private val bannerDetailProductLabelTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_label)
        private val bannerDetailDiscountRateTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_discount_rate)
        private val bannerDetailDiscountPriceTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_discount_price)
        private val bannerDetailPriceTextView = view.findViewById<TextView>(R.id.tv_banner_detail_product_price)

        fun bind(banner: Banner) {
            // view holder에 binding을 할 때 호출 할 메서드를 추가
            // 그리고 이때는 data를 전달 받아서 binding을 해야 함.
            // 그래서 이 adapter class에서 data type으로 사용하고 있는 Banner를 매개변수의 인자로 전달 받는다
            // 이 메서드에서는 인자로 banner 객체를 받아서, view와 binding을 해줘야 한다 (즉 데이터를 view에 할당해 줘야 한다). 이를 위해 view에 대한 참조가 필요하다. (클래스의 맴버변수로 필요합니다)

            /*GlideApp.with(itemView) // 이전에는 Fragment에서 GlideApp.with을 호출했기 때문에 Fragment에 대한 참조 this를 전달했었다.
                    // 이번에는 이 viewholder에 대한 view에 대한 참조를 전달. viewholder를 생성할 때, 인자로 전달했었던 뷰는 내부에서는 itemView로 참조 가능하다
                .load(banner.backgroundImageUrl)
                .into(bannerImageView)*/

            loadImage(banner.backgroundImageUrl, bannerImageView)

            // Banner data를 view에 바인딩 작업 마저 마무리 하기

            bannerBadgeTextView.text = banner.badge.label
            bannerBadgeTextView.background = ColorDrawable(Color.parseColor(banner.badge.backgroundColor))
            bannerTitleTextView.text = banner.label

            loadImage(banner.productDetail.thumbnailImageUrl, bannerDetailThumbnailImageView)

            bannerDetailBrandLabelTextView.text = banner.productDetail.brandName
            bannerDetailProductLabelTextView.text = banner.productDetail.label
            bannerDetailDiscountRateTextView.text = "${banner.productDetail.discountRate}%"
            calculateDiscountAmount(bannerDetailDiscountPriceTextView, banner.productDetail.discountRate, banner.productDetail.price)
            applyPriceFormat(bannerDetailPriceTextView, banner.productDetail.price)
        }

        private fun loadImage(urlString: String, imageView: ImageView) {
            GlideApp.with(itemView)
                .load(urlString)
                .into(imageView)
        }

        private fun applyPriceFormat(view: TextView, price: Int) {
            val decimalFormat = DecimalFormat("#,###")
            view.text = decimalFormat.format(price) + "원"
        }

        private fun calculateDiscountAmount(view: TextView, discountRate: Int, price: Int) {
            val discountPrice = (((100 - discountRate) / 100.0) * price).roundToInt()
            applyPriceFormat(view, discountPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        // 이 메서드 안에서 HomeBannerViewHolder를 생성해야 한다.
        // HomeBannerViewHolder의 생성자 매개변수로 view를 전달할 때는 Layout을 inflate해야 한다

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return HomeBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        // onCreateViewHolder가 호출 된 이후에, HomeBannerViewHolder가 잘 생성되면 그 HomeBannerViewHolder 객체가 이 메서드의 인자로 전달된다
        // HomeBannerViewHolder가 잘 생성된 이후에, 이 holder에 data를 binding 하는 것
        // holder.bind 메서드 호출 시 Banner 객체를 전달하기 위해 ListAdapter에서 제공하는 getItem 메서드를 활용한다. position을 전달하면, 해당 position의 data type을 반환해 준다.
        // 그럼 이렇게 전달 받은 data를 TextView나 ImageView 등에 할당해서 원하는 layout을 완성시키는 것.
        holder.bind(getItem(position))
    }

}

class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {
    /* DiffUtil.ItemCallback<T> --> T 자리에 전달해야 하는 클래스는 Banner.
    * Banner 객체가 서로 다른지, 같은지를 구분하는 기준을 DiffUtil.ItemCallback을 상속받으며 정의
    *
    *
    * BannerDiffCallback을 통해 두 객체를 비교하는 기준을 알려주게 되면, areItemsTheSame가 호출 되었을 떄 두 객체의 productId의 값이 동일하지 않다면,
    * 두 객체는 바로 다른 객체로 판단할 수 있게 된다. 만약, productId가 동일하다면, areContentsTheSame까지 호출되어, 나머지 프로퍼티까지 전부 비교하게 된다.
    * 만약, 나머지 property 중에 일부 다른 값을 갖고 있다면, 두 객체는 서로 다른 객체로 판단이 되고, 기존에 그려졌던 UI가 있다면, 그 UI를 update 하게 되는 것
    * */

    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        // 기존의 객체와, 새로운 객체를 비교할 때 어떠한 값을 식별자로 사용할지 알려줘야 함.
        // 기존의 객체의 productId와 새로운 객체의 productId가 동일하다면, 두 객체는 동일한 id에 대해서 다른 필드의 값이 업데이트 될 수도 있는 것.
        // 그래서 만약, 객체의 id가 동일하다면 아래 두 번째 메서드가 마저 호출된다. 다른 프로퍼티의 값도 모두 동일한지 확인하는 단계...
        return oldItem.productDetail.productId == newItem.productDetail.productId
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        // oldItem과 newItem이 모두 동등한지, 동등성 비교 연산자로 비교할 수 있다.
        return oldItem == newItem
    }

}
package com.example.shoppingandroidapp

import android.content.Context
import android.util.Log

class AssetLoader(private val context: Context) {

    fun getJsonString(fileName: String): String? {
        // val string = loadAsset(context, fileName)
        return kotlin.runCatching {
            loadAsset(fileName)
        }.getOrNull()
    }

    private fun loadAsset(fileName: String): String {
        // JSON 데이터를 객체로 변환하는 작업
        // assets 폴더 하위에 추가되는 file들은 project에서 접근할 수 있는 방법이 제공된다.
        // 먼저, assets folder에 접근하려면 context 라는 객체에 접근해야 한다. context를 통해 application 전역에서 사용할 수 있는 정보에 접근할 수 있고, resource나 db와 같은 시스템 자원에 접근할 수도 있다
        // 지금은 assets 폴더 하위의 file을 열고 싶으므로
        return context.assets.open(fileName).use { inputStream ->
            val size: Int = inputStream.available()
            val bytes = ByteArray(size) // inputstream에서 전달받는 데이터의 type이 bytearray이기 때문
            inputStream.read(bytes)
            val homeData:String = String(bytes)
            Log.d("AssetLoader-loadAsset", homeData)
            homeData
        } // open 함수의 반환 type은 InputStream
    }

}
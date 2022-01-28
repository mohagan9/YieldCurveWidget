package com.widget.yieldcurve.config

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    companion object {
        private fun getRetrofit(Url: String): Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(Url)
                .build()
        }

        fun getApiData(): Retrofit {
            return getRetrofit(Url.BASE_URL)
        }

        fun yieldCurveApi(): UsTreasuryYieldCurveApi {
            val retrofitCall = getApiData()
            return retrofitCall.create(UsTreasuryYieldCurveApi::class.java)
        }

    }
}
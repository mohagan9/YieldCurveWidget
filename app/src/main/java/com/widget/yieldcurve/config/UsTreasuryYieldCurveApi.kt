package com.widget.yieldcurve.config

import com.widget.yieldcurve.model.YieldCurveSnapshot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UsTreasuryYieldCurveApi {
    @GET(Url.URL)
    fun getYieldCurveSnapshot(@Query("date") date: String): Observable<List<YieldCurveSnapshot>>
}
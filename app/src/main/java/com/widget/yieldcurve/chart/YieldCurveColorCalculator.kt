package com.widget.yieldcurve.chart

import com.widget.yieldcurve.model.Color
import com.widget.yieldcurve.model.YieldCurveSnapshot

interface YieldCurveColorCalculator {
    fun getColor(curveSnapshot: YieldCurveSnapshot): Color
}
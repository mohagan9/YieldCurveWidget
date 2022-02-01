package com.widget.yieldcurve.model

data class YieldCurveSnapshot(
    val yield_1m: Float,
    val yield_3m: Float,
    val yield_6m: Float,
    val yield_1y: Float,
    val yield_2y: Float,
    val yield_3y: Float,
    val yield_5y: Float,
    val yield_7y: Float,
    val yield_10y: Float,
    val yield_20y: Float,
    val yield_30y: Float
)

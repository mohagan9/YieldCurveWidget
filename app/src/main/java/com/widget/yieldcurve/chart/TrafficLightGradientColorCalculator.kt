package com.widget.yieldcurve.chart

import com.widget.yieldcurve.model.Color
import com.widget.yieldcurve.model.YieldCurveSnapshot

/**
 * Color transitions from green to red as the yield curve flattens and inverts
 */
class TrafficLightGradientColorCalculator : YieldCurveColorCalculator {
    override fun getColor(curveSnapshot: YieldCurveSnapshot): Color {
        val mostInverted = minOf(
            curveSnapshot.yield_2y - curveSnapshot.yield_3m,
            curveSnapshot.yield_10y - curveSnapshot.yield_2y,
            curveSnapshot.yield_10y - curveSnapshot.yield_5y,
            curveSnapshot.yield_30y - curveSnapshot.yield_5y
        )
        val red = constrainChannel(2 - (mostInverted * 2))
        val green = constrainChannel(mostInverted * 2)

        return Color(1F, red, green, 0F)
    }

    private fun constrainChannel(channel: Float): Float {
        return when {
            channel < 0F -> 0F
            channel > 1F -> 1F
            else -> channel
        }
    }
}
package com.widget.yieldcurve.chart

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class TermAxisFormatter(terms: Array<String>) : IndexAxisValueFormatter(terms) {
    override fun getFormattedValue(value: Float): String {
        return when (value) {
            1F -> "3m"
            2F -> "2y"
            3F -> "5y"
            5F -> "10y"
            7F -> "30y"
            else -> ""
        }
    }
}
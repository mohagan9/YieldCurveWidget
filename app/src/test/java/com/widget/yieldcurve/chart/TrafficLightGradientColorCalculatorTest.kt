package com.widget.yieldcurve.chart

import com.widget.yieldcurve.model.Color
import com.widget.yieldcurve.model.YieldCurveSnapshot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TrafficLightGradientColorCalculatorTest {

    lateinit var colorCalculator: YieldCurveColorCalculator

    @BeforeEach
    fun setup() {
        colorCalculator = TrafficLightGradientColorCalculator()
    }

    @ParameterizedTest
    @CsvSource(value = ["1.5|1.4", "1.1|1.1"], delimiter = '|')
    fun getColor_shouldReturnRed_whenThreeMonthEqualOrGreaterThanTwoYear(yield3m: Float, yield2y: Float) {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = yield3m,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = yield2y,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 0F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnOrange_whenThreeMonthTwentyFiveBasisPointsLowerThanTwoYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 1.75F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 0.5F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnYellow_whenThreeMonthFiftyBasisPointsLowerThanTwoYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 1.5F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnLime_whenThreeMonthSeventyFiveBasisPointsLowerThanTwoYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 1.25F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 0.5F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @ParameterizedTest
    @CsvSource(value = ["0.4|1.4", "0.3|1.4"], delimiter = '|')
    fun getColor_shouldReturnGreen_whenThreeMonthEqualOrLessThanOneHundredBasisPointsBelowTwoYear(yield3m: Float, yield2y: Float) {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = yield3m,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = yield2y,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 0F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @ParameterizedTest
    @CsvSource(value = ["2.5|2.4", "2.1|2.1"], delimiter = '|')
    fun getColor_shouldReturnRed_whenTwoYearEqualOrGreaterThanTenYear(yield2y: Float, yield10y: Float) {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = yield2y,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = yield10y,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 0F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnOrange_whenTwoYearTwentyFiveBasisPointsLowerThanTenYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 2.25F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 0.5F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnYellow_whenTwoYearFiftyBasisPointsLowerThanTenYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 2.5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnLime_whenTwoYearSeventyFiveBasisPointsLowerThanTenYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = 2.75F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 0.5F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @ParameterizedTest
    @CsvSource(value = ["1.4|2.4", "1.3|2.4"], delimiter = '|')
    fun getColor_shouldReturnGreen_whenTwoYearEqualOrLessThanOneHundredBasisPointsBelowTenYear(yield2y: Float, yield10y: Float) {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = yield2y,
            yield_3y = 0F,
            yield_5y = 0F,
            yield_7y = 0F,
            yield_10y = yield10y,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 0F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @ParameterizedTest
    @CsvSource(value = ["3.5|3.4", "3.1|3.1"], delimiter = '|')
    fun getColor_shouldReturnRed_whenFiveYearEqualOrGreaterThanThirtyYear(yield5y: Float, yield30y: Float) {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = yield5y,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = yield30y
        )
        assertEquals(
            Color(1F, 1F, 0F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnOrange_whenFiveYearTwentyFiveBasisPointsLowerThanThirtyYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 4.75F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 0.5F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnYellow_whenFiveYearFiftyBasisPointsLowerThanThirtyYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 4.5F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 1F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnLime_whenFiveYearSeventyFiveBasisPointsLowerThanThirtyYear() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 4.25F,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = 5F
        )
        assertEquals(
            Color(1F, 0.5F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @ParameterizedTest
    @CsvSource(value = ["2.4|3.4", "2.3|3.4"], delimiter = '|')
    fun getColor_shouldReturnGreen_whenFiveYearEqualOrLessThanOneHundredBasisPointsBelowTenYear(yield5y: Float, yield30y: Float) {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 0F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = yield5y,
            yield_7y = 0F,
            yield_10y = 5F,
            yield_20y = 5F,
            yield_30y = yield30y
        )
        assertEquals(
            Color(1F, 0F, 1F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }

    @Test
    fun getColor_shouldReturnColorOfMostInverted() {
        val snapshot = YieldCurveSnapshot(
            yield_1m = 0F,
            yield_3m = 1.09F,
            yield_6m = 0F,
            yield_1y = 0F,
            yield_2y = 2F,
            yield_3y = 0F,
            yield_5y = 2.05F,
            yield_7y = 0F,
            yield_10y = 2.3F,
            yield_20y = 5F,
            yield_30y = 2.75F
        )
        assertEquals(
            Color(1F, 1F, 0.5F, 0F),
            colorCalculator.getColor(snapshot)
        )
    }
}
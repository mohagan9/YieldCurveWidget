package com.widget.yieldcurve

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.widget.yieldcurve.chart.TermAxisFormatter
import com.widget.yieldcurve.config.RetrofitConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class YieldCurveWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if ("android.appwidget.action.APPWIDGET_UPDATE" == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, YieldCurveWidget::class.java)
            onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName))
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    val disposable = RetrofitConfig.yieldCurveApi()
        .getYieldCurveSnapshot(currentDate)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe (
            { response -> run {
                val chart = LineChart(context)
                val entries = listOf(
                    Entry(1F, response[0].yield_3m),
                    Entry(2F, response[0].yield_2y),
                    Entry(3F, response[0].yield_5y),
                    Entry(5F, response[0].yield_10y),
                    Entry(7F, response[0].yield_30y)
                )
                val dataSet = LineDataSet(entries, "US Treasury Yield Curve")
                dataSet.lineWidth = 2F
                dataSet.circleRadius = 4F
                chart.data = LineData(dataSet)
                chart.description.text = currentDate
                chart.description.textColor = Color.LTGRAY
                chart.axisLeft.setDrawLabels(false)
                chart.axisRight.setDrawLabels(false)
                chart.axisLeft.setDrawGridLines(false)
                chart.axisRight.setDrawGridLines(false)
                chart.xAxis.setDrawGridLines(false)
                chart.setDrawBorders(false)
                chart.axisLeft.setDrawAxisLine(false)
                chart.axisRight.setDrawAxisLine(false)
                chart.xAxis.setDrawAxisLine(false)
                chart.setBackgroundColor(Color.DKGRAY)
                chart.data.setValueTextColor(Color.WHITE)
                chart.data.setValueTextSize(8F)
                chart.legend.textColor = Color.WHITE
                chart.xAxis.textColor = Color.WHITE
                chart.xAxis.valueFormatter = TermAxisFormatter(arrayOf("3m", "2y", "5y", "10y", "30y"))
                chart.xAxis.labelCount = entries.size
                chart.xAxis.granularity = 1F
                chart.measure(
                    View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(500, View.MeasureSpec.EXACTLY))
                chart.layout(0, 0, chart.measuredWidth, chart.measuredHeight)

                val views = RemoteViews(context.packageName, R.layout.yield_curve_widget)
                views.setImageViewBitmap(R.id.chart_image, chart.chartBitmap)
                views.setOnClickPendingIntent(R.id.refresh_button, getPendingSelfIntent(context))

                appWidgetManager.updateAppWidget(appWidgetId, views)
            } },
            { error -> run {

            } }
        )
}

private fun getPendingSelfIntent(context: Context): PendingIntent? {
    val intent = Intent(context, YieldCurveWidget::class.java)
    intent.action = "android.appwidget.action.APPWIDGET_UPDATE"
    return PendingIntent.getBroadcast(context, 0, intent, 0)
}
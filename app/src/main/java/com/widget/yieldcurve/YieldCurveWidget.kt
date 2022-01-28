package com.widget.yieldcurve

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.widget.yieldcurve.config.RetrofitConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
    val currentTime = Date().time
    val disposable = RetrofitConfig.yieldCurveApi()
        .getYieldCurveSnapshot(currentDate)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe (
            { response -> run {
                val views = RemoteViews(context.packageName, R.layout.yield_curve_widget)
                views.setTextViewText(R.id.appwidget_text, "$currentTime $response")

                appWidgetManager.updateAppWidget(appWidgetId, views)
            } },
            { error -> run {
                val views = RemoteViews(context.packageName, R.layout.yield_curve_widget)
                views.setTextViewText(R.id.appwidget_text, error.localizedMessage ?: "Error")

                appWidgetManager.updateAppWidget(appWidgetId, views)
            } }
        )
}
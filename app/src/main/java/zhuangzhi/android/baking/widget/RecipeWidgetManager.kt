package zhuangzhi.android.baking.widget

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import zhuangzhi.android.baking.R


class RecipeWidgetManager(private val application: Application) {
    fun updateWidget() {
        val intent = Intent(application, RecipeWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val appWidgetManager = AppWidgetManager.getInstance(application)
        val componentName = ComponentName(application, RecipeWidgetProvider::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        application.sendBroadcast(intent)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_ingredient_list)
    }
}
package zhuangzhi.android.baking.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import zhuangzhi.android.baking.MainApplication
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.ui.recipe.RecipeActivity
import zhuangzhi.android.baking.util.Constants
import zhuangzhi.android.baking.util.PreferenceHelper
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
class RecipeWidgetProvider : AppWidgetProvider() {
    @Inject lateinit var preferenceHelper: PreferenceHelper
    @Inject lateinit var dataSource: DataSource

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        MainApplication.appComponent.inject(this)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, preferenceHelper, dataSource)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int, preferenceHelper: PreferenceHelper, dataSource: DataSource) {

            val recipeId = preferenceHelper.get(Constants.RECIPE_WIDGET_RECIPE_ID, 0)
            val recipe = dataSource.getRecipe(recipeId!!, context)
            val widgetText = recipe.name
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.recipe_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            val ingredients = recipe.ingredients
            for (ingredient in ingredients) {
                val ingredientView = RemoteViews(context.packageName, R.layout.item_recipe_widget)
                ingredientView.apply {
                    setTextViewText(R.id.recipe_widget_ingredient_name, ingredient.ingredient)
                    setTextViewText(R.id.recipe_widget_ingredient_quantity, "${ingredient.quantity} ${ingredient.measure}")
                }
                views.addView(R.id.recipe_widget_ingredient_list, ingredientView)
            }
//            views.setRemoteAdapter(R.id.recipe_widget_ingredient_list,
//                    Intent(context, RecipeWidgetRemoteViewService::class.java))

            val intent = Intent(context, RecipeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            views.setOnClickPendingIntent(R.id.recipe_widget_container, pendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}


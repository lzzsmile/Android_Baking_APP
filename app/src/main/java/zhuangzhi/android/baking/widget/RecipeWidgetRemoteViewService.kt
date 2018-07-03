package zhuangzhi.android.baking.widget

import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import zhuangzhi.android.baking.MainApplication
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.data.Recipe
import zhuangzhi.android.baking.util.Constants
import zhuangzhi.android.baking.util.PreferenceHelper
import javax.inject.Inject

class RecipeWidgetRemoteViewService: RemoteViewsService() {
    @Inject lateinit var preferenceHelper: PreferenceHelper
    @Inject lateinit var dataSource: DataSource

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return object: RemoteViewsFactory {
            override fun getLoadingView(): RemoteViews? {
                return null
            }

            override fun getItemId(p0: Int): Long {
                return p0.toLong()
            }

            override fun hasStableIds(): Boolean {
                return false
            }

            override fun getViewTypeCount(): Int {
                return 1
            }

            private var recipe: Recipe? = null

            override fun onCreate() {
                MainApplication.appComponent.inject(this@RecipeWidgetRemoteViewService)
            }

            override fun onDestroy() {

            }

            override fun onDataSetChanged() {
                val recipeId = preferenceHelper.get(Constants.RECIPE_WIDGET_RECIPE_ID, 0)
                recipe = dataSource.getRecipe(recipeId!!, baseContext)
            }

            override fun getCount(): Int {
                return recipe?.ingredients?.size ?: 0
            }

            override fun getViewAt(position: Int): RemoteViews {
                val views = RemoteViews(packageName, R.layout.item_recipe_widget)
                val ingredient = recipe?.ingredients?.get(position)
                if (ingredient != null) {
                    views.apply {
                        setTextViewText(R.id.recipe_widget_ingredient_name, ingredient.ingredient)
                        setTextViewText(R.id.recipe_widget_ingredient_quantity, "${ingredient.quantity} ${ingredient.measure}")
                    }
                }
                return views
            }


        }
    }
}
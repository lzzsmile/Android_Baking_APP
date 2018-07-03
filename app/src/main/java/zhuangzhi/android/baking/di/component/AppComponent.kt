package zhuangzhi.android.baking.di.component

import dagger.Component
import zhuangzhi.android.baking.di.module.ApiModule
import zhuangzhi.android.baking.di.module.AppModule
import zhuangzhi.android.baking.di.module.NetworkModule
import zhuangzhi.android.baking.ui.recipe.RecipeActivity
import zhuangzhi.android.baking.ui.recipeDetail.RecipeDetailActivity
import zhuangzhi.android.baking.ui.recipeStep.RecipeStepActivity
import zhuangzhi.android.baking.ui.recipeStep.RecipeStepFragment
import zhuangzhi.android.baking.widget.RecipeWidgetManager
import zhuangzhi.android.baking.widget.RecipeWidgetProvider
import zhuangzhi.android.baking.widget.RecipeWidgetRemoteViewService
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class), (NetworkModule::class), (AppModule::class)])
interface AppComponent {
    fun inject(recipeActivity: RecipeActivity)
    fun inject(recipeDetailActivity: RecipeDetailActivity)
    fun inject(recipeStepActivity: RecipeStepActivity)
    fun inject(recipeStepFragment: RecipeStepFragment)
    fun inject(recipeWidgetRemoteViewService: RecipeWidgetRemoteViewService)
    fun inject(recipeWidgetProvider: RecipeWidgetProvider)
}
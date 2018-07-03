package zhuangzhi.android.baking.di.module

import android.app.Application
import com.kabu.eats.util.SchedulerProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import zhuangzhi.android.baking.network.RecipeService
import zhuangzhi.android.baking.util.PreferenceHelper
import zhuangzhi.android.baking.widget.RecipeWidgetManager
import javax.inject.Singleton
import javax.sql.DataSource

@Module(includes = ([ApiModule::class]))
open class AppModule(private val application: Application) {

    @Provides
    @Singleton
    open fun providesApplication(): Application = application

    @Provides
    @Singleton
    open fun providesSchedulerProvider(): SchedulerProvider = SchedulerProvider

    @Provides
    @Singleton
    open fun providesPreferenceHelper(application: Application): PreferenceHelper {
        return PreferenceHelper(application)
    }

    @Provides
    @Singleton
    open fun providesRecipeWidgetManager(application: Application): RecipeWidgetManager {
        return RecipeWidgetManager(application)
    }
}
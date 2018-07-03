package zhuangzhi.android.baking.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import zhuangzhi.android.baking.data.DataSource
import zhuangzhi.android.baking.network.ApiConstants
import zhuangzhi.android.baking.network.RecipeService
import javax.inject.Singleton

@Module
open class ApiModule {

    private lateinit var okHttpClient: OkHttpClient

    @Provides
    @Singleton
    open fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        this.okHttpClient = okHttpClient
        val builder: OkHttpClient.Builder = okHttpClient.newBuilder()

        this.okHttpClient = builder.build()
        return Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(this.okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    open fun providesRecipeService(retrofit: Retrofit): RecipeService =
            retrofit.create(RecipeService::class.java)

    @Provides
    @Singleton
    open fun providesDataSource(recipeService: RecipeService): DataSource {
        return DataSource(recipeService)
    }
}
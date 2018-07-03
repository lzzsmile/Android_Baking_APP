package zhuangzhi.android.baking.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import zhuangzhi.android.baking.data.Recipe

interface RecipeService {

    @GET("android-baking-app-json")
    fun getRecipes(): Call<List<Recipe>>

}
package zhuangzhi.android.baking.data

import android.app.Activity
import android.content.Context
import retrofit2.Call
import zhuangzhi.android.baking.network.RecipeService
import zhuangzhi.android.baking.util.DataUtil

class DataSource(private val recipeService: RecipeService) {

    fun getRecipes(): Call<List<Recipe>> {
        return recipeService.getRecipes()
    }

    fun getRecipes(context: Context): List<Recipe> {
        return DataUtil.getRecipes(context)
    }

    fun getRecipe(position: Int, context: Context): Recipe {
        val recipes = DataUtil.getRecipes(context)
        return recipes[position]
    }

    fun getStep(recipeId: Int, stepId: Int, context: Context): Step {
        val recipes = DataUtil.getRecipes(context)
        val recipe = recipes[recipeId]
        val step = recipe.steps[stepId]
        return step
    }

}
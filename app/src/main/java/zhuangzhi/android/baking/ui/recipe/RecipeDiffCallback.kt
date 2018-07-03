package zhuangzhi.android.baking.ui.recipe

import android.support.v7.util.DiffUtil
import zhuangzhi.android.baking.data.Recipe

class RecipeDiffCallback(
        private val oldRecipes: List<Recipe>,
        private val newRecipes: List<Recipe>
) : DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipes[oldItemPosition].id == newRecipes[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldRecipes.size

    override fun getNewListSize(): Int = newRecipes.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipes[oldItemPosition] == newRecipes[newItemPosition]
    }

}
package zhuangzhi.android.baking.ui.recipeDetail

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.item_recipe_detail_step.view.*
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.Recipe
import zhuangzhi.android.baking.data.Step

class RecipeDetailStepViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(step: Step) = with(itemView) {
        recipe_detail_step.text = "${step.id}. ${step.shortDescription}"
    }

}
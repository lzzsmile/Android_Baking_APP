package zhuangzhi.android.baking.ui.recipe

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recipe.view.*
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.Recipe

class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(recipe: Recipe) = with(itemView) {
        recipe_name.text = recipe.name
        recipe_serve.text = recipe.servings.toString()
        if (recipe.image.isNotEmpty()) {
            Picasso.get()
                    .load(Uri.parse(recipe.image))
                    .into(recipe_image)
        } else {
            recipe_image.setImageResource(R.drawable.image_placeholder)
        }
    }

}
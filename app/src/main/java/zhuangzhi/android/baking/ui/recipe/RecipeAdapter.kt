package zhuangzhi.android.baking.ui.recipe

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.Recipe
import zhuangzhi.android.baking.event.RecipeClickedEvent
import zhuangzhi.android.baking.event.RxBus

class RecipeAdapter(private var items: List<Recipe>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> {
                val recipe = items[position]
                holder.bind(recipe)
                holder.itemView.setOnClickListener { RxBus.publish(RecipeClickedEvent(position)) }
            }
        }
    }

    fun replaceData(recipes: List<Recipe>) {
        val diffResult = DiffUtil.calculateDiff(RecipeDiffCallback(this.items, recipes))
        this.items = emptyList()
        this.items = recipes
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}
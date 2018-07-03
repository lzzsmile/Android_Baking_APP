package zhuangzhi.android.baking.ui.recipeDetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import zhuangzhi.android.baking.R
import zhuangzhi.android.baking.data.Step
import zhuangzhi.android.baking.event.RxBus
import zhuangzhi.android.baking.event.StepClickEvent

class RecipeDetailStepAdapter(private var items: List<Step>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeDetailStepViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_detail_step, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeDetailStepViewHolder -> {
                val step = this.items[position]
                holder.bind(step)
                holder.itemView.setOnClickListener { RxBus.publish(StepClickEvent(position)) }
            }
        }
    }

    fun replaceData(steps: List<Step>) {
        this.items = emptyList()
        this.items = steps
        notifyDataSetChanged()
    }
}
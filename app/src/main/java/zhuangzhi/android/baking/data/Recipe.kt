package zhuangzhi.android.baking.data

import com.google.gson.annotations.SerializedName

data class Recipe (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("ingredients")
        val ingredients: List<Ingredient>,
        @SerializedName("steps")
        val steps: List<Step>,
        @SerializedName("servings")
        val servings: Int,
        @SerializedName("image")
        val image: String
)
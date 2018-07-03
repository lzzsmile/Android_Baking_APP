package zhuangzhi.android.baking.data

import com.google.gson.annotations.SerializedName

data class Ingredient (
        @SerializedName("quantity")
        val quantity: Double,
        @SerializedName("measure")
        val measure: String,
        @SerializedName("ingredient")
        val ingredient: String
)
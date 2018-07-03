package zhuangzhi.android.baking.data

import com.google.gson.annotations.SerializedName

data class Step (
        @SerializedName("id")
        val id: Int,
        @SerializedName("shortDescription")
        val shortDescription: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("videoURL")
        val videoUrl: String,
        @SerializedName("thumbnailURL")
        val thumbnailUrl: String
)
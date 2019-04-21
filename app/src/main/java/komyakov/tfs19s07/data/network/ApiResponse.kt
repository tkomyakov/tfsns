package komyakov.tfs19s07.data.network

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("resultCode")
    val resultCode: String,

    @SerializedName("payload")
    val payload: T?,

    @SerializedName("trackingId")
    val trackingId: String
)
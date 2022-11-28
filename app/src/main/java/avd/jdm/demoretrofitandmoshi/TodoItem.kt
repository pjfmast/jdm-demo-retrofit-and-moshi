package avd.jdm.demoretrofitandmoshi

import com.squareup.moshi.Json

data class TodoItem(
    @Json(name = "todoId")
    val id: Int? = null,

    @Json(name = "userId")
    val userId: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "completed")
    val completed: Boolean,
)
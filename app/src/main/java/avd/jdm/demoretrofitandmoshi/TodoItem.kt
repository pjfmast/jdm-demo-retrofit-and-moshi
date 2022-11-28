package avd.jdm.demoretrofitandmoshi

data class TodoItem(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val completed: Boolean,
)
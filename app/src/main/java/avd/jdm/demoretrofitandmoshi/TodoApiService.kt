package avd.jdm.demoretrofitandmoshi

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): String // With Moshi we can convert the request result to: List<TodoItem>

    @DELETE("todos/{id}")
    suspend fun deleteItem(@Path("id") todoId: Int)

    @POST(value = "todos")
    suspend fun postItem(@Body todoItem: TodoItem): TodoItem
}

private val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object TodoApi {
    val retrofitService: TodoApiService by lazy {
        retrofit.create(TodoApiService::class.java)
    }
}
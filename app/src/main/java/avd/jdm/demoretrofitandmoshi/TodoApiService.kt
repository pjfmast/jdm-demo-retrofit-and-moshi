package avd.jdm.demoretrofitandmoshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


interface TodoApiService {
    @GET("todos")
    suspend fun getTodosWithResponse(): Response<List<TodoItem>>

    @GET("todos")
    suspend fun getTodos(): List<TodoItem> // With Moshi we can convert the request result to: List<TodoItem>

    @DELETE("todos/{id}")
    suspend fun deleteItemWithResponse(@Path("id") todoId: Int): Response<ResponseBody>

    @DELETE("todos/{id}")
    suspend fun deleteItem(@Path("id") todoId: Int)

    @POST(value = "todos")
    suspend fun postItemWithResponse(@Body todoItem: TodoItem): Response<TodoItem>

    @POST(value = "todos")
    suspend fun postItem(@Body todoItem: TodoItem): TodoItem


    @PUT(value = "todos/{id}")
    suspend fun putItem(@Body todoItem: TodoItem, @Path("id") todoId: Int): TodoItem
}

private val BASE_URL
//= "https://jsonplaceholder.typicode.com/"
=    "http://10.0.2.2:8080/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object TodoApi {
    val retrofitService: TodoApiService by lazy {
        retrofit.create(TodoApiService::class.java)
    }
}
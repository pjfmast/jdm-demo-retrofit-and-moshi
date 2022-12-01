package avd.jdm.demoretrofitandmoshi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


// accessible only in classes in this file
private const val TAG = "TodoViewModel"

class TodoViewModel : ViewModel() {

    private val _todoResponse: MutableLiveData<String> = MutableLiveData()
    val todoResponse: LiveData<String> // this livedata value is bound to a TextView.text property
        get() = _todoResponse

    init {
        getTodoItems()
    }

    fun getTodoItems() {
        viewModelScope.launch {
            _todoResponse.value = TodoApi.retrofitService.getTodos().toString()
        }
    }

    fun deleteTodoItemWithResponse() {
        viewModelScope.launch {
            // with handling response codes:
            val getAllresponse = TodoApi.retrofitService.getTodosWithResponse()
            if (getAllresponse.isSuccessful) {
                if (getAllresponse.body()?.isEmpty() ?: false) {
                    val firstTodoItemId = getAllresponse.body()!!.first().id!!
                    val deleteResponse =
                        TodoApi.retrofitService.deleteItemWithResponse(firstTodoItemId)
                    if (deleteResponse.isSuccessful) {
                        _todoResponse.value = "First todo item with id: ${firstTodoItemId} deleted"
                    } else {
                        _todoResponse.value =
                            "Item id: ${firstTodoItemId} not succesful deleted. Delete response code: ${deleteResponse.code()}"
                    }
                } else {
                    _todoResponse.value =
                        "No todo items to be deleted. Response code: ${getAllresponse.code()} message: ${getAllresponse.message()}"
                }
            } else {
                _todoResponse.value = "No fist item found. Response code: ${getAllresponse.code()}"
            }

        }
    }

    fun deleteTodoItem() {
        viewModelScope.launch {

            // without handling response codes:
            val todos = TodoApi.retrofitService.getTodos()
            if (todos.isNotEmpty()) {
                val firstTodoId = todos.first().id!!
                TodoApi.retrofitService.deleteItem(firstTodoId)
                _todoResponse.value = "First todo item with id: ${firstTodoId} deleted"
            } else {
                _todoResponse.value = "No todo items to be deleted"
            }

        }
    }


    fun postTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            TodoApi.retrofitService.postItem(todoItem)
            _todoResponse.value = "postTodoItem: ${todoItem} posted"
        }
    }

    fun putTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            TodoApi.retrofitService.putItem(todoItem, todoItem.id!!)
            _todoResponse.value = "todo item with id: ${todoItem.id} updated"
        }
    }
}
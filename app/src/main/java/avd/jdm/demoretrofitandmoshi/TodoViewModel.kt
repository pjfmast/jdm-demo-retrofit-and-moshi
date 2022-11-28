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

    fun deleteTodoItem(todoId: Int) {
        viewModelScope.launch {
            TodoApi.retrofitService.deleteItem(todoId)
            _todoResponse.value = "deleteTodoItem: ${todoId} deleted"
        }
    }

    fun postTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            TodoApi.retrofitService.postItem(todoItem)
            _todoResponse.value = "postTodoItem: ${todoItem} posted"
        }
    }
}
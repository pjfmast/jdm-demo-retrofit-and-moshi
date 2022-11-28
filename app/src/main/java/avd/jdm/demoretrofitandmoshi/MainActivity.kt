package avd.jdm.demoretrofitandmoshi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import avd.jdm.demoretrofitandmoshi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val todoViewModel:TodoViewModel by viewModels()
        todoViewModel.todoResponse.observe(this) {
            binding.result.text = todoViewModel.todoResponse.value
        }

        binding.getBtn.setOnClickListener {
            todoViewModel.getTodoItems()
        }

        binding.deleteBtn.setOnClickListener {
            todoViewModel.deleteTodoItem(1)
        }

        binding.postBtn.setOnClickListener {
            val todoItem = TodoItem(1, null, "Learn Moshi", false)
            todoViewModel.postTodoItem(todoItem)
        }
    }
}
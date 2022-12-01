package avd.jdm.demoretrofitandmoshi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import avd.jdm.demoretrofitandmoshi.databinding.ActivityMainBinding
import io.github.serpro69.kfaker.Faker

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val faker = Faker()
    private var lastId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val todoViewModel: TodoViewModel by viewModels()
        todoViewModel.todoResponse.observe(this) {
            binding.result.text = todoViewModel.todoResponse.value
        }

        binding.getBtn.setOnClickListener {
            todoViewModel.getTodoItems()
        }

        binding.deleteBtn.setOnClickListener {
            todoViewModel.deleteTodoItem()
        }

        binding.postBtn.setOnClickListener {
            val todoItem = TodoItem(
                null,
                faker.random.nextInt(10, 100),
                faker.food.dish(),
                faker.random.nextBoolean()
            )
            todoViewModel.postTodoItem(todoItem)
        }

        binding.putBtn.setOnClickListener {
            val todoItem = TodoItem(
                1,
                faker.random.nextInt(10, 100),
                faker.food.dish(),
                faker.random.nextBoolean()
            )
            todoViewModel.putTodoItem(todoItem)
        }
    }
}
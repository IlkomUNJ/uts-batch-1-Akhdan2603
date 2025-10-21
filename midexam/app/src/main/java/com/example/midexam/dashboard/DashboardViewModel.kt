package midexam.ui.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Student(
    val id: String,
    val name: String,
    val phone: String,
    val address: String
)

data class DashboardUiState(
    val isLoading: Boolean = false
)

class DashboardViewModel : ViewModel() {

    private companion object StudentStorage {
        private val _students = MutableStateFlow<List<Student>>(emptyList())
        val students: StateFlow<List<Student>> = _students.asStateFlow()
    }

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    val studentListState: StateFlow<List<Student>> = StudentStorage.students

    fun addStudent(student: Student) {
        StudentStorage._students.update { currentList ->
            if (currentList.any { it.id == student.id }) {
                currentList
            } else {
                (currentList + student).sortedBy { it.id }
            }
        }
    }
}
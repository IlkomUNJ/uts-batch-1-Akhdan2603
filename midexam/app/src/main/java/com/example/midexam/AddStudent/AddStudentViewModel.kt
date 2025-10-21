package midexam.ui.addstudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import midexam.ui.dashboard.Student

data class AddStudentUiState(
    val studentId: String = "",
    val studentName: String = "",
    val studentPhone: String = "",
    val studentAddress: String = "",
    val isRegistering: Boolean = false,
    val error: String? = null
)

class AddStudentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddStudentUiState())
    val uiState: StateFlow<AddStudentUiState> = _uiState

    fun updateId(value: String) { _uiState.value = _uiState.value.copy(studentId = value.take(10), error = null) }
    fun updateName(value: String) { _uiState.value = _uiState.value.copy(studentName = value, error = null) }
    fun updatePhone(value: String) { _uiState.value = _uiState.value.copy(studentPhone = value.take(15), error = null) }
    fun updateAddress(value: String) { _uiState.value = _uiState.value.copy(studentAddress = value, error = null) }

    fun registerStudent(
        onStudentAdded: (Student) -> Unit,
        onSuccess: () -> Unit
    ) {
        if (isInputValid()) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isRegistering = true, error = null)

                kotlinx.coroutines.delay(1000)

                val newStudent = Student(
                    id = _uiState.value.studentId,
                    name = _uiState.value.studentName,
                    phone = _uiState.value.studentPhone,
                    address = _uiState.value.studentAddress
                )

                onStudentAdded(newStudent)

                _uiState.value = _uiState.value.copy(isRegistering = false)
                onSuccess()
            }
        }
    }

    private fun isInputValid(): Boolean {
        return when {
            _uiState.value.studentId.isBlank() -> {
                _uiState.value = _uiState.value.copy(error = "Student ID harus diisi."); false
            }
            _uiState.value.studentName.isBlank() -> {
                _uiState.value = _uiState.value.copy(error = "Student Name harus diisi."); false
            }
            else -> true
        }
    }
}
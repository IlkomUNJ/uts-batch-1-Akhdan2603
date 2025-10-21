package midexam.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {

    private val CORRECT_USERNAME = "akhdan"
    private val CORRECT_PASSWORD = "akhdan123"

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun updateUsername(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername, error = null)
    }

    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, error = null)
    }

    fun login(onSuccess: () -> Unit) {
        if (_uiState.value.username.isBlank() || _uiState.value.password.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Username dan Password harus diisi.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            delay(1500)

            if (_uiState.value.username == CORRECT_USERNAME && _uiState.value.password == CORRECT_PASSWORD) {
                _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Username atau Password salah. Coba: $CORRECT_USERNAME/$CORRECT_PASSWORD"
                )
            }
        }
    }
}
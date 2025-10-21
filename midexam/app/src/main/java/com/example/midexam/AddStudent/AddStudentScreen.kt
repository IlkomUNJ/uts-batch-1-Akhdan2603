package midexam.ui.addstudent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import midexam.ui.dashboard.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(
    viewModel: AddStudentViewModel,
    onStudentAdded: (Student) -> Unit,
    onRegistrationSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    val isButtonEnabled = !state.isRegistering && state.studentId.isNotBlank() && state.studentName.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Student Form") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = state.studentId,
                onValueChange = viewModel::updateId,
                label = { Text("Student ID (Wajib)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isRegistering
            )

            OutlinedTextField(
                value = state.studentName,
                onValueChange = viewModel::updateName,
                label = { Text("Student Name (Wajib)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isRegistering
            )

            OutlinedTextField(
                value = state.studentPhone,
                onValueChange = viewModel::updatePhone,
                label = { Text("Student Phone (Disimpan)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isRegistering
            )

            OutlinedTextField(
                value = state.studentAddress,
                onValueChange = viewModel::updateAddress,
                label = { Text("Student Address (Disimpan)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isRegistering
            )

            Spacer(modifier = Modifier.height(8.dp))

            state.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    viewModel.registerStudent(onStudentAdded, onRegistrationSuccess)
                },
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFA5D6A7),
                    disabledContentColor = Color.White
                )
            ) {
                if (state.isRegistering) {
                    CircularProgressIndicator(
                        Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("Register Student")
                }
            }
        }
    }
}
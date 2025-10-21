package com.example.midexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import midexam.ui.dashboard.DashboardScreen
import midexam.ui.dashboard.DashboardViewModel
import midexam.ui.login.LoginScreen
import midexam.ui.addstudent.AddStudentScreen
import midexam.ui.addstudent.AddStudentViewModel
import midexam.ui.dashboard.Student
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.midexam.ui.theme.MidexamTheme

class MainActivity : ComponentActivity() {

    private object Destinations {
        const val LOGIN_ROUTE = "login"
        const val DASHBOARD_ROUTE = "dashboard"
        const val ADD_STUDENT_ROUTE = "addstudent"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidexamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigationGraph()
                }
            }
        }
    }

    @Composable
    private fun AppNavigationGraph() {
        val navController = rememberNavController()

        val dashboardViewModel: DashboardViewModel = viewModel()

        NavHost(
            navController = navController,
            startDestination = Destinations.LOGIN_ROUTE
        ) {

            composable(Destinations.LOGIN_ROUTE) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Destinations.DASHBOARD_ROUTE) {
                            popUpTo(Destinations.LOGIN_ROUTE) { inclusive = true }
                        }
                    }
                )
            }

            composable(Destinations.DASHBOARD_ROUTE) {
                DashboardScreen(
                    viewModel = dashboardViewModel,
                    onEditItem = {
                    },
                    onFabClicked = {
                        navController.navigate(Destinations.ADD_STUDENT_ROUTE)
                    }
                )
            }

            composable(Destinations.ADD_STUDENT_ROUTE) {
                val addStudentViewModel: AddStudentViewModel = viewModel()

                AddStudentScreen(
                    viewModel = addStudentViewModel,
                    onStudentAdded = { newStudent: Student ->
                        dashboardViewModel.addStudent(newStudent)
                    },
                    onRegistrationSuccess = {
                        navController.popBackStack()
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
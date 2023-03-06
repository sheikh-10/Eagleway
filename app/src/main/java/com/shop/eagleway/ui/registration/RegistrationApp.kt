package com.shop.eagleway.ui.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shop.eagleway.BottomNavItem
import com.shop.eagleway.ui.SignupScreen

@Composable
fun RegistrationApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = RegistrationScreens.SignupScreen.name) {
        composable(route = RegistrationScreens.SignupScreen.name) {
            SignupScreen()
        }
    }

}

enum class RegistrationScreens(val title: String) {
    SignupScreen("Signup")
}

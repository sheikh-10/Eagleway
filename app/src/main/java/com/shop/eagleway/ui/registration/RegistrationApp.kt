package com.shop.eagleway.ui.registration

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shop.eagleway.ui.main.HomeActivity
import com.shop.eagleway.ui.registration.signup.SignupOTPScreen
import com.shop.eagleway.ui.registration.signup.SignupPhoneScreen
import com.shop.eagleway.ui.registration.signup.SignupScreen
import com.shop.eagleway.viewmodel.RegistrationViewModel

@Composable
fun RegistrationApp(modifier: Modifier = Modifier, viewModel: RegistrationViewModel = viewModel(), activity: Activity?) {
    val navController = rememberNavController()
    val context = LocalContext.current


    NavHost(navController, startDestination = RegistrationScreens.SignupScreen.name) {
        composable(route = RegistrationScreens.SignupScreen.name) {
            SignupScreen {
                navController.navigate(RegistrationScreens.SignupPhoneScreen.name)
            }
        }
        composable(route = RegistrationScreens.SignupPhoneScreen.name) {
            SignupPhoneScreen(
                onBack = { navController.navigateUp() },
                onNext = {
                    viewModel.registerPhoneNumber(
                        context = context,
                        onNextScreen = {
                            navController.navigate(RegistrationScreens.SignupOTPScreen.name)
                            viewModel.updateSmsCodeInput("")
                        }
                        ) },
                phoneNumber = viewModel.userPhoneInput,
                onPhoneNumberInput = { viewModel.updatePhoneNumber(it) }
            )
        }
        composable(route = RegistrationScreens.SignupOTPScreen.name) {
            SignupOTPScreen(
                onBack = { navController.navigateUp() },
                onNext = {
                    viewModel.checkOTP(
                        onNextScreenSignedInUser = {
                            activity?.finish()
                            HomeActivity.startActivity(activity) },
                        onNextScreenNewUser = {
                            activity?.finish()
                            HomeActivity.startActivity(activity) },
                    )

                },
                smsCode = viewModel.smsCode,
                onSmsCodeInput = { viewModel.updateSmsCodeInput(it) }
            )
        }
    }

}

enum class RegistrationScreens {
    SignupScreen,
    SignupPhoneScreen,
    SignupOTPScreen
}

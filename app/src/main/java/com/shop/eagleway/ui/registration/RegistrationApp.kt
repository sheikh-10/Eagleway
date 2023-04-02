package com.shop.eagleway.ui.registration

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shop.eagleway.ui.registration.login.LoginOTPScreen
import com.shop.eagleway.ui.registration.login.LoginPhoneScreen
import com.shop.eagleway.ui.main.HomeActivity
import com.shop.eagleway.ui.registration.signup.*
import com.shop.eagleway.viewmodel.RegistrationViewModel

@Composable
fun RegistrationApp(modifier: Modifier = Modifier, viewModel: RegistrationViewModel = viewModel(), activity: Activity?) {
    val navController = rememberNavController()
    val context = LocalContext.current


    NavHost(navController, startDestination = RegistrationScreens.SignupScreen.name) {
        composable(route = RegistrationScreens.SignupScreen.name) {
            SignupScreen(
                onClickSignupScreen = { navController.navigate(RegistrationScreens.SignupPhoneScreen.name) },
                onClickLoginScreen = { navController.navigate(RegistrationScreens.LoginPhoneScreen.name) }
            )
        }
        composable(route = RegistrationScreens.SignupPhoneScreen.name) {
            SignupPhoneScreen(
                onBack = {
                    viewModel.resetUserInput()
                    navController.navigateUp()
                         },
                onNext = {
                    viewModel.registerPhoneNumber(
                        context = context,
                        onNextScreen = {
                            navController.navigate(RegistrationScreens.SignupOTPScreen.name)
                            viewModel.updateSmsCodeInput("")
                        },
                        isCalledFromSignup = true
                        ) },
                phoneNumber = viewModel.userPhoneInput,
                onPhoneNumberInput = { viewModel.updatePhoneNumber(it) },
                countryCode = viewModel.userCountryCodeInput,
                onCountryCodeInput = { viewModel.updateCountryCode(it) },
                isUserSignedIn = viewModel.isSignedInUser
            )
        }
        composable(route = RegistrationScreens.SignupOTPScreen.name) {
            SignupOTPScreen(
                onBack = { navController.navigateUp() },
                onNext = {
                    viewModel.checkOTPSignup {
                        navController.navigate(RegistrationScreens.CreateBusinessInfoScreen.name)
                    }
                },
                smsCode = viewModel.smsCode,
                onSmsCodeInput = { viewModel.updateSmsCodeInput(it) }
            )
        }

        composable(route = RegistrationScreens.CreateBusinessInfoScreen.name) {
            CreateBusinessInfoScreen(
                onBack = { navController.navigateUp() },
                onNext = {  activity?.finish()
                    HomeActivity.startActivity(activity)
                },
                userName = viewModel.userName,
                businessName = viewModel.businessName,
                onUserNameInput = { viewModel.updateUserInfo(it) },
                onBusinessNameInput = { viewModel.updateBusinessInfo(it) }
            )
        }

        composable(route = RegistrationScreens.LoginPhoneScreen.name) {
            LoginPhoneScreen(
                onBack = {
                    viewModel.resetUserInput()
                    navController.navigateUp() },
                onNext = {
                    viewModel.registerPhoneNumber(
                        context = context,
                        onNextScreen = {
                            navController.navigate(RegistrationScreens.LoginOTPScreen.name)
                            viewModel.updateSmsCodeInput("")
                        },
                        isCalledFromSignup = false
                    ) },
                phoneNumber = viewModel.userPhoneInput,
                onPhoneNumberInput = { viewModel.updatePhoneNumber(it) },
                countryCode = viewModel.userCountryCodeInput,
                onCountryCodeInput = { viewModel.updateCountryCode(it) },
                isUserSignedIn = viewModel.isSignedInUser)
        }

        composable(route = RegistrationScreens.LoginOTPScreen.name) {
            LoginOTPScreen(
                onBack = { navController.navigateUp() },
                onNext = {
                    viewModel.checkOTPLogin {
                        activity?.finish()
                        HomeActivity.startActivity(activity)
                    }
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
    SignupOTPScreen,
    CreateBusinessInfoScreen,

    LoginPhoneScreen,
    LoginOTPScreen
}

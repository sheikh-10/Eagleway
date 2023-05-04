package com.shop.eagleway.ui.registration

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.shop.eagleway.R
import com.shop.eagleway.ui.main.HomeActivity
import com.shop.eagleway.utility.toast
import com.shop.eagleway.viewmodel.RegistrationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "RegisterScreen"
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, viewModel: RegistrationViewModel = viewModel(), activity: Activity? = null) {

    var isFinished by remember { mutableStateOf(true) }
    var screenState: RegistrationStates by remember { mutableStateOf(RegistrationStates.ShowEmpty) }

    var isChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val modifierOne = modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.purple_1),
                    colorResource(id = R.color.purple_2)
                )
            )
        )
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)
        .clickable(onClick = {
            viewModel.resetUserInput()
            screenState = RegistrationStates.ShowNews
        })

    val modifierTwo = modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.purple_1),
                    colorResource(id = R.color.purple_2)
                )
            )
        )
        .fillMaxSize()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .padding(top = 48.dp)
        .clickable(onClick = {
            viewModel.resetUserInput()
            screenState = RegistrationStates.ShowNews
        })


    Box {
        Column(modifier = if (screenState == RegistrationStates.ShowEmpty) modifierOne else modifierTwo) {

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    )) {
                Image(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    modifier = modifier.size(if (screenState == RegistrationStates.ShowEmpty) 60.dp else 30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Spacer(modifier = modifier.width(5.dp))

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = if (screenState == RegistrationStates.ShowEmpty) MaterialTheme.typography.h4 else MaterialTheme.typography.h6,
                    color = Color.White)
            }

            Spacer(modifier = modifier.height(40.dp))

            if (!isFinished) {
                Image(painter = painterResource(id = R.drawable.slide_image_pic1),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .size(400.dp)
                )
            }
        }

        if (!isFinished) {
            Column(modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)) {

                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    modifier = modifier.animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    ),
                    backgroundColor = Color.White) {

                    when (screenState) {
                        RegistrationStates.ShowNews -> {
                            val pagerState = rememberPagerState()

                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                                    .padding(top = 20.dp)) {

                                repeat(3) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_circle),
                                        contentDescription = null,
                                        modifier = if (pagerState.currentPage == it) Modifier.size(15.dp) else Modifier.size(10.dp),
                                        tint = if (pagerState.currentPage == it) colorResource(id = R.color.purple_1) else Color.LightGray
                                    )

                                    Spacer(modifier = modifier.width(2.dp))
                                }
                            }

                            HorizontalPager(count = 3, state = pagerState) {
                                when (it) {
                                    0 -> PagerContentOne(modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(align = Alignment.CenterHorizontally)) { pagerState.animateScrollToPage(1) }


                                    1 -> PagerContentTwo(modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(align = Alignment.CenterHorizontally)) { pagerState.animateScrollToPage(2) }


                                    2 -> PagerContentThree(modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(align = Alignment.CenterHorizontally)) {
                                        screenState = RegistrationStates.ShowRegister
                                    }
                                }
                            }
                        }
                        RegistrationStates.ShowRegister -> {
                            SignUpSheet(
                                isChecked = isChecked,
                                onCheckedClick = { isChecked = it },
                                onContinueClick = {
                                    viewModel.registerPhoneNumber(
                                        context = context,
                                        onNextScreen = {
                                            screenState = RegistrationStates.ShowRegisterOTP
                                            viewModel.updateSmsCodeInput("")
                                        },
                                        isCalledFromSignup = true
                                    )},
                                isLoginClicked = {
                                    isChecked = false
                                    viewModel.resetUserInput()
                                    screenState = RegistrationStates.ShowLogin },
                                phoneNumber = viewModel.userPhoneInput,
                                onPhoneNumberInput = { viewModel.updatePhoneNumber(it) },
                                countryCode = viewModel.userCountryCodeInput,
                                onCountryCodeInput = { viewModel.updateCountryCode(it) },
                                isUserSignedIn = viewModel.isSignedInUser
                                )
                        }
                        RegistrationStates.ShowLogin -> {
                            LoginSheet(
                                onContinueClick = {
                                    viewModel.registerPhoneNumber(
                                        context = context,
                                        onNextScreen = {
                                            screenState = RegistrationStates.ShowLoginOTP
                                            viewModel.updateSmsCodeInput("")
                                        },
                                        isCalledFromSignup = false
                                    ) },
                                isSignupClicked = {
                                    isChecked = false
                                    viewModel.resetUserInput()
                                    screenState = RegistrationStates.ShowRegister },
                                phoneNumber = viewModel.userPhoneInput,
                                onPhoneNumberInput = { viewModel.updatePhoneNumber(it) },
                                countryCode = viewModel.userCountryCodeInput,
                                onCountryCodeInput = { viewModel.updateCountryCode(it) },
                                isUserSignedIn = viewModel.isSignedInUser
                                )
                        }
                        RegistrationStates.ShowRegisterOTP -> { OTPSignupSheet(
                            smsCode = viewModel.smsCode,
                            onSmsCodeInput = { viewModel.updateSmsCodeInput(it) },
                            onContinueClick = {
                                viewModel.checkOTPSignup {
                                    screenState = RegistrationStates.ShowBusinessInfo
                                }
                            }
                        ) }
                        RegistrationStates.ShowLoginOTP -> { OTPLoginSheet(
                            smsCode = viewModel.smsCode,
                            onSmsCodeInput = { viewModel.updateSmsCodeInput(it) },
                            onContinueClick = {
                                viewModel.checkOTPLogin(
                                    onNextScreenSignedInUser =  {
                                        activity?.finish()
                                        HomeActivity.startActivity(activity)
                                    },
                                    context = context)
                            }
                        ) }
                        else -> {
                           BusinessInfoSheet(
                               onContinueClick = {
                                   viewModel.writeUserInfoToDatabase(context)
                                   activity?.finish()
                                   HomeActivity.startActivity(activity)
                               },
                               userName = viewModel.userName,
                               businessName = viewModel.businessName,
                               onUserNameInput = { viewModel.updateUserInfo(it) },
                               onBusinessNameInput = { viewModel.updateBusinessInfo(it) }
                           )
                        }
//                        else -> {  }
                    }
                }
            }
        }
    }

    if (screenState == RegistrationStates.ShowEmpty) {
        LaunchedEffect(isFinished) {
            delay(3000L)
            isFinished = !isFinished
            screenState = RegistrationStates.ShowNews
        }
    }
}

@Composable
private fun PagerContentOne(modifier: Modifier = Modifier, onClick: suspend () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = modifier.height(20.dp))
        
        Text(text = "The e-commerce platform",
            modifier = modifier,
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
            )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = "Unleash Your Business Potential with Eagleway",
            style = MaterialTheme.typography.body1,
            modifier = modifier)

        Spacer(modifier = modifier.height(20.dp))

        FloatingActionButton(onClick = {
            coroutineScope.launch {
                onClick()
            }
        },
            modifier = modifier,
            backgroundColor = colorResource(id = R.color.purple_1)
            ) {
            Icon(imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                )
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}

@Composable
private fun PagerContentTwo(modifier: Modifier = Modifier, onClick: suspend () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Create your online business",
            modifier = modifier,
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = "You can create your online business instantly",
            style = MaterialTheme.typography.body1,
            modifier = modifier)

        Spacer(modifier = modifier.height(20.dp))

        FloatingActionButton(onClick = {
            coroutineScope.launch {
                onClick()
            }
        },
            modifier = modifier,
            backgroundColor = colorResource(id = R.color.purple_1)
        ) {
            Icon(imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                tint = Color.White,
            )
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}

@Composable
private fun PagerContentThree(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Get Started With Eagleway",
            modifier = modifier,
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = "Create account and start online business",
            style = MaterialTheme.typography.body1,
            modifier = modifier)

        Spacer(modifier = modifier.height(20.dp))

        FloatingActionButton(onClick = onClick,
            modifier = modifier,
            backgroundColor = colorResource(id = R.color.purple_1)
        ) {
            Icon(imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                tint = Color.White,
            )
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun BusinessInfoSheet(modifier: Modifier = Modifier,
                              onContinueClick: () -> Unit = {},
                              userName: String = "",
                              businessName: String = "",
                              onUserNameInput: (String) -> Unit = {},
                              onBusinessNameInput: (String) -> Unit = {}) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "Let's begin to set you up!",
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Please add your business information to get started")

        Spacer(modifier = modifier.height(20.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = onUserNameInput,
            label = { Text(text = "Your name") },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            singleLine = true,
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
        )

        OutlinedTextField(
            value = businessName,
            onValueChange = onBusinessNameInput,
            label = { Text(text = "Business name") },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
        )

        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = onContinueClick,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_1)),
            enabled = true
        ) {
            Text(text = "Continue", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun OTPSignupSheet(modifier: Modifier = Modifier,
                     smsCode: String = "",
                     onSmsCodeInput: (String) -> Unit = {},
                     onContinueClick: () -> Unit = {}) {

    val focusManager = LocalFocusManager.current
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "OTP",
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Verification code")

        Spacer(modifier = modifier.height(20.dp))

        BasicTextField(
            value = smsCode,
            onValueChange = {
                if (it.length <= 6) {
                    onSmsCodeInput(it)
                }
            },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(6) {
                        val char = when {
                            it >= smsCode.length -> ""
                            else -> smsCode[it].toString()
                        }

                        val isFocused = smsCode.length == it

                        isChecked = smsCode.length == 6

                        Text(
                            text = char,
                            modifier = modifier
                                .width(40.dp)
                                .border(
                                    if (isFocused) 2.dp
                                    else 1.dp,
                                    if (isFocused) colorResource(id = R.color.purple_1)
                                    else Color.LightGray, RoundedCornerShape(8.dp)
                                )
                                .padding(2.dp),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center)

                        Spacer(modifier = modifier.width(8.dp))
                    }
                }
            },
            )

        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = onContinueClick,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_1)),
            enabled = isChecked
        ) {
            Text(text = "Continue", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpSheet(modifier: Modifier = Modifier,
                        isChecked: Boolean = false,
                        onCheckedClick: (Boolean) -> Unit = {},
                        onContinueClick: () -> Unit = {},
                        isLoginClicked: () -> Unit = {},
                        onBack: () -> Unit = {},
                        onNext: () -> Unit = {},
                        phoneNumber: String = "",
                        onPhoneNumberInput: (String) -> Unit = {},
                        countryCode: String = "",
                        onCountryCodeInput: (String) -> Unit = {},
                        isUserSignedIn: Boolean = false) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "Sign Up",
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Phone Number")
        Spacer(modifier = modifier.height(10.dp))

        Row {
            OutlinedTextField(
                value = countryCode,
                onValueChange = onCountryCodeInput,
                label = { Text(text = "Country") },
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                modifier = modifier.width(100.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Right) }
                ),
                singleLine = true
            )

            Spacer(modifier = modifier.width(10.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberInput,
                label = { Text(text = if (!isUserSignedIn) "Mobile Number" else "User already signed in") },
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                modifier = modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                isError = isUserSignedIn,
            )
        }

        Spacer(modifier = modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onCheckedClick(!isChecked) }) {
                Icon(painter = if (isChecked) painterResource(id = R.drawable.ic_check_box) else painterResource(id = R.drawable.ic_empty_check_box),
                    contentDescription = null,
                    modifier = modifier.size(30.dp))
            }

            Text(text = "I agree to the Terms & Conditions and Privacy Policy")
        }

        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = onContinueClick,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_1)),
            enabled = isChecked
        ) {
            Text(text = "Continue", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)) {

            Text(text = "Already have an account?", fontFamily = FontFamily.SansSerif)

            TextButton(onClick = isLoginClicked) {
                Text(text = "Login",
                    fontFamily = FontFamily.SansSerif,
                    color = colorResource(id = R.color.purple_1),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun OTPLoginSheet(modifier: Modifier = Modifier,
                           smsCode: String = "",
                           onSmsCodeInput: (String) -> Unit = {},
                           onContinueClick: () -> Unit = {}) {

    val focusManager = LocalFocusManager.current
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "OTP",
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Verification code")

        Spacer(modifier = modifier.height(20.dp))

        BasicTextField(
            value = smsCode,
            onValueChange = {
                if (it.length <= 6) {
                    onSmsCodeInput(it)
                }
            },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(6) {
                        val char = when {
                            it >= smsCode.length -> ""
                            else -> smsCode[it].toString()
                        }

                        val isFocused = smsCode.length == it

                        isChecked = smsCode.length == 6

                        Text(
                            text = char,
                            modifier = modifier
                                .width(40.dp)
                                .border(
                                    if (isFocused) 2.dp
                                    else 1.dp,
                                    if (isFocused) colorResource(id = R.color.purple_1)
                                    else Color.LightGray, RoundedCornerShape(8.dp)
                                )
                                .padding(2.dp),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center)

                        Spacer(modifier = modifier.width(8.dp))
                    }
                }
            },
        )

        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = onContinueClick,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_1)),
            enabled = isChecked
        ) {
            Text(text = "Continue", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginSheet(modifier: Modifier = Modifier,
                       onContinueClick: () -> Unit = {},
                       isSignupClicked: () -> Unit = {},
                       phoneNumber: String = "",
                       onPhoneNumberInput: (String) -> Unit = {},
                       countryCode: String = "",
                       onCountryCodeInput: (String) -> Unit = {},
                       isUserSignedIn: Boolean = false) {

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "Login",
            color = colorResource(id = R.color.purple_1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = modifier.height(20.dp))

        Text(text = "Phone Number")
        Spacer(modifier = modifier.height(10.dp))

        Row {
            OutlinedTextField(
                value = countryCode,
                onValueChange = onCountryCodeInput,
                label = { Text(text = "Country") },
                modifier = modifier.width(100.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Right) }
                ),
                singleLine = true,
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
            )

            Spacer(modifier = modifier.width(10.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberInput,
                label = { Text(text = if (!isUserSignedIn) "Mobile number" else "Create a new account" )},
                modifier = modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                isError = isUserSignedIn,
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
            )
        }

        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = onContinueClick,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_1)),
            enabled = true
        ) {
            Text(text = "Continue", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)) {

            Text(text = "Don't have an account", fontFamily = FontFamily.SansSerif)

            TextButton(onClick = isSignupClicked) {
                Text(text = "Signup",
                    fontFamily = FontFamily.SansSerif,
                    color = colorResource(id = R.color.purple_1),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}

enum class RegistrationStates {
    ShowEmpty,
    ShowNews,
    ShowRegister,
    ShowRegisterOTP,
    ShowLogin,
    ShowLoginOTP,
    ShowBusinessInfo
}
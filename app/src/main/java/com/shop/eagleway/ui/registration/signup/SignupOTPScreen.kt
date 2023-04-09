package com.shop.eagleway.ui.registration.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun SignupOTPScreen(modifier: Modifier = Modifier,
                    onBack: () -> Unit = {},
                    smsCode: String = "",
                    onSmsCodeInput: (String) -> Unit = {},
                    onNext: () -> Unit = {}
                    ) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(modifier = modifier
            .fillMaxWidth()
            .size(30.dp)) {

            IconButton(onClick = onBack, modifier = modifier.size(24.dp)) {
                Icon(imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Button")
            }

            Text(text = "OTP", fontSize = 18.sp, modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally))
        }


        Text(text = "Verification Code",
            fontSize = 18.sp,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally))

        Text(
            text = "Enter the 6 digit code sent to you to your mobile number" ,
            fontSize = 18.sp
            )

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

                        Text(
                            text = char,
                            modifier = modifier
                                .width(40.dp)
                                .border(
                                    if(isFocused) 2.dp
                                    else 1.dp,
                                    if (isFocused) Color.DarkGray
                                    else Color.LightGray
                                    , RoundedCornerShape(8.dp))
                                .padding(2.dp),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center)

                        Spacer(modifier = modifier.width(8.dp))
                    }
                }
            },

            )

        Spacer(modifier = modifier.height(20.dp))

        Button(onClick = onNext, modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .clip(RoundedCornerShape(20))) {
            Text(text = "Continue", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignupOTPScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        SignupOTPScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SignupOTPScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        SignupOTPScreen()
    }
}
package com.shop.eagleway.ui.registration.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun LoginPhoneScreen(modifier: Modifier = Modifier,
                     onBack: () -> Unit = {},
                     onNext: () -> Unit = {},
                     phoneNumber: String = "",
                     onPhoneNumberInput: (String) -> Unit = {},
                     countryCode: String = "",
                     onCountryCodeInput: (String) -> Unit = {},
                     isUserSignedIn: Boolean = false

                     ) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .size(30.dp)
        ) {

            IconButton(onClick = onBack, modifier = modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Button"
                )
            }

            Text(
                text = "Login", fontSize = 18.sp, modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )
        }

        Text(text = "Welcome Back", fontSize = 30.sp)
        Text(text = "Enter your details to continue")

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = countryCode,
                onValueChange = onCountryCodeInput,
                label = { Text(text = "Country") },
                modifier = modifier.width(100.dp)
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberInput,
                label = { Text(text = if (!isUserSignedIn) "Mobile number" else "Create a new account" )},
                modifier = modifier.weight(1f),
                isError = isUserSignedIn
                )
        }

        Spacer(modifier = modifier.height(40.dp))

        Button(onClick = onNext, modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .clip(RoundedCornerShape(20))) {
            Text(text = "Continue", fontSize = 20.sp)
        }

        BackHandler(enabled = true) {
            onBack()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        LoginPhoneScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        LoginPhoneScreen()
    }
}
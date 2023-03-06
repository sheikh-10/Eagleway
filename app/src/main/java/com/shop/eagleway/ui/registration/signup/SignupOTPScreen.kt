package com.shop.eagleway.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.Contacts.Intents.UI
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
fun SignupOTPScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Button"
                )
            }

            Text(
                text = "OTP",
                fontSize = 18.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )
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

        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = modifier.fillMaxWidth()
            )

        Spacer(modifier = modifier.height(20.dp))

        Button(onClick = {}, modifier = modifier
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
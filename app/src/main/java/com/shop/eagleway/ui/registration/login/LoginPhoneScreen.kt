package com.shop.eagleway.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
fun LoginPhoneScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back Button")
            }

            Text(text = "Login",
                fontSize = 18.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally))
        }

        Text(text = "Welcome Back", fontSize = 30.sp)
        Text(text = "Enter your details to continue")

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = "91",
                onValueChange = {},
                label = { Text(text = "Country") },
                modifier = modifier.width(100.dp)
            )

            OutlinedTextField(
                value = "999999999",
                onValueChange = {},
                label = { Text(text = "Mobile number") },
                modifier = modifier.weight(1f))
        }

        Spacer(modifier = modifier.height(40.dp))

        Button(onClick = {}, modifier = modifier.height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .clip(RoundedCornerShape(20))) {
            Text(text = "Continue", fontSize = 20.sp)
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
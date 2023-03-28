package com.shop.eagleway.ui.registration.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun CreateBusinessInfoScreen(modifier: Modifier = Modifier,
                             onBack: () -> Unit = {},
                             onNext: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
        }
        Text(text = "Let's begin to set you up!", style = MaterialTheme.typography.h6)
        Text(text = "Please add your business information to get started", style = MaterialTheme.typography.body2)

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Your name") },
            modifier = modifier.fillMaxWidth()
            )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Business name") },
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.weight(1f))

        Button(onClick = onNext,
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20))) {
            Text(text = "Register")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateBusinessInfoScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        CreateBusinessInfoScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CreateBusinessInfoScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        CreateBusinessInfoScreen()
    }
}
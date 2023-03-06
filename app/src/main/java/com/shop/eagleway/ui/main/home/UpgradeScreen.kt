package com.shop.eagleway.ui.main.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun UpgradeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back Button",
                modifier = modifier.wrapContentWidth(align = Alignment.Start)
            )

            Text(text = "Subscription Plans",
                fontSize = 18.sp,
                modifier = modifier.fillMaxWidth().wrapContentWidth(align = Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UpgradeScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        UpgradeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NotificationScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        UpgradeScreen()
    }
}
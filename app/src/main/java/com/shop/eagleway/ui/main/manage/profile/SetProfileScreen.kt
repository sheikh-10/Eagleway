package com.shop.eagleway.ui.main.manage.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SetProfileScreen(modifier: Modifier = Modifier,
                     viewModel: HomeViewModel = viewModel()) {

    Column(modifier = modifier.fillMaxSize()) {
        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Set Profile", fontSize = 18.sp)
            }
        }

        Column(modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)) {

            TextField(
                value = viewModel.userName,
                onValueChange = { viewModel.updateUserName(it) },
                label = { Text(text = "User Name")  },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                )

            TextField(
                value = viewModel.userNum,
                onValueChange = {},
                label = { Text(text = "Phone number")  },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            TextField(
                value = viewModel.userLanguage,
                onValueChange = { viewModel.updateLanguage(it) },
                label = { Text(text = "Language")  },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            TextField(
                value = viewModel.userEmail,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text(text = "Email")  },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            OutlinedButton(
                onClick = { viewModel.updateUserNameToDatabase()},
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SetProfileScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        SetProfileScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SetProfileScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        SetProfileScreen()
    }
}
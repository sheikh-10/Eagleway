package com.shop.eagleway.ui.registration.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun SignupScreen(modifier: Modifier = Modifier,
                 onClickSignupScreen: () -> Unit = {},
                 onClickLoginScreen: () -> Unit = {},
                 ) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Eagleway", fontSize = 30.sp)
        }

        Text(
            text = "Trusted by 1 lakh Business",
            fontSize = 18.sp,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )

        Spacer(modifier = modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.slide_image_pic1),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .size(300.dp),
            contentScale = ContentScale.Crop
            )

        Spacer(modifier = modifier.weight(1f))

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            ) {

            Text(text = "Let's get started",
                fontSize = 30.sp,
            modifier = modifier.weight(1f))

            IconButton(onClick = onClickSignupScreen) {
                Icon(imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = stringResource(R.string.forward_button))
            }
        }

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = "Already have an account?", fontSize = 20.sp)
            Spacer(modifier = modifier.width(6.dp))

            TextButton(onClick = onClickLoginScreen) {
                Text(text = "Login", fontSize = 20.sp)
            }
        }

        Spacer(modifier = modifier.height(50.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignupScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        SignupScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SignupScreenDarkTheme() {
    EaglewayTheme(darkTheme = true) {
        SignupScreen()
    }
}
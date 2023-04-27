package com.shop.eagleway.ui.main.manage.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel

@Composable
fun SetProfileApp(modifier: Modifier = Modifier, viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.readUserInfoFromDatabase(LocalContext.current)

    SetProfileScreen()
}
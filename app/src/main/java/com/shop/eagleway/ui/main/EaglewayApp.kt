package com.shop.eagleway.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shop.eagleway.R
import com.shop.eagleway.ui.main.*
import com.shop.eagleway.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.shop.eagleway.ui.registration.RegistrationActivity
import com.shop.eagleway.viewmodel.RegistrationViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EaglewayApp(modifier: Modifier = Modifier,
                viewModel: HomeViewModel = viewModel(),
                activity: Activity?,
                ) {

    val navController = rememberNavController()

    viewModel.getTimerValueFromDb(LocalContext.current)
    viewModel.timer(LocalContext.current)
    viewModel.readUserInfoFromDatabase(LocalContext.current)

    Scaffold(bottomBar = {
        BottomNavigation(navController = navController)
    }) {
        NavHost(navController, startDestination = BottomNavItem.HomeScreen.screenRoute) {

            composable(BottomNavItem.HomeScreen.screenRoute) {
                HomeScreen(viewModel = viewModel) {  }
            }
            composable(BottomNavItem.InvoiceScreen.screenRoute) {
                InvoiceScreen(viewModel = viewModel)
            }
            composable(BottomNavItem.OrderScreen.screenRoute) {
                OrderScreen(viewModel = viewModel)
            }
            composable(BottomNavItem.ProductScreen.screenRoute) {
                ProductScreen(viewModel = viewModel)
            }
            composable(BottomNavItem.ManageScreen.screenRoute) {
                ManageScreen(
                    onLogout = {
                    viewModel.logout()
                    activity?.finish()
                    RegistrationActivity.startActivity(activity) },
                    viewModel = viewModel,
                    activity = activity
                    )
            }
        }
    }
}

enum class EaglewayAppScreen(val title: String) {
    HomeScreen("Home"),
    InvoiceScreen("Invoice"),
    OrderScreen("Order"),
    ProductScreen("Product"),
    ManageScreen("Manage")
}

sealed class BottomNavItem(
    var title: String,
    @DrawableRes var icon: Int,
    var screenRoute: String) {

    object HomeScreen: BottomNavItem(
        title = EaglewayAppScreen.HomeScreen.title,
        icon = R.drawable.ic_home,
        screenRoute = EaglewayAppScreen.HomeScreen.name)


    object InvoiceScreen: BottomNavItem(
        title = EaglewayAppScreen.InvoiceScreen.title,
        icon = R.drawable.ic_invoice,
        screenRoute = EaglewayAppScreen.InvoiceScreen.name)


    object OrderScreen: BottomNavItem(
        title = EaglewayAppScreen.OrderScreen.title,
        icon = R.drawable.ic_bag,
        screenRoute = EaglewayAppScreen.OrderScreen.name)


    object ProductScreen: BottomNavItem(
        title = EaglewayAppScreen.ProductScreen.title,
        icon = R.drawable.ic_product,
        screenRoute = EaglewayAppScreen.ProductScreen.name)

    object ManageScreen: BottomNavItem(
        title = EaglewayAppScreen.ManageScreen.title,
        icon = R.drawable.ic_settings,
        screenRoute = EaglewayAppScreen.ManageScreen.name)
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.HomeScreen,
        BottomNavItem.InvoiceScreen,
        BottomNavItem.OrderScreen,
        BottomNavItem.ProductScreen,
        BottomNavItem.ManageScreen
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_1),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(id = R.color.purple_200), colorResource(id = R.color.purple_2)
                    )
                )
            )
            .padding(8.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(50))) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title, modifier = Modifier.size(24.dp)) },
                    label = { Text(text = item.title, fontSize = 12.sp) },
                    selectedContentColor = Color.Yellow,
                    unselectedContentColor = Color.White.copy(0.5f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screenRoute,
                    onClick = {
                        navController.navigate(item.screenRoute) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },

                )
            }
    }
}

package com.shop.eagleway

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shop.eagleway.ui.SignupScreen
import com.shop.eagleway.ui.main.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EaglewayApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
        NavigationGraph(navController = navController)
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
    var icon: ImageVector,
    var screenRoute: String) {

    object HomeScreen: BottomNavItem(
        title = EaglewayAppScreen.HomeScreen.title,
        icon = Icons.Outlined.Home,
        screenRoute = EaglewayAppScreen.HomeScreen.name)


    object InvoiceScreen: BottomNavItem(
        title = EaglewayAppScreen.InvoiceScreen.title,
        icon = Icons.Outlined.Info,
        screenRoute = EaglewayAppScreen.InvoiceScreen.name)


    object OrderScreen: BottomNavItem(
        title = EaglewayAppScreen.OrderScreen.title,
        icon = Icons.Outlined.FavoriteBorder,
        screenRoute = EaglewayAppScreen.OrderScreen.name)


    object ProductScreen: BottomNavItem(
        title = EaglewayAppScreen.ProductScreen.title,
        icon = Icons.Outlined.ArrowBack,
        screenRoute = EaglewayAppScreen.ProductScreen.name)

    object ManageScreen: BottomNavItem(
        title = EaglewayAppScreen.ManageScreen.title,
        icon = Icons.Outlined.Settings,
        screenRoute = EaglewayAppScreen.ManageScreen.name)

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.HomeScreen.screenRoute) {

        composable(BottomNavItem.HomeScreen.screenRoute) {
            HomeScreen()
        }
        composable(BottomNavItem.InvoiceScreen.screenRoute) {
            InvoiceScreen()
        }
        composable(BottomNavItem.OrderScreen.screenRoute) {
            OrderScreen()
        }
        composable(BottomNavItem.ProductScreen.screenRoute) {
            ProductScreen()
        }
        composable(BottomNavItem.ManageScreen.screenRoute) {
            ManageScreen()
        }
    }
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
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = androidx.compose.ui.graphics.Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = androidx.compose.ui.graphics.Color.Black,
                unselectedContentColor = androidx.compose.ui.graphics.Color.Black.copy(0.4f),
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
                }
            )
        }
    }
}

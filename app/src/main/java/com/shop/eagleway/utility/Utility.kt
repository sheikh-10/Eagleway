package com.shop.eagleway.utility

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.shop.eagleway.ui.main.invoice.InvoiceAllScreen
import com.shop.eagleway.ui.main.invoice.InvoicePaidScreen
import com.shop.eagleway.ui.main.invoice.InvoiceUnpaidScreen
import com.shop.eagleway.ui.main.orders.*

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun String.log(tag: String) {
    Log.d(tag, this)
}

typealias ComposableFun = @Composable () -> Unit
sealed class InvoiceTabItem(var title: String, var screen: ComposableFun) {
    object All: InvoiceTabItem(title = "All", screen = { InvoiceAllScreen() })
    object Paid: InvoiceTabItem(title = "Paid", screen = { InvoicePaidScreen() })
    object Unpaid: InvoiceTabItem(title = "Unpaid", screen = { InvoiceUnpaidScreen() })
}

sealed class OrdersTabItem(var title: String, var screen: ComposableFun) {
    object New: OrdersTabItem(title = "New", screen = { NewOrderScreen() })
    object Confirmed: OrdersTabItem(title = "Confirmed", screen = { ConfirmedOrderScreen() })
    object ShipmentReady: OrdersTabItem(title = "Shipment Ready", screen = { ShipmentReadyOrderScreen() })
    object InTransit: OrdersTabItem(title = "In Transit", screen = { InTransitOrderScreen() })
    object Completed: OrdersTabItem(title = "Completed", screen = { CompletedOrderScreen() })
    object Returns: OrdersTabItem(title = "Returns", screen = { ReturnsOrderScreen() })
    object Cancelled: OrdersTabItem(title = "Cancelled", screen = { CancelledOrderScreen() })
}
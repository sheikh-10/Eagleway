package com.shop.eagleway.utility

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.shop.eagleway.ui.main.invoice.InvoiceAllScreen
import com.shop.eagleway.ui.main.invoice.InvoicePaidScreen
import com.shop.eagleway.ui.main.invoice.InvoiceUnpaidScreen
import com.shop.eagleway.ui.main.orders.*
import java.util.Currency
import java.util.Locale

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

fun String.smartTruncate(length: Int): String {
    return if (this.length >= length) this.substring(0, length).plus("...") else this
}

val Int.toCountryFlag: String
    get() {
        val countryCode = Locale.getISOCountries()

        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode[this], 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode[this], 1) - asciiOffset + flagOffset
        return (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
    }

val String.toCurrency: String
    get() {
        val currency = Currency.getInstance(this);
        return currency.symbol
    }

enum class LoadingState {
    True, False
}

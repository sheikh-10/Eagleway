package com.shop.eagleway.ui.main.product

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun ProductsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Search by name or brand")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            },
            modifier = modifier.fillMaxWidth()
            )

        Text(
            text = "You haven't added any product. Add product and then start sharing them or create them or create orders for customers",
            modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.Center),
            textAlign = TextAlign.Center
            )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProductsScreenPreview() {
    EaglewayTheme {
        ProductsScreen()
    }
}

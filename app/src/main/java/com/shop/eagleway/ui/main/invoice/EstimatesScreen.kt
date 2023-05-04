package com.shop.eagleway.ui.main.invoice

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun EstimatesScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = modifier.width(10.dp))
                IconButton(onClick = { (context as Activity).finish() }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close Button",
                    )
                }

                Spacer(modifier = modifier.width(30.dp))
                Text(text = "Estimates", fontSize = 18.sp)

                Spacer(modifier = modifier.weight(1f))

                Icon(imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null,
                    modifier = modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EstimatesScreenPreview() {
    EaglewayTheme {
        EstimatesScreen()
    }
}

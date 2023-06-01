package com.shop.eagleway.ui.main.product

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.eagleway.R
import com.shop.eagleway.data.ProductPreview
import com.shop.eagleway.utility.LoadingState
import com.shop.eagleway.viewmodel.ErrorState
import com.shop.eagleway.viewmodel.ProductViewModel

private const val TAG = "UpdateProductScreen"
@Composable
fun UpdateProductScreen(modifier: Modifier = Modifier,
                        id: Int = 0,
                        productId: String = "",
                        viewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory)) {

    val imagePreviewList by viewModel.addProductUiState.collectAsState()

    val context = LocalContext.current as Activity
    val focusManager = LocalFocusManager.current

    val nameErrorState: ErrorState = viewModel.nameFieldError
    val salesPriceErrorState: ErrorState = viewModel.salesPriceFieldError
    val mrpErrorState: ErrorState = viewModel.mrpFieldError

    var isLastCardRemoved by remember { mutableStateOf(false) }

    var imageResult by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
        imageResult = it
        it?.let {
            if (imagePreviewList.productPreview.size == 6) {
                viewModel.updateImagePreview(
                    id = viewModel.addImageRowId,
                    productPreview = ProductPreview(bitmap = it, isClickable = false)
                )
            } else {
                viewModel.insertImagePreview(ProductPreview(bitmap = it, isClickable = false))
            }
        }
    }

    var categoryField by remember { mutableStateOf("") }

    var measureUnitField by remember { mutableStateOf("") }

    var showButton by remember { mutableStateOf(false) }


    Column {

        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { context.finish() }) {
                    Icon(imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null)
                }

                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Edit Product", fontSize = 18.sp)
            }
        }

        Divider(modifier = modifier.width(5.dp))

        Column(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {

            ImageCardList(
                productPreview = imagePreviewList.productPreview,
                onClick = { launcher.launch() },
                onImageRemove = {
                    when (imagePreviewList.productPreview.size) {
                        in 0..5 -> viewModel.deleteImagePreview(it)
                        else -> {
                            isLastCardRemoved = if (isLastCardRemoved) {
                                viewModel.deleteImagePreview(it)
                                false
                            } else {
                                viewModel.updateImagePreview(
                                    id = viewModel.addImageRowId,
                                    productPreview = ProductPreview(bitmap = null, isClickable = true)
                                )
                                true
                            }
                        }
                    }
                },
                viewModel = viewModel
            )

            Spacer(modifier = modifier.height(10.dp))

            Text(text = "You can add up to maximum 6 images")

            Spacer(modifier = modifier.height(20.dp))

            OutlinedTextField(
                value = viewModel.nameField,
                onValueChange = { viewModel.updateNameField(it) },
                label = {
                    when(nameErrorState) {
                        is ErrorState.Null -> {
                            Text(text = "Name*")
                        }
                        is ErrorState.PriceError -> {
                            Text(text = nameErrorState.message)
                        }
                    }},
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                isError = when(nameErrorState) {
                    is ErrorState.Null -> false
                    is ErrorState.PriceError -> true
                },

                )

            Spacer(modifier = modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = viewModel.salesPriceField.toString(),
                    onValueChange = { viewModel.updateSalesPriceField(it.toIntOrNull() ?: 0) },
                    label = {
                        when(salesPriceErrorState) {
                            is ErrorState.Null -> {
                                Text(text = "Sales Price*")
                            }
                            is ErrorState.PriceError -> {
                                Text(text = salesPriceErrorState.message)
                            }
                        }},
                    modifier = modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(20),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                    isError = when(salesPriceErrorState) {
                        is ErrorState.Null -> false
                        is ErrorState.PriceError -> true
                    }
                )

                OutlinedTextField(
                    value = viewModel.mrpField.toString(),
                    onValueChange = { viewModel.updateMrpField(it.toIntOrNull() ?: 0) },
                    label =  {
                        when(mrpErrorState) {
                            is ErrorState.Null -> {
                                Text(text = "Mrp Price*")
                            }
                            is ErrorState.PriceError -> {
                                Text(text = mrpErrorState.message)
                            }
                        }},
                    modifier = modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(20),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                    isError = when(mrpErrorState) {
                        is ErrorState.Null -> false
                        is ErrorState.PriceError -> true
                    }
                )
            }

            Spacer(modifier = modifier.height(20.dp))

            OutlinedTextField(
                value = categoryField,
                onValueChange = { categoryField = it },
                label = { Text(text = "Category") },
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
            )

            Spacer(modifier = modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = viewModel.quantityField.toString(),
                    onValueChange = { viewModel.updateQuantityField(it.toIntOrNull() ?: 0) },
                    label = { Text(text = "Quantity") },
                    modifier = modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Right) }
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(20),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                )

                OutlinedTextField(
                    value = measureUnitField,
                    onValueChange = { measureUnitField = it },
                    label = { Text(text = "Measuring Units") },
                    modifier = modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(20),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                )
            }

            Spacer(modifier = modifier.height(20.dp))

            OutlinedTextField(
                value = viewModel.descriptionField,
                onValueChange = { viewModel.updateDescriptionField(it) },
                label = { Text(text = "Description") },
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                shape = RoundedCornerShape(20),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
            )

            Spacer(modifier = modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.updateProductInfo(id = id, productId = productId, bitmap = imagePreviewList.productPreview.filter { it.bitmap != null }) {
                        context.finish()
                    }
                },
                modifier = modifier.padding(vertical = 30.dp)
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20)),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_1)),
                enabled = viewModel.state == LoadingState.False
            ) {

                if (viewModel.state == LoadingState.True)  {
                    CircularProgressIndicator(
                        modifier = modifier.size(32.dp),
                        color = Color.LightGray,
                        strokeWidth = 2.dp
                    )

                } else {
                    Text(text = "Save", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }

//        FloatingActionButton(
//            onClick = {
//                viewModel.updateProductInfo(id = id, productId = productId, bitmap = imagePreviewList.productPreview.filter { it.bitmap != null }) {
//                    context.finish()
//                } },
//            modifier = modifier
//                .fillMaxSize()
//                .wrapContentSize(align = Alignment.BottomEnd)
//                .padding(30.dp),
//            backgroundColor = colorResource(id = R.color.purple_1)
//        ) {
//            Icon(imageVector = Icons.Outlined.Check,
//                contentDescription = null,
//                tint = Color.White)
//        }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageCardList(modifier: Modifier = Modifier,
                          productPreview: List<ProductPreview>,
                          onClick: () -> Unit,
                          onImageRemove: (ProductPreview) -> Unit = {},
                          viewModel: ProductViewModel = viewModel()
) {
    LazyRow {
        items(productPreview, key = { it.id }) {
            ImageCard(
                modifier = modifier.animateItemPlacement(),
                onClick = onClick,
                isClickable = it.isClickable,
                productPreview = it,
                onImageRemove = onImageRemove,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun ImageCard(modifier: Modifier = Modifier,
                      onClick: () -> Unit = { },
                      isClickable: Boolean = false,
                      productPreview: ProductPreview = ProductPreview(),
                      onImageRemove: (ProductPreview) -> Unit = {},
                      viewModel: ProductViewModel = viewModel()
) {
    Card(elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 2.dp, color = if (viewModel.imageAddError) Color.Red else colorResource(id = R.color.purple_1)),
        onClick = {
            if (isClickable) {
                onClick()
                viewModel.resetImageAddError()
            }
        },
        modifier = modifier
            .size(100.dp)
            .padding(4.dp)
    ) {

        Column(modifier = modifier.padding(if (isClickable) 10.dp else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,) {

            if (isClickable) {
                Image(painter = painterResource(id = R.drawable.ic_take_photo),
                    contentDescription = null,
                    modifier = modifier.size(40.dp),
                    colorFilter =  if (viewModel.imageAddError) ColorFilter.tint(Color.Red) else ColorFilter.tint(
                        colorResource(id = R.color.purple_1)
                    )
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(text = "Add Image", fontSize = 14.sp, color =  if (viewModel.imageAddError) Color.Red else colorResource(id = R.color.purple_1))
            } else {
                productPreview.bitmap?.let {
                    Box(modifier = modifier.fillMaxSize()) {

                        Image(bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier.fillMaxSize()
                        )

                        FloatingActionButton (
                            onClick = { onImageRemove(productPreview) },
                            modifier = modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.TopEnd)
                                .size(30.dp)
                                .padding(5.dp),
                            backgroundColor = colorResource(id = R.color.purple_1)
                        ) {
                            Icon(imageVector = Icons.Outlined.Close,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateProductScreenPreview() {
    UpdateProductScreen()
}
package com.shop.eagleway.ui.main.product

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.shop.eagleway.data.Category
import com.shop.eagleway.data.Currency
import com.shop.eagleway.data.MeasuringUnit
import com.shop.eagleway.data.ProductPreview
import com.shop.eagleway.model.ImagePreview
import com.shop.eagleway.utility.BottomSheet
import com.shop.eagleway.utility.LoadingState
import com.shop.eagleway.utility.toCountryFlag
import com.shop.eagleway.utility.toCurrency
import com.shop.eagleway.viewmodel.ErrorState
import com.shop.eagleway.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

private const val TAG = "UpdateProductScreen"
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateProductScreen(modifier: Modifier = Modifier,
                        id: Int = 0,
                        productId: String = "",
                        viewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory)) {

    val imagePreviewList by viewModel.addProductUiState.collectAsState()
    val measuringUnitList by viewModel.measuringUnitUiState.collectAsState()

    val categoryUnitList by viewModel.categoryUiState.collectAsState()
    val currencyState by viewModel.currencyUiState.collectAsState()

    val context = LocalContext.current as Activity
    val focusManager = LocalFocusManager.current

    val nameErrorState: ErrorState = viewModel.nameFieldError
    val salesPriceErrorState: ErrorState = viewModel.salesPriceFieldError
    val mrpErrorState: ErrorState = viewModel.mrpFieldError
    val descriptionErrorState: ErrorState = viewModel.descriptionFieldError
    val measuringUnitErrorState: ErrorState = viewModel.measuringUnitError
    val categoryErrorState: ErrorState = viewModel.categoryFieldError
    val currencyErrorState: ErrorState = viewModel.currencyFieldError


    var imageResult by remember { mutableStateOf<Bitmap?>(null) }


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
        imageResult = it
        it?.let {
            if (imagePreviewList.imagePreview.size == 6) {
                viewModel.updateImagePreview(ImagePreview(bitmap = it, isClickable = false))
            } else {
                viewModel.insertImagePreview(ImagePreview(bitmap = it, isClickable = false))
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    var bottomSheet by remember { mutableStateOf(BottomSheet.Category) }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            when (bottomSheet) {
                BottomSheet.Category -> {
                    BottomSheetContentCategory(
                        categoryUnit = categoryUnitList.category,
                        state = modalBottomSheetState,
                        viewModel = viewModel)
                }
                BottomSheet.Currency -> {
                    BottomSheetContentCurrency(
                        currency = currencyState.currency,
                        state = modalBottomSheetState,
                        viewModel = viewModel)
                }
                BottomSheet.MeasuringUnit -> {
                    BottomSheetContentMeasuringUnit(
                        measuringUnit = measuringUnitList.unit,
                        state = modalBottomSheetState,
                        viewModel = viewModel)
                }
            }
        }) {
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
                    Text(text = "Add Product", fontSize = 18.sp)
                }
            }

            Divider(modifier = modifier.width(5.dp))

            Column(modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {

                ImageCardList(
                    imagePreview = imagePreviewList.imagePreview,
                    onClick = { launcher.launch() },
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

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    OutlinedTextField(
                        value = viewModel.categoryField,
                        onValueChange = {  },
                        label = {
                            when(categoryErrorState) {
                                is ErrorState.Null -> {
                                    Text(text = "Category")
                                }
                                is ErrorState.PriceError -> {
                                    Text(text = categoryErrorState.message)
                                }
                            }
                        },
                        trailingIcon = { IconButton(onClick = {

                            bottomSheet = BottomSheet.Category

                            coroutineScope.launch {
                                modalBottomSheetState.show()
                            }
                        }) {
                            Icon(painter = painterResource(id = R.drawable.expand_more), contentDescription = null)
                        } },
                        readOnly = true,
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
                        isError = when(categoryErrorState) {
                            is ErrorState.Null -> false
                            is ErrorState.PriceError -> true
                        }
                    )

                    OutlinedTextField(
                        value = viewModel.currencyField,
                        onValueChange = { },
                        label = {
                            when(currencyErrorState) {
                                is ErrorState.Null -> {
                                    Text(text = "Currency")
                                }
                                is ErrorState.PriceError -> {
                                    Text(text = currencyErrorState.message)
                                }
                            }},
                        trailingIcon = {
                            IconButton(onClick = {
                                bottomSheet = BottomSheet.Currency

                                coroutineScope.launch {
                                    modalBottomSheetState.show()
                                }
                            }) {
                                Icon(painter = painterResource(id = R.drawable.expand_more), contentDescription = null)
                            }
                        },
                        modifier = modifier.weight(1f),
                        readOnly = true,
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
                        isError = when(currencyErrorState) {
                            is ErrorState.Null -> false
                            is ErrorState.PriceError -> true
                        }
                    )
                }

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
                        value = viewModel.measuringUnitField,
                        onValueChange = { },
                        label = {
                            when(measuringUnitErrorState) {
                                is ErrorState.Null -> {
                                    Text(text = "Measuring Unit")
                                }
                                is ErrorState.PriceError -> {
                                    Text(text = measuringUnitErrorState.message)
                                }
                            }},
                        trailingIcon = {
                            IconButton(onClick = {

                                bottomSheet = BottomSheet.MeasuringUnit

                                coroutineScope.launch {
                                    modalBottomSheetState.show()
                                }
                            }) {
                                Icon(painter = painterResource(id = R.drawable.expand_more), contentDescription = null) }
                        },
                        readOnly = true,
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
                        isError = when(measuringUnitErrorState) {
                            is ErrorState.Null -> false
                            is ErrorState.PriceError -> true
                        }
                    )
                }

                Spacer(modifier = modifier.height(20.dp))

                OutlinedTextField(
                    value = viewModel.descriptionField,
                    onValueChange = { viewModel.updateDescriptionField(it) },
                    label = {
                        when(descriptionErrorState) {
                            is ErrorState.Null -> {
                                Text(text = "Description")
                            }
                            is ErrorState.PriceError -> {
                                Text(text = descriptionErrorState.message)
                            }
                        }},
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
                    isError = when(descriptionErrorState) {
                        is ErrorState.Null -> false
                        is ErrorState.PriceError -> true
                    }
                )

                Spacer(modifier = modifier.weight(1f))

                Button(
                    onClick = {
                        viewModel.updateProductInfo(id = id, productId = productId,bitmap = imagePreviewList.imagePreview.filter { it.bitmap != null }) {
                            context.finish()
                        }
                    },
                    modifier = modifier
                        .padding(vertical = 30.dp)
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
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageCardList(modifier: Modifier = Modifier,
                          imagePreview: List<ImagePreview>,
                          onClick: () -> Unit,
                          viewModel: ProductViewModel = viewModel()
) {
    LazyRow {
        items(imagePreview) {
            ImageCard(
                modifier = modifier.animateItemPlacement(),
                onClick = onClick,
                isClickable = it.isClickable,
                imagePreview = it,
                onImageRemove = viewModel::deleteImagePreview,
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
                      imagePreview: ImagePreview = ImagePreview(),
                      onImageRemove: (ImagePreview) -> Unit = {},
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
                    colorFilter =  if (viewModel.imageAddError) ColorFilter.tint(Color.Red) else ColorFilter.tint(colorResource(id = R.color.purple_1))
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(text = "Add Image", fontSize = 14.sp, color =  if (viewModel.imageAddError) Color.Red else colorResource(id = R.color.purple_1))
            } else {
                imagePreview.bitmap?.let {
                    Box(modifier = modifier.fillMaxSize()) {

                        Image(bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier.fillMaxSize()
                        )

                        FloatingActionButton (
                            onClick = {
                                onImageRemove(imagePreview)
                            },
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetContentMeasuringUnit(modifier: Modifier = Modifier,
                                            measuringUnit: List<MeasuringUnit>,
                                            state: ModalBottomSheetState,
                                            viewModel: ProductViewModel = viewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Text(
            text = "Measuring Units",
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(text = "Search by units") },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(
                    id = R.color.purple_3
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        LazyColumn(contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(measuringUnit) {
                if (it.unitKey!!.startsWith(
                        searchText,
                        ignoreCase = true
                    )) {
                    MeasuringUnitCard(measuringUnit = it) { unit ->
                        unit?.let { u -> viewModel.updateMeasuringField(u) }
                        coroutineScope.launch {
                            state.hide()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MeasuringUnitCard(modifier: Modifier = Modifier,
                              measuringUnit: MeasuringUnit = MeasuringUnit(),
                              onClick: (String?) -> Unit = { }) {
    Card(elevation = 2.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = colorResource(id = R.color.purple_3),
        modifier = modifier.clickable(onClick = { onClick(measuringUnit.unitValue) })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(8.dp)) {
            Text(text = measuringUnit.unitKey.toString(), fontSize = 20.sp, color = colorResource(id = R.color.purple_1))
            Spacer(modifier = modifier.weight(1f))
            Text(text = measuringUnit.unitValue.toString(), fontSize = 20.sp, color = colorResource(id = R.color.purple_1))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetContentCurrency(modifier: Modifier = Modifier,
                                       currency: List<Currency>,
                                       state: ModalBottomSheetState,
                                       viewModel: ProductViewModel = viewModel()) {

    val coroutineScope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Text(
            text = "Currency",
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(text = "Search by currency") },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(
                    id = R.color.purple_3
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        LazyColumn(contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(currency) {
                if (it.currencyKey!!.startsWith(
                        searchText,
                        ignoreCase = true
                    )) {

                    CurrencyCard(currency = it) { unit ->
                        unit?.let { u -> viewModel.updateCurrencyField(u) }
                        coroutineScope.launch {
                            state.hide()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CurrencyCard(modifier: Modifier = Modifier,
                         currency: Currency = Currency(),
                         onClick: (String?) -> Unit = { }) {

    Card(elevation = 2.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = colorResource(id = R.color.purple_3),
        modifier = modifier.clickable(onClick = { onClick(currency.currencyKey) })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(8.dp)) {
            Text(text = currency.currencyFlag?.toCountryFlag ?: "", fontSize = 20.sp, color = colorResource(id = R.color.purple_1))
            Spacer(modifier = modifier.width(10.dp))
            Text(text = currency.currencyKey.toString(), fontSize = 20.sp, color = colorResource(id = R.color.purple_1))
            Spacer(modifier = modifier.weight(1f))

            Text(text = currency.currencyKey?.toCurrency ?: "", fontSize = 20.sp, color = colorResource(id = R.color.purple_1))
            Spacer(modifier = modifier.width(10.dp))
            Text(text = currency.currencyValue.toString(), fontSize = 20.sp, color = colorResource(id = R.color.purple_1))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetContentCategory(modifier: Modifier = Modifier,
                                       categoryUnit: List<Category>,
                                       state: ModalBottomSheetState,
                                       viewModel: ProductViewModel = viewModel()) {

    val coroutineScope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    var isAddCategoryClicked by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Text(
            text = "Category",
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(text = "Search by category") },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(
                    id = R.color.purple_3
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(categoryUnit) {
                        if (it.category!!.startsWith(
                                searchText,
                                ignoreCase = true
                            )) {

                            CategoryCard(
                                category = it,
                                onClick =  { category ->
                                    category?.let { u -> viewModel.updateCategoryField(u) }
                                    coroutineScope.launch {
                                        state.hide()
                                    }
                                },
                                onDelete = { category -> viewModel.deleteCategory(category) }
                            )
                        }
                    }
                },
                modifier = modifier.weight(4f)
            )

            Spacer(modifier = modifier.weight(1f))

            IconButton(
                onClick = { isAddCategoryClicked = !isAddCategoryClicked },
                modifier = modifier
                    .clip(RoundedCornerShape(100))
                    .background(
                        color = colorResource(
                            id = R.color.purple_1
                        )
                    ),
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null, tint = Color.Yellow)
            }
        }
    }

    if (isAddCategoryClicked) {
        ShowAddCategoryDialog(
            onDismiss = { isAddCategoryClicked = !isAddCategoryClicked },
            value = viewModel.categoryField,
            onValueChange = viewModel::updateCategoryField,
            onSave = viewModel::saveCategory
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoryCard(modifier: Modifier = Modifier,
                         category: Category = Category(),
                         onClick: (String?) -> Unit = { },
                         onDelete: (category: Category) -> Unit = {}
) {
    Chip(onClick = { onClick(category.category)}) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = category.category.toString(),
                fontSize = 18.sp,
                color = colorResource(id = R.color.purple_1),
                maxLines = 1,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            )
            IconButton(onClick = { onDelete(category) }) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
            }
        }

    }
}

@Preview
@Composable
private fun ShowAddCategoryDialog(modifier: Modifier = Modifier,
                                  onDismiss: () -> Unit = {},
                                  value: String = "",
                                  onValueChange: (String) -> Unit = {},
                                  onSave: () -> Unit = {}) {

    var isClicked by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Category", modifier = modifier.padding(10.dp))
        },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = if (isClicked) "" else "(Eg) Mobile, Laptop") },
                shape = RoundedCornerShape(20),
                interactionSource = remember { MutableInteractionSource() }.also {
                    LaunchedEffect(it) {
                        it.interactions.collect { interation ->
                            if (interation is PressInteraction.Release) {
                                isClicked = !isClicked
                            }
                        }
                    }
                },
            ) },
        confirmButton = {
            Button(onClick = {
                onSave()
                onDismiss()
            }, shape = RoundedCornerShape(50)) {
                Text(text = "Ok") }
        },
        dismissButton = { Button(onClick = onDismiss, shape = RoundedCornerShape(50)) {
            Text(text = "Cancel")
        } },
        shape = RoundedCornerShape(10)
    )
}

@Preview(showBackground = true)
@Composable
private fun UpdateProductScreenPreview() {
    UpdateProductScreen()
}
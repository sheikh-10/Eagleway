package com.shop.eagleway.ui.main.subscription

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.shop.eagleway.R
import com.shop.eagleway.data.Subscription
import com.shop.eagleway.utility.PaymentUtility
import com.shop.eagleway.utility.onPlanClick
import com.shop.eagleway.viewmodel.SubscriptionViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

private const val TAG = "SubscriptionScreen"
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun SubscriptionScreen(modifier: Modifier = Modifier,
                       viewModel: SubscriptionViewModel = viewModel(factory = SubscriptionViewModel.Factory), ) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var isAutoScrolled by remember { mutableStateOf(true) }

    val subscriptionUiState by viewModel.uiState.collectAsState()

//    LaunchedEffect(true) {
//        while (isAutoScrolled) {
//            try {
//                delay(2000L)
//                pagerState.animateScrollToPage(pagerState.currentPage.inc())
//                if (pagerState.currentPage == subscriptionUiState.subscription.lastIndex) {
//                    isAutoScrolled = false
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, e.message.toString())
//            }
//        }
//    }

    Column(modifier = modifier.fillMaxSize()) {
        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Subscription", fontSize = 18.sp)
            }
        }

        SubscriptionPlanList(pagerState = pagerState, subscription = subscriptionUiState.subscription, viewModel = viewModel)

        Spacer(modifier = modifier.height(5.dp))
        PageIndicator(
            numberOfPages = subscriptionUiState.subscription.size,
            selectedPage = pagerState.currentPage,
            defaultRadius = 10.dp,
            selectedLength = 20.dp,
            space = 10.dp,
            animationDurationInMillis = 1000,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )

        Spacer(modifier = modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(16.dp)) {

            if (subscriptionUiState.subscription.isNotEmpty()) {
                items(subscriptionUiState.subscription[pagerState.currentPage].planDetail!!) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = null, tint = colorResource(
                            id = R.color.purple_1
                        ))

                        Column {
                            Text(text = it.title.toString(), style = MaterialTheme.typography.h6, color = colorResource(
                                id = R.color.purple_1
                            ))
                            Text(text = it.description.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    selectedPage: Int = 0,
    selectedColor: Color = colorResource(id = R.color.purple_1),
    defaultColor: Color = Color.LightGray,
    defaultRadius: Dp = 20.dp,
    selectedLength: Dp = 60.dp,
    space: Dp = 30.dp,
    animationDurationInMillis: Int = 300,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage
            PageIndicatorView(
                isSelected = isSelected,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}


@Composable
fun PageIndicatorView(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    animationDurationInMillis: Int,
    modifier: Modifier = Modifier,
) {

    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            defaultColor
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )
    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedLength
        } else {
            defaultRadius
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )

    Canvas(
        modifier = modifier
            .size(
                width = width,
                height = defaultRadius,
            ),
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx(),
            ),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun SubscriptionPlanList(modifier: Modifier = Modifier,
                                 pagerState: PagerState? = null,
                                 subscription: List<Subscription>? = null,
                                 viewModel: SubscriptionViewModel = viewModel()
                                 ) {

    val fling = PagerDefaults.flingBehavior(
        state = pagerState!!,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    HorizontalPager(
        pageCount = subscription!!.size,
        state = pagerState,
        contentPadding = PaddingValues(10.dp),
        pageSpacing = 32.dp,
        flingBehavior = fling
    ) {
        SubscriptionPlanCard(
            pagerState = pagerState,
            page = it,
            subscription = subscription[it],
            viewModel = viewModel
            )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun SubscriptionPlanCard(modifier: Modifier = Modifier,
                                 pagerState: PagerState? = null,
                                 page: Int? = null,
                                 subscription: Subscription? = null,
                                 viewModel: SubscriptionViewModel = viewModel()
                                 ) {

    Card(elevation = 4.dp,
        backgroundColor = colorResource(id = R.color.purple_3),
        shape = RoundedCornerShape(10),
        modifier = modifier.graphicsLayer {

        val pageOffset = (
                (pagerState!!.currentPage - page!!) + pagerState.currentPageOffsetFraction
                ).absoluteValue

        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            Text(text = subscription?.name.toString(), style = MaterialTheme.typography.h4)

            LazyColumn(content = {
                subscription?.plan?.let { plan ->
                    items(plan) {
                        PlanCard(
                            subscriptionPlan = it,
                            onClick = { planId ->
                                viewModel.onPlanClick(plan = it.onPlanClick(plan = subscription.plan, planIndex = planId!!), subIndex = page!!)
                            })
                    }
                }
            }, verticalArrangement = Arrangement.spacedBy(8.dp))

            var paymentState by remember { mutableStateOf(false) }

            viewModel.task.addOnCompleteListener {
                if (it.isComplete) {
                    try {
                        it.getResult(ApiException::class.java)?.let {  state ->
                            paymentState = state
                        }
                    }catch (e: Exception) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }

            OutlinedButton(
                onClick = { viewModel.onUpgradeClicked(pagerState?.currentPage ?: 0) },
                shape = RoundedCornerShape(30),
                enabled = paymentState
                ) {

                if (paymentState) {
                    Text(text = "Upgrade to ${subscription?.name}", color = Color.Black)
                } else {
                    Text(text = "Gpay is not supported on this device")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlanCard(modifier: Modifier = Modifier,
                     subscriptionPlan: Subscription.SubscriptionPlan? = null,
                     onClick: (Int?) -> Unit = {}
                     ) {

    Card(elevation = 4.dp,
        shape = RoundedCornerShape(30),
        backgroundColor = if (subscriptionPlan?.isSelected == true) colorResource(id = R.color.purple_1) else colorResource(id = R.color.light_indigo),
        modifier = modifier.selectable(selected = subscriptionPlan?.isSelected == true, onClick = { onClick(subscriptionPlan?.id) })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(10.dp)) {
            RadioButton(selected = subscriptionPlan?.isSelected == true, onClick = { onClick(subscriptionPlan?.id) }, colors = RadioButtonDefaults.colors(unselectedColor = Color.White))

            Column(modifier = modifier.padding(horizontal = 10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = subscriptionPlan?.name.toString(), style = MaterialTheme.typography.h6, color = Color.White)
                    Spacer(modifier = modifier.weight(1f))
                    Text(text = "$" + subscriptionPlan?.price.toString(), style = MaterialTheme.typography.h6, color = Color.White)
                }

                Text(text = subscriptionPlan?.description.toString(), color = Color.White)
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SubscriptionScreenPreview() {
    SubscriptionScreen()
}
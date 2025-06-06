package com.konfio.test.feature.dogsscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.konfio.common.ErrorMessage
import com.konfio.test.R
import com.konfio.test.feature.dogsscreen.model.DogUi
import com.konfio.test.feature.dogsscreen.viewmodel.ListDogEvent
import com.konfio.test.feature.dogsscreen.viewmodel.ListDogState
import com.konfio.test.feature.dogsscreen.viewmodel.ListDogViewModel
import com.konfio.test.ui.theme.Dimens
import com.konfio.test.ui.theme.Dimens.CardHorizontalPadding
import com.konfio.test.ui.theme.Dimens.CardMiniBackgroundMarginTop
import com.konfio.test.ui.theme.Dimens.CardPadding
import com.konfio.test.ui.theme.Dimens.CardSpacing
import com.konfio.common.LoadingIndicator
import com.konfio.test.ui.theme.Background
import com.konfio.test.ui.theme.Dimens.CardImageHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDogScreen(viewModel: ListDogViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onEvent(ListDogEvent.LoadDogs)
    }
    Scaffold(
        containerColor = Background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_bar_title),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Background
                )
            )
        }) { padding ->
        when (state) {
            is ListDogState.Loading -> {
                LoadingIndicator()
            }

            is ListDogState.Success -> {
                val dogs = (state as ListDogState.Success).dogs
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(CardSpacing),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = CardHorizontalPadding),
                ) {
                    items(dogs) { dog ->
                        DogCardComposable(dog = dog, modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            is ListDogState.Error -> {
                ErrorMessage((state as ListDogState.Error).message)
            }

            else -> {

            }
        }
    }
}

@Composable
fun DogCardComposable(dog: DogUi, modifier: Modifier) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            val (image, miniBackground) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(
                    model = dog.image,
                    placeholder = painterResource(id = R.drawable.xddd),
                    error = painterResource(id = R.drawable.xddd)
                ),
                contentDescription = stringResource(R.string.app_bar_title),
                modifier = Modifier
                    .height(CardImageHeight)
                    .background(Color.White)
                    .clip(RoundedCornerShape(Dimens.CardCornerRadius))
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })

            ConstraintLayout(
                modifier = Modifier
                    .background(Color.White)
                    .padding(CardPadding)
                    .constrainAs(miniBackground) {
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(image.top, margin = CardMiniBackgroundMarginTop)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }) {
                val (text, description, date) = createRefs()
                Text(
                    text = dog.dogName,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)

                    })
                Text(
                    text = dog.description.trim(),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(description) {
                        top.linkTo(text.bottom, margin = Dimens.CardTextMarginTop)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                Text(
                    text = stringResource(id = R.string.dog_age, dog.age),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.constrainAs(date) {
                        top.linkTo(description.bottom, Dimens.CardDateMarginTop)
                        start.linkTo(parent.start)
                    })

            }

        }
    }
}
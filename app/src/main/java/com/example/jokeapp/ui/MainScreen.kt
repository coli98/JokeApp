package com.example.jokeapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jokeapp.R
import com.example.jokeapp.coreui.composecomponents.CustomDialog
import com.example.jokeapp.coreui.composecomponents.largePadding
import com.example.jokeapp.coreui.composecomponents.normalPadding
import com.example.jokeapp.coreui.composecomponents.smallPadding
import com.example.jokeapp.domain.model.JokeDto

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onAction: (action: MainAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        content = {
            val stateSubscriber =
                viewModel.observeState().subscribeAsState(initial = MainState.Loading)

            val stateValue = stateSubscriber.value
            Box(modifier = Modifier.padding(all = normalPadding)) {
                when (stateValue) {
                    MainState.Loading -> LoadingIcon()
                    is MainState.Error -> CustomDialog(
                        onAction = onAction,
                        title = stateValue.errorMessage,
                        confirmBtnText = stringResource(id = R.string.try_again_btn_dialog),
                        confirmAction = MainAction.LoadJokesFromApi
                    )
                    is MainState.Loaded -> ListOfJokes(stateValue.jokeList, onAction)
                    is MainState.NoInternetConnection ->
                        CustomDialog(
                            onAction = onAction,
                            title = stringResource(id = R.string.no_internet_txt),
                            confirmBtnText = stringResource(id = R.string.try_again_btn_dialog),
                            confirmAction = MainAction.LoadJokesFromApi
                        )
                }
            }
        })
}

@Composable
fun LoadingIcon() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ListOfJokes(jokeList: List<JokeDto>, onAction: (action: MainAction) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(smallPadding)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.joke_list_title),
                modifier = Modifier.padding(top = largePadding, start = normalPadding)
            )
        }
        items(jokeList.size) { element ->
            Card(
                onClick = { onAction(MainAction.ShowJokeDelivery(element)) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(normalPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = stringResource(id = R.string.id_text) + jokeList[element].id)
                        Spacer(modifier = Modifier.height(smallPadding))
                        Text(text = jokeList[element].setup)
                        Spacer(modifier = Modifier.height(smallPadding))
                        if (jokeList[element].isDeliveryShowed) {
                            Text(text = jokeList[element].delivery)
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun LoadingIconPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}




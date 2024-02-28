package com.evilstan.mornhousetest.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.evilstan.mornhousetest.R
import com.evilstan.mornhousetest.ui.dialog.WarningDialog
import com.evilstan.mornhousetest.ui.screen.InfoScreen
import com.evilstan.mornhousetest.ui.screen.SearchScreen
import com.evilstan.mornhousetest.ui.viewmodel.SharedViewModel
import com.evilstan.mornhousetest.utils.isNetworkAvailable

@Composable
fun NumbersNavHost(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = Destinations.SearchScreen.route
    ) {
        composable(Destinations.SearchScreen.route) {
            val context = LocalContext.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val uiState by viewModel.uiState.collectAsState()

            if (uiState.isError())
                WarningDialog(
                title = stringResource(R.string.error),
                body = stringResource(R.string.some_error),
                onConfirm = { viewModel.inactivate() }
            )

            if (uiState.isSuccess()){
                navController.navigate(Destinations.InfoScreen.route)
                viewModel.inactivate()
            }

            SearchScreen(
                isLoading = uiState.isLoading(),
                historyFlow = viewModel.historyFlow,
                search = {
                    if (!debounce()) return@SearchScreen

                    if (!isNetworkAvailable(context)) {
                        toastError(context)
                        return@SearchScreen
                    }

                    keyboardController?.hide()
                    viewModel.search(it)
                })
        }

        composable(Destinations.InfoScreen.route) {
            InfoScreen(viewModel.uiState)
        }
    }
}

fun getDestination(destination: String?) =
    listOf(
        Destinations.SearchScreen,
        Destinations.InfoScreen
    ).firstOrNull { it.route == destination } ?: Destinations.SearchScreen

private var debounce = 0L
private fun debounce(): Boolean {
    if (System.currentTimeMillis() - debounce < 1000) return false
    debounce = System.currentTimeMillis()
    return true
}

private fun toastError(context: Context) =
    Toast.makeText(context, context.getString(R.string.err_network), Toast.LENGTH_SHORT).show()


private object Destinations {
    object SearchScreen : Destination {
        override val route = "search_screen"
        override val title = R.string.numbers_api
        override val showNavigation = false
    }

    object InfoScreen : Destination {
        override val route = "info_screen"
        override val title = R.string.number_info
        override val showNavigation = true
    }
}

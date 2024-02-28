package com.evilstan.mornhousetest.ui.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.evilstan.mornhousetest.MainActivity
import com.evilstan.mornhousetest.R
import com.evilstan.mornhousetest.ui.dialog.WarningDialog
import com.evilstan.mornhousetest.ui.navigation.NumbersNavHost
import com.evilstan.mornhousetest.ui.navigation.getDestination
import com.evilstan.mornhousetest.ui.viewmodel.SharedViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route
    val viewModel: SharedViewModel = hiltViewModel(LocalContext.current as MainActivity)

    val topBar: @Composable () -> Unit = {
        TopBar(
            title = stringResource(getDestination(currentDestination).title),
            showNavButton = getDestination(currentDestination).showNavigation,
            onNavClick = { navController.popBackStack() })
    }

    Scaffold(topBar = topBar) {
        NumbersNavHost(it, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(title: String, showNavButton: Boolean, onNavClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(title)
            }
        },

        navigationIcon = {
            var showInfo by rememberSaveable { mutableStateOf(false) }

            if (showInfo)
                WarningDialog(title = stringResource(R.string.info),
                    body = { AboutBody() },
                    onConfirm = { showInfo = false })

            if (showNavButton)
                IconButton(onClick = { onNavClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = " "
                    )
                } else {
                IconButton(onClick = { showInfo = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = " "
                    )
                }
            }
        }
    )
}

@Composable
private fun AboutBody() {
    Column {
        val context = LocalContext.current

        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            modifier = Modifier.clickable {
                val uri = Uri.parse("https://t.me/evilstan")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("org.telegram.messenger")
                try {
                    context.startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(context, context.getString(R.string.no_tg), Toast.LENGTH_SHORT).show()
                }
            },
            text = stringResource(R.string.contact),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
            textDecoration = TextDecoration.Underline
        )
    }
}
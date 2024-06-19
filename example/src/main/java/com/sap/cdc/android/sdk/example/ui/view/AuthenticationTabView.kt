package com.sap.cdc.android.sdk.example.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sap.cdc.android.sdk.example.ui.viewmodel.CredentialsRegistrationViewModel
import com.sap.cdc.android.sdk.example.ui.viewmodel.CredentialsRegistrationViewModelPreview
import com.sap.cdc.android.sdk.example.ui.viewmodel.SignInViewModelModel
import com.sap.cdc.android.sdk.example.ui.viewmodel.SignInViewModelPreview
import kotlinx.coroutines.launch


/**
 * Created by Tal Mirmelshtein on 10/06/2024
 * Copyright: SAP LTD.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthenticationTabView(selected: Int) {
    val tabs = listOf("Register", "Sign In")

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size }, initialPage = selected)
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = selectedTabIndex.value == pagerState.currentPage,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedTabIndex.value) {
                    0 -> CredentialsRegistrationView(
                        viewModel = CredentialsRegistrationViewModel(
                            context =
                            LocalContext.current
                        )
                    )

                    1 -> SignInPageView(
                        viewModel = SignInViewModelModel(
                            context =
                            LocalContext.current
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AuthenticationTabViewPreview() {
    val tabs = listOf("Register", "Sign In")

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size }, initialPage = 0)
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = selectedTabIndex.value == pagerState.currentPage,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedTabIndex.value) {
                    0 -> CredentialsRegistrationView(
                        viewModel = CredentialsRegistrationViewModelPreview(
                            context =
                            LocalContext.current
                        )
                    )

                    1 -> SignInPageView(
                        viewModel = SignInViewModelPreview(
                            context =
                            LocalContext.current
                        )
                    )
                }
            }
        }
    }
}

package com.sap.cdc.android.sdk.example.ui.view.flow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sap.cdc.android.sdk.example.ui.view.custom.ActionOutlineButton
import com.sap.cdc.android.sdk.example.ui.view.custom.IndeterminateLinearIndicator
import com.sap.cdc.android.sdk.example.ui.view.custom.TitledText
import com.sap.cdc.android.sdk.example.ui.view.custom.UpdatableEditBox
import com.sap.cdc.android.sdk.example.ui.viewmodel.IViewModelAuthentication
import com.sap.cdc.android.sdk.example.ui.viewmodel.ViewModelAuthenticationPreview


/**
 * Created by Tal Mirmelshtein on 20/06/2024
 * Copyright: SAP LTD.
 *
 * Account information view.
 * Allow user to update the name of the account using setAccountInfo API.
 */

@Composable
fun AboutMeView(viewModel: IViewModelAuthentication) {

    var loading by remember { mutableStateOf(false) }
    var setError by remember { mutableStateOf("") }

    var name by remember {
        mutableStateOf(
            "${viewModel.accountInfo()?.profile?.firstName ?: ""} " +
                    (viewModel.accountInfo()?.profile?.lastName ?: "")
        )
    }

    // UI elements
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        // Title box
        Box(
            modifier = Modifier
                .height(height = 36.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Text(
                "About Me", fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
            )
        }

        // Name (dynamic edit box) - can be updated.
        UpdatableEditBox(
            title = "Name: ",
            initialValue = name
        ) {
            name = it
        }
        HorizontalDivider(
            modifier = Modifier
                .height(3.dp)
                .background(Color.LightGray)
        )

        // Email (static)
        TitledText(
            title = "Email",
            value = viewModel.accountInfo()?.profile?.email ?: ""
        )
        HorizontalDivider(
            modifier = Modifier
                .height(3.dp)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Error message
        if (setError.isNotEmpty()) {
            Spacer(modifier = Modifier.size(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Cancel,
                    contentDescription = "",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = setError,
                    color = Color.Red,
                )
            }
        }

        // Save Changes button
        ActionOutlineButton(
            modifier = Modifier,
            text = "Save changes",
            onClick = {
                loading = true
                viewModel.updateAccountInfoWith(
                    name = name,
                    success = {
                        loading = false
                    },
                    onFailed = { error ->
                        loading = false
                        setError = error.errorDetails!!
                    }
                )
            }
        )

        // Loading indicator on top of all views.
        Box(Modifier.fillMaxWidth()) {
            IndeterminateLinearIndicator(loading)
        }
    }
}

@Preview
@Composable
fun AboutMeViewPreview() {
    AboutMeView(viewModel = ViewModelAuthenticationPreview())
}


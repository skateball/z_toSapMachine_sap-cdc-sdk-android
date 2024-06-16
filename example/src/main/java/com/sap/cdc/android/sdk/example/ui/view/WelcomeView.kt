package com.sap.cdc.android.sdk.example.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sap.cdc.android.sdk.example.ui.route.NavigationCoordinator
import com.sap.cdc.android.sdk.example.ui.route.ProfileScreenRoute

/**
 * Created by Tal Mirmelshtein on 10/06/2024
 * Copyright: SAP LTD.
 */

@Composable
fun WelcomeView(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.size(140.dp))
        Text("Welcome!", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text("Manage your profile", fontSize = 16.sp, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.size(20.dp))

        SocialSelectionView {

        }

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedButton(
            modifier = Modifier.size(width = 240.dp, height = 44.dp),
            shape = RoundedCornerShape(6.dp),
            onClick = {
                NavigationCoordinator.INSTANCE
                    .navigate("${ProfileScreenRoute.AuthTabView.route}/1")
            }) {
            Text("Sign in")
        }
        Spacer(modifier = Modifier.size(20.dp))

        OutlinedButton(
            modifier = Modifier.size(width = 240.dp, height = 44.dp),
            shape = RoundedCornerShape(6.dp),
            onClick = {
                NavigationCoordinator.INSTANCE
                    .navigate("${ProfileScreenRoute.AuthTabView.route}/0")
            }) {
            Text("Register")
        }
        Spacer(modifier = Modifier.size(28.dp))
        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.size(
          240.dp,  1.dp
        ))
        Spacer(modifier = Modifier.size(28.dp))
        Text("Sign in with SSO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun WelcomeViewPreview() {
    WelcomeView(navController = rememberNavController())
}



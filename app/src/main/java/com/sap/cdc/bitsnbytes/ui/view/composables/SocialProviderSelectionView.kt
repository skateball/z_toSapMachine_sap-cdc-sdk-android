package com.sap.cdc.bitsnbytes.ui.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sap.cdc.bitsnbytes.R

/**
 * Created by Tal Mirmelshtein on 10/06/2024
 * Copyright: SAP LTD.
 *
 * Custom view for social login UI selection Row.
 * View may appear in different composable views.
 */

@Composable
fun SocialProviderSelectionView(
    onSocialProviderSelection: (String) -> Unit,
    onMutableValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 74.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            modifier = Modifier.size(52.dp),
            onClick = {
                onMutableValueChange(true)
                onSocialProviderSelection("facebook")
            }) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.facebook_v),
                contentDescription = "Localized description",
                tint = Color.Unspecified
            )
        }
        IconButton(
            modifier = Modifier.size(52.dp),
            onClick = {
                // Google social login.
                onMutableValueChange(true)
                onSocialProviderSelection("google")
            }) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.google_v),
                contentDescription = "Localized description",
                tint = Color.Unspecified
            )

        }
        IconButton(
            modifier = Modifier.size(52.dp),
            onClick = {
                // Apple social login.
                onMutableValueChange(true)
                onSocialProviderSelection("apple")
            }) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.apple_v),
                contentDescription = "Localized description",
                tint = Color.Unspecified
            )
        }
        IconButton(
            modifier = Modifier.size(52.dp),
            onClick = {
                // Line social login.
                onMutableValueChange(true)
                onSocialProviderSelection("line")
            }) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_line),
                contentDescription = "Localized description",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
fun SocialSelectionViewPreview() {
    SocialProviderSelectionView({}, {})
}

@Composable
fun ViewDynamicSocialSelection(
    socialProviders: List<String>,
    onSocialProviderSelection: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 74.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        socialProviders.forEach { provider ->
            IconButton(
                modifier = Modifier.size(52.dp),
                onClick = {
                    // Line social login.
                    onSocialProviderSelection(provider)
                }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = providerIcon(provider),
                    contentDescription = "Localized description",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
fun ViewDynamicSocialSelectionPreview() {
    ViewDynamicSocialSelection(listOf("google", "facebook")) { }
}

/**
 * Helper extension method for composable view to provide the correct painter resource for
 * String social provider name.
 */
@Composable
fun providerIcon(provider: String): Painter {
    when (provider) {
        "line" -> {
            return painterResource(id = R.drawable.ic_line)
        }

        "google" -> {
            return painterResource(id = R.drawable.google_v)
        }

        "facebook" -> {
            return painterResource(id = R.drawable.facebook_v)
        }

        "apple" -> {
            return painterResource(id = R.drawable.apple_v)
        }
        "linkedIn" -> {
            return painterResource(id = R.drawable.linkedin_v)
        }

        else -> {
            return painterResource(id = R.drawable.ic_logo)
        }
    }
}

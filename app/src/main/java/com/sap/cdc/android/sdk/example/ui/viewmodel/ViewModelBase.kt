package com.sap.cdc.android.sdk.example.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sap.cdc.android.sdk.example.cdc.IdentityServiceRepository
import kotlinx.serialization.json.Json

/**
 * Created by Tal Mirmelshtein on 18/06/2024
 * Copyright: SAP LTD.
 *
 * Base class for application view models.
 */
open class ViewModelBase(context: Context) : ViewModel() {

    /**
     * Available Json interface.
     */
    val json = Json { ignoreUnknownKeys = true }

    /**
     * Identity service repository instance.
     */
    val identityService: IdentityServiceRepository =
        IdentityServiceRepository.getInstance(context)
}
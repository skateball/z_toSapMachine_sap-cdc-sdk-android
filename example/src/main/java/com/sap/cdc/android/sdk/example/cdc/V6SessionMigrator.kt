package com.sap.cdc.android.sdk.example.cdc

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import java.math.BigInteger
import java.security.KeyStore
import java.util.Arrays
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Created by Tal Mirmelshtein on 10/06/2024
 * Copyright: SAP LTD.
 */

class V6SessionMigrator(private val context: Context) {

    private var keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
    private var preferences: SharedPreferences =
        context.getSharedPreferences(pref_file, Context.MODE_PRIVATE)

    init {
        keyStore.load(null)
    }

    companion object {
        const val alias = "GS_ALIAS_V2"
        const val pref_file = "GSLIB"
        const val pref_session_entry = "GS_PREFS"
        const val pref_session_iv_spec = "IV_session"
        const val transformation_algorithm = "AES/GCM/NoPadding"

        const val TAG = "V6SessionMigrator"
    }

    /**
     * Check if the Android Keystore has an old session alias.
     */
    fun sessionAvailableForMigration(): Boolean = keyStore.containsAlias(alias)

    /**
     * Get session keystore key for decryption.
     */
    private fun getKeyV2(): SecretKey? {
        keyStore.load(null)
        if (keyStore.containsAlias(alias)) {
            // Alias available. Key generated.
            val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
            return secretKeyEntry.secretKey
        }
        return null
    }

    /**
     * Clear old SDK keystore alias.
     */
    private fun clearKeyV2() {
        keyStore.deleteEntry(alias)
    }

    /**
     * Clear old SDK shared preferences entries.
     */
    private fun clearPrefsV2() {
        preferences.edit()
            .remove(pref_session_entry)
            .remove(pref_session_iv_spec).apply()
        context.deleteSharedPreferences(pref_file)
    }

    /**
     * Try to fetch available session.
     */
    fun getSession(
        success: (String?) -> Unit,
        error: (String) -> Unit
    ) {
        val cipher = Cipher.getInstance(transformation_algorithm)

        val encryptedSession: String? = preferences.getString(pref_session_entry, null)
        if (encryptedSession == null) {
            Log.e(TAG, "Session not available for migration")
            error("$TAG: Session not available")
            return
        }

        val ivSpecString: String? =
            preferences.getString(pref_session_iv_spec, null)
        if (ivSpecString == null) {
            Log.e(TAG, "Session not migrated to GCM. Cannot be migrated")
            error("$TAG: Session not migrated to GCM. Cannot be migrated")
            return
        }

        // Decrypt session.
        val ivSpec = GCMParameterSpec(128, Base64.decode(ivSpecString, Base64.DEFAULT))
        cipher.init(Cipher.DECRYPT_MODE, getKeyV2(), ivSpec)
        val encPLBytes = encryptedSession.stringToBytes()
        val sessionString = String(cipher.doFinal(encPLBytes))

        // Delete v6 keystore entry.
        clearKeyV2()
        // Delete v6 preferences records.
        clearPrefsV2()

        success(sessionString)
    }
}

fun String.stringToBytes(): ByteArray {
    val b2 = BigInteger(this, 36).toByteArray()
    return Arrays.copyOfRange(b2, 1, b2.size)
}
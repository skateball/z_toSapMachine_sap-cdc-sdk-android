package com.sap.cdc.android.sdk.example.extensions


/**
 * Helper extension method to split first/last name.
 */
fun String.splitFullName(name: String): Pair<String, String> {
    val index = name.lastIndexOf("")
    val firstName = index.let { it1 -> name.substring(0, it1) }
    val lastName = index.plus(1).let { it1 -> name.substring(it1) }
    return Pair(firstName, lastName)
}

/**
 * Helper extension method to parse String value for required missing fields list.
 */
fun String.parseRequiredMissingFieldsForRegistration(): List<String> {
    val fields = this.substringAfterLast(": ")
    return fields.split(", ")
}
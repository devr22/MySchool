package com.dev.myschool.models

import com.google.firebase.Timestamp

data class SubjectMessage(
    val message: String,
    val timestamp: Timestamp
) {
    constructor() : this("", Timestamp.now())
}

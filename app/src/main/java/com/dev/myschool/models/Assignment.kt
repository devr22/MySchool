package com.dev.myschool.models

import com.google.firebase.Timestamp

data class Assignment(
    val id: String,
    val name: String,
    val subject: String,
    val taskLink: String,
    val done: Boolean,
    val missing: Boolean,
    val dueDate: Timestamp
) {
    constructor() : this("", "", "", "", false, false, Timestamp.now())
}

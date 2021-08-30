package com.dev.myschool.models

data class User(
    val uid: String,
    val email: String,
    val userType: Int,
    val subjectList: ArrayList<Subject>?
) {
    constructor() : this("", "", -1, null)
}

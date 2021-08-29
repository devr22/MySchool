package com.dev.myschool.models

data class Subject(
    val name: String,
    val teacher: String
) {
    constructor() : this("", "")
}

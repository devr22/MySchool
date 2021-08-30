package com.dev.myschool.models

data class Subject(
    val name: String,
    val teacher: String,
    val classLink: String,
    val timing: String
) {
    constructor() : this("", "", "", "")
}

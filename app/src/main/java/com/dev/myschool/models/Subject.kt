package com.dev.myschool.models

data class Subject(
    val id: String,
    val name: String,
    val teacher: String,
    val classLink: String,
    val timing: String
) {
    constructor() : this("", "", "", "", "")
}

package com.dev.myschool.models

import com.google.firebase.Timestamp

data class Test(
    var name: String,
    var subject: String,
    var timestamp: Timestamp,
    var testLink: String,
    var fullMarks: Int,
    var obtainedMarks: Int?
) {
    constructor() : this("", "", Timestamp.now(), "", 0, null)
}

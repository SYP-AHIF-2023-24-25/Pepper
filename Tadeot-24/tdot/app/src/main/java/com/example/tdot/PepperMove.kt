package com.example.tdot

import kotlin.time.Duration.Companion.milliseconds

data class PepperMove(val title: String, val fileDance: Int, val duration: kotlin.time.Duration)

fun getMoves(): List<PepperMove> {
    return listOf(
        PepperMove(
            title = "Miss The Rage",
            fileDance = R.raw.dance_b003,
            duration = 20.milliseconds
        )
    )
}

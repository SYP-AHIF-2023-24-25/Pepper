package com.example.tdot

data class Dance(
    val id: String,
    val title: String,
    val dancePath: Int,
    val musicPath: Int )

fun getListOfDances(): List<Dance> {
    val listOfDances: List<Dance> =
        listOf(
            Dance(
                id = "1",
                title = "testDance 01",
                dancePath = R.raw.dance_b001,
                musicPath = R.raw.clash_of_clans_raiding
            )
        )
    return listOfDances
}

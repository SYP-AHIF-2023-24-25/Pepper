package com.example.tdot

data class Dance(
    val id: Int,
    val title: String,
    val dancePath: Int,
    val musicPath: Int )

public fun getListOfDances(): List<Dance> {
    val listOfDances: List<Dance> =
        listOf(
            Dance(
                id = 1,
                title = "testDance 01",
                dancePath = R.raw.dance_b003,
                musicPath = R.raw.clash_of_clans_raiding
            ),
            Dance(
                id = 2,
                title = "Breath",
                dancePath = R.raw.pepper_dance_fein,
                musicPath = R.raw.playboi_carti_stop_breathing_official_audio_k4zvzbrb
            )
        )
    return listOfDances
}

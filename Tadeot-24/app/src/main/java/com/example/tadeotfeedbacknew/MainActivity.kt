package com.example.tadeotfeedbacknew

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL


class MainActivity {




    fun main() {
        val apiUrl = "https://vm64.htl-leonding.ac.at/tadeot-backend/api/questions"
        val questions = retrieveQuestions(apiUrl)

        if (questions.isNotEmpty()) {
            questions.forEach { println(it) }
        } else {
            println("Fragen konnten nicht abgerufen werden.")
        }
    }

    fun retrieveQuestions(apiUrl: String): List<Question> {
        val url = URL(apiUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        return try {
            val json = connection.inputStream.bufferedReader().use { it.readText() }
            parseQuestions(json)
        } finally {
            connection.disconnect()
        }
    }

    fun parseQuestions(json: String): List<Question> {
        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}
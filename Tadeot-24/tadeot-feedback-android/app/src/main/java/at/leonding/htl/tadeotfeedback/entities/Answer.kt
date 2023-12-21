package at.leonding.htl.tadeotfeedback.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Answer (
        @SerializedName("timestamp")
        @Expose
        val timestamp: String,

        @SerializedName("questionText")
        @Expose
        val questionText: String,

        @SerializedName("questionId")
        @Expose
        val questionId: Int,

        @SerializedName("questionNumber")
        @Expose
        val questionNumber: Int,

        @SerializedName("answer")
        @Expose
        val answer: String,

        @SerializedName("detailsQuestion")
        @Expose
        val detailsQuestion: String,

        @SerializedName("detailText")
        @Expose
        val detailText: String)
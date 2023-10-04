package at.leonding.htl.tadeotfeedback.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Answer (
        @SerializedName("questionNumber")
        @Expose
        val questionNumber: Int,
        @SerializedName("rating")
        @Expose
        var rating : Int = 0,
        @SerializedName("detailText")
        @Expose
        var detailText : String = "")
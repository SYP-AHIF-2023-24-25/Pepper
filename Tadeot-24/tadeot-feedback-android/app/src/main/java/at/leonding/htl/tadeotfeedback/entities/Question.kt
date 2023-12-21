package at.leonding.htl.tadeotfeedback.entities

//data class Question (val id : Int, val number : Int, val questionType : Int, val titleGerman : String, val titleEnglish : String,
//                     val subTitleGerman : String, val subTitleEnglish : String, val detailsGerman : List<String>, val detailsEnglish : List<String>)


/*
data class Question (val number : Int, val questionType : Int, val titleGerman : String, val titleEnglish : String,
                     val subTitleGerman : String, val subTitleEnglish : String, val detailsGerman : List<String>,
                     val detailsEnglish : List<String>, val bonusQuestion : String)



data class Question(
    val id: Int,  // Falls benötigt
    val number: Int,
    val questionType: Int,
    val titleGerman: String,
    val titleEnglish: String,
    val subTitleGerman: String,
    val subTitleEnglish: String,
    val detailsGerman: List<String>,
    val detailsEnglish: List<String>,
    val bonusQuestion: String,
    val options: List<String>,  // Hinzugefügt, falls benötigt
    val detailsIf: List<String>,  // Hinzugefügt, falls benötigt
    val detailsQuestion: String  // Hinzugefügt, falls benötigt
)*/

data class Question(
    val id: Int,
    val number: Int,
    val title: String,
    val subTitle: String,
    val options: List<String>,
    val detailsIf: List<String>,
    val detailsQuestion: String,
    val details: List<String>

){
    fun toXmlString(): String {
        return """
            <Question>
                <Number>${number}</Number>
                <Title>${title}</Title>
                <Details>${detailsQuestion}</Details>
            </Question>
        """.trimIndent()
    }
};



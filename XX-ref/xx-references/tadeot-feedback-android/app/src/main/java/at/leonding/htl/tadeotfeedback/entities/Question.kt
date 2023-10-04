package at.leonding.htl.tadeotfeedback.entities

//data class Question (val id : Int, val number : Int, val questionType : Int, val titleGerman : String, val titleEnglish : String,
//                     val subTitleGerman : String, val subTitleEnglish : String, val detailsGerman : List<String>, val detailsEnglish : List<String>)


data class Question (val number : Int, val questionType : Int, val titleGerman : String, val titleEnglish : String,
                     val subTitleGerman : String, val subTitleEnglish : String, val detailsGerman : List<String>,
                     val detailsEnglish : List<String>, val bonusQuestion : String)

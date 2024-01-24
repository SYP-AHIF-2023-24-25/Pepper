package at.htlleonding.visitorassistant.model.data

data class QuestionData(
    val timestamp: String,
    val questionText: String,
    val questionId: Int,
    val questionNumber: Int,
    val answer: String,
    val detailsQuestion: String,
    val detailText: String
    /*
 var QuestionId: Int,

 var QuestionNumber: Int,
 var Answer: String,
 var DetailsQuestion: String,
 var DetailText: String?*/
)

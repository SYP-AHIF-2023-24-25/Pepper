package at.leonding.htl.tadeotfeedback
import at.leonding.htl.tadeotfeedback.api.BackendService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

fun main() {
    val backendService = BackendService
    val questionsApi = backendService.getQuestionsApi()

    val compositeDisposable = CompositeDisposable()

    val observable = questionsApi.getQuestions()
        .subscribeOn(Schedulers.io())
        .subscribe(
            { questions ->
                // Hier kannst du die Liste von Fragen weiterverarbeiten
                questions.forEach {
                    println("Number: ${it.number}, Title: ${it.title}")
                }
            },
            { error ->
                println("Fehler beim Laden der Fragen: ${error.message}")
            }
        )

    compositeDisposable.add(observable)

    // Warte kurz, um sicherzustellen, dass die Anfrage abgeschlossen ist, bevor das Programm endet
    Thread.sleep(5000)

    // Aufräumen und beenden
    compositeDisposable.dispose()
}

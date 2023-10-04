# Tadeot - Bestehende Software

## Backend v2023

- https://vm64.htl-leonding.ac.at/tadeot-backend-v23/swagger

### Feedback Questions

- https://vm64.htl-leonding.ac.at/tadeot-backend-v23/api/questions

Grundsätzlich gibts zwei Arten von Fragen:

- Typ `Rating`: Hier kann man 1 - 5 angeben, und wenn 4 oder 5, dann wird noch nach entsprechenden Details gefragt (=> warum war das Rating so schlecht?)

```json
[
  {
    "id": 1,
    "number": 1,
    "questionType": "Rating",
    "title": "Wie hat dir der Tag der offenen Tür insgesamt gefallen?",
    "subTitle": "",
    "bonus": "Der Test1",
    "details": [
      "Unfreundlichkeit",
      "Lange Wartezeiten",
      "Gedränge",
      "Falsche Erwartungshaltung",
      "Sonstiges"
    ]
  },
```

- Typ `Selection`: Hier werden von vorneherein 5 Details abgefragt und man muss eine der 5 Optionen auswählen:

```json
  {
    "id": 8,
    "number": 8,
    "questionType": "Selection",
    "title": "Welchen Zweig würden Sie gerne besuchen?",
    "subTitle": "",
    "bonus": "Der Test8",
    "details": [
      "Informatik",
      "Medientechnik",
      "Elektronik",
      "Medizintechnik",
      "Keinen"
    ]
  }
]
```

### Feedback Answer Post-Endpoint

- https://vm64.htl-leonding.ac.at/tadeot-backend-v23/api/answers

Der AddAnswer-Endpoint erwartet folgenden body:

- Beispiel auf Frage Nummer 1 bezogen - gute Bewertung:

```json
{
  "QuestionNumber": 1,
  "Rating": 2
}
```

- Beispiel auf Frage Nummer 1 bezogen - schlechte Bewertung:

```json
{
  "QuestionNumber": 1,
  "Rating": 4,
  "DetailText": "Falsche Erwartungshaltung"
}
```

- Beispiel auf Frage Nummer 8 bezogen:

```json
{
  "QuestionNumber": 8,
  "Rating": 0,
  "DetailText": "Informatik"
}
```

### Feedback Answers - Auswertung

Leider gibts bisher erst einen CSV-Download mit allen Antworten:

- https://vm64.htl-leonding.ac.at/tadeot-backend-v23/api/answers/csvfile

## Angular Frontends v2023

- https://vm64.htl-leonding.ac.at/admin
- Zugangsdaten erfragen (stell ich nicht in ein public repo)

## Feedback - Android Kotlin

- siehe `tadeot-feedback-android`
- 2020 in Verwendung auf Android Tablets - ich bin mir nicht sicher ob exakt diese Version :(

### Feedback - TODOs

- Feedback-AndroidKotlin-App auf neue HTL Designs upgraden, backend-url fixen, testen ...
- Fragen überarbeiten (=> derzeit nur CSV-Import beim Erstellen der DB ...)
- Feedback-App eventuell auf Angular PWA umstellen?
  - => dann könnte man einen QR-Code mit dem Link am Pepper anzeigen
  - => Besucher laden die PWA und antworten selbst am Handy
  - => Dabei muss aber ein "sent"-Token in den local storage geschrieben werden, damit von einem Handy nur eine Feedback-Beantwortung möglich ist

## Feedback Config/Auswertung: TODOs

- Konfiguration der Feedback Fragen in Tadeot-Admin
- Auswertungs-Frontend (ähnlich wie Besucher-Statistik Slideshow)

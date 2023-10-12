import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DataService } from 'src/services/data.service';
import { Answer, Question, RestService } from 'src/services/rest.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  constructor(
    private router: Router,
    private dataService: DataService,
    private restClient: RestService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar) { }

  questions: Array<Question> = [];
  question: Question | undefined = undefined;
  current = -1;
  title = '';
  options = [''];
  isDetail = false;
  showSpinner = true;

  currentAnswer: Answer | undefined;

  ngOnInit(): void {
    this.restClient.getQuestions()
      .subscribe(data => {
        //console.log(data);
        if (data) {
          this.questions = data;
          this.current = 0;

          this.showQuestion();

        }
      },
        error => {
          console.log(error);
          this.snackBar.open('Laden der Feedback-Fragen fehlgeschlagen', 'OK',
            {
              duration: 5000
            });
          this.router.navigate(['start']);
        });
  }

  async save() {
    this.showSpinner = true;
    this.dataService.storeAnswer(this.currentAnswer!);
    if (this.current + 1 === this.questions.length) {
      this.finished();
    }
    else {
      ++this.current;
      this.showQuestion();
    }
  }

  answer(ratingAnswer: number) {
    if (this.isDetail) {
      this.currentAnswer!.detailText = this.options[ratingAnswer];
      this.isDetail = false;
      this.save();
    }
    else {
      this.currentAnswer = {
        questionId: this.question!.id,
        questionNumber: this.question!.number,
        answer: this.question!.options[ratingAnswer],
        detailText: ''
      }
      const detailsIndex = this.question?.detailsIf.findIndex(d => d === this.currentAnswer?.answer);
      //console.log(this.question);
      console.log(detailsIndex);
      if (detailsIndex !== undefined && detailsIndex > -1) {
        this.isDetail = true;
        this.showDetailQuestion();
      }
      else {
        this.save();
      }
    }

  }

  showQuestion() {
    this.question = this.questions[this.current];
    if (this.question) {
      this.title = this.question.title;
      this.options = this.question.options;
    }
    this.showSpinner = false;
  }
  showDetailQuestion() {
    this.title = this.question!.detailsQuestion;
    this.options = this.question!.details;
  }

  async finished() {
    this.openDialog();
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogQuestion, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(async result => {
      this.showSpinner = true;
      //// console.log(`The dialog was closed with answer ${result}`);
      if (result === 'no') {

        this.dataService.clearStorage();

      }
      else {
        try {
          await this.dataService.postAnswers();

        } catch (error) {
          console.log(error);

          let ref = this.snackBar.open(`Speichern nicht möglich`, "OK", {
            duration: 5000,
          });
          this.dataService.clearStorage();
        }

      }
      this.router.navigate(['start']);
      this.showSpinner = false;

    });
  }
}

export interface DialogData {
  answer: string;
}
@Component({
  selector: 'dialog-question',
  templateUrl: 'dialog-question.html',
})

export class DialogQuestion {

  constructor(
    public dialogRef: MatDialogRef<DialogQuestion>) { }

  clicked(answer: string) {
    this.dialogRef.close(answer);
  }
}

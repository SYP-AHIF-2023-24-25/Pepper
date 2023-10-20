import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

export interface Question {

  id: number;
  number: number;
  title: string;
  subTitle: string;
  options: string[];
  detailsIf: string[];
  detailsQuestion: string;
  details: string[];
}

export interface Answer {
  questionId: number;
  questionNumber: number;
  answer: string;
  detailText: string;
}

export interface Result {
  question: string;
  category: string;
  count: number;
}
export interface AnswerResults {
  mainResult: Result[];
  detailResults: Result[];
}

@Injectable({
  providedIn: 'root'
})
export class RestService {

  baseUrl = '';

  constructor(
    private http: HttpClient) {
    this.baseUrl = "https://vm64.htl-leonding.ac.at/tadeot-backend/api/";
  }
  getQuestions(): Observable<Question[]> {
    let headers: HttpHeaders = new HttpHeaders();
    console.log(this.baseUrl);
    return this.http.get<Question[]>(
      this.baseUrl + 'questions',
      { headers });
  }

  getAnswerResults(questionNumber: number): Observable<AnswerResults> {
    let headers: HttpHeaders = new HttpHeaders();
    return this.http.get<AnswerResults>(
      this.baseUrl + 'answers/statistics?questionNumber=' + questionNumber,
      { headers });
  }

  async postAnswer(answer: Answer) {
    let headers: HttpHeaders = new HttpHeaders();
    return await this.http.post(
      this.baseUrl + 'answers',
      answer,
      { headers: headers }).toPromise();
  }

  async postAllAnswers(answers: Answer[]) {
    let headers: HttpHeaders = new HttpHeaders();
    return await this.http.post(
      this.baseUrl + 'answers/addAll',
      answers,
      { headers: headers }).toPromise();
  }
}

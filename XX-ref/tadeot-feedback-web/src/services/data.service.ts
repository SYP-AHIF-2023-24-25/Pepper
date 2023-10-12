import { RestService } from './rest.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Answer } from 'src/services/rest.service';

export interface AnswerStorage {
  answers: Answer[];
  sent: Date | undefined;
}

export const storageKey = 'tadeot-feedback-answers';
@Injectable({
  providedIn: 'root'
})
export class DataService {


  constructor(
    private httpClient: HttpClient,
    private restService: RestService) {
  }

  getAnswers(): Answer[] | undefined {
    let current = localStorage.getItem(storageKey);
    if (current) {
      const storage: AnswerStorage = JSON.parse(current);
      return storage.answers;
    }
    return undefined;
  }

  storeAnswer(answer: Answer) {
    let current = localStorage.getItem(storageKey);
    console.log(current);
    let storage: AnswerStorage = {
      answers: [],
      sent: undefined
    }
    if (current) {
      storage = JSON.parse(current);
    }
    storage.answers.push(answer);
    localStorage.setItem(storageKey, JSON.stringify(storage));
  }

  clearStorage() {
    localStorage.removeItem(storageKey);
  }

  async postAnswers() {
    const answers = this.getAnswers();
    if (answers) {
      await this.restService.postAllAnswers(answers);
      this.clearStorage();
      localStorage.setItem('tadeot-feedback-sent', JSON.stringify(new Date()));
    }
  }
}

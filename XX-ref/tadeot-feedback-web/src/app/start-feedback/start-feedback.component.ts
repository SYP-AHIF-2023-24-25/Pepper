import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-start-feedback',
  templateUrl: './start-feedback.component.html',
  styleUrls: ['./start-feedback.component.scss']
})
export class StartFeedbackComponent implements OnInit {

  constructor(public router: Router) { }

  token: string | null = null;
  timeSent = new Date();
  timer: any;

  ngOnInit(): void {


    this.token = localStorage.getItem('tadeot-feedback-sent');
    if (this.token !== null) {
      this.timeSent = JSON.parse(this.token);
      this.timer = setTimeout(() => {
        localStorage.removeItem('tadeot-feedback-sent');
        this.token = null;
      }, 3000);
    }
  }

  ngOnDestroy(): void {
    clearTimeout(this.timer);
  }

}

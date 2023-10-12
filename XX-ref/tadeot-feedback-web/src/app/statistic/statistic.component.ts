import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ChartType, GoogleChartComponent } from 'angular-google-charts';
import { Question, RestService, Result } from 'src/services/rest.service';

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.scss']
})
export class StatisticComponent implements OnInit {

  pieChartType = ChartType.PieChart;
  pieOptions = {
    is3D: true
  }
  columnChartType = ChartType.ColumnChart;
  hasDetails = false;

  @ViewChild('mainChart') mainChartElement!: GoogleChartComponent | undefined;
  @ViewChild('detailsChart') detailsChartElement!: GoogleChartComponent | undefined;

  detailsOptions = {
    title: '',
    legend: {
      position: 'left'
    },
    bar: { groupWidth: "15%" },
    sliceVisibilityThreshold: 0.05,
    pieResidueSliceLabel: "Andere",
    vAxis: {
      format: '0'
    }
  }
  yesNoOptions = {
    title: '',
    pieHole: 0.4,
    legend: {
      position: 'left'
    },
    //colors: ['#3366CC', '#DC3912'],
    pieSliceText: 'percentage',
    sliceVisibilityThreshold: 0
  }

  data = [['', 0]];
  detailsData = [['', 0]];
  questions: Question[] = [];
  current = -1;
  hasResults = false;

  constructor(
    public router: Router,
    private restService: RestService) { }

  getColumnCount() {
    return this.hasDetails ? 2 : 1;
  }
  async ngOnInit() {
    const questions = await this.restService.getQuestions().toPromise();
    if (questions && questions.length > 0) {
      this.questions = questions;
      //console.log(this.questions);
      this.next();
    }
  }

  hasNext() {
    return this.questions !== undefined && this.questions.length > this.current + 1;
  }

  hasPrevious() {
    return this.questions !== undefined && this.current - 1 >= 0;
  }

  getOptions() {

    this.yesNoOptions.title = `${this.questions[this.current].title}\n${this.questions[this.current].subTitle}`;
    return this.yesNoOptions;
  }

  async previous() {
    this.current--;
    await this.showQuestion();
  }

  async showQuestion() {
    const results = await this.restService.getAnswerResults(this.questions[this.current].number).toPromise();
    this.hasResults = results!.mainResult.length > 0;
    console.log(results);
    if (results) {

      this.yesNoOptions.title = `${results.mainResult[0].question}\n${this.questions[this.current].subTitle}`;
      this.data = this.map(results.mainResult);
      if (this.mainChartElement) {
        console.log(this.yesNoOptions.title);
        this.mainChartElement.chartWrapper.setOption('title', this.yesNoOptions.title);
        this.mainChartElement.chartWrapper.draw();
      }
      if (results.detailResults.length > 0) {
        this.hasDetails = true;
        this.detailsOptions.title = results.detailResults[0].question;
        this.detailsData = this.map(results.detailResults);
        if (this.detailsChartElement) {
          this.detailsChartElement.chartWrapper.setOption('title', this.detailsOptions.title);
          this.detailsChartElement.chartWrapper.draw();
        }

      }
      else {
        this.hasDetails = false;
        this.detailsData = [['', 0]];
        this.detailsChartElement!.chartWrapper.draw();
      }

    }
    else {
      console.log('Keine Antworten');
    }
  }

  async next() {
    this.current++;
    await this.showQuestion();
  }

  public map(data: Result[]) {
    return data.map(r => [r.category, r.count]);
  }

}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuestionComponent } from './question/question.component';
import { StartFeedbackComponent } from './start-feedback/start-feedback.component';
import { StatisticComponent } from './statistic/statistic.component';

const routes: Routes = [
  { path: '', redirectTo: 'start', pathMatch: 'full' },
  { path: 'start', component: StartFeedbackComponent },
  { path: 'question', component: QuestionComponent },
  { path: 'statistic', component: StatisticComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TimetableComponent} from "./timetable/timetable.component";

const routes: Routes = [
  {path: "timetable", component: TimetableComponent},
  {path: '**', redirectTo: 'timetable'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

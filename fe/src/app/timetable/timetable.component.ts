import {Component, OnInit} from '@angular/core';
import {HttpService} from "../services/http.service";
import {Teacher} from "../model/teacher";
import {Class} from "../model/class";
import {Unit} from "../model/unit";
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.scss']
})
export class TimetableComponent implements OnInit {

  teachers: Teacher[] = [];
  classes: Class[] = [];
  selectedClass?: string;
  units?: Unit[];
  days = ['Mo', 'Di', 'Mi', 'Do', 'Fr'];
  timeUnits = new Array(10);
  unitsToUpdate: Unit[] = [];

  constructor(private readonly http: HttpService) {
  }

  ngOnInit(): void {
    this.http.listTeachers().subscribe(data => this.teachers = data);
    this.http.listClasses().subscribe(data => {
      this.classes = data
      console.log(data)
    });
  }

  updateTimetable() {
    this.units = undefined;
    this.http.getUnitsForClass(this.selectedClass ?? '').subscribe(data => {
      this.units = data;
      for (let day = 1; day <= this.days.length; day++) {
        for (let unit = 1; unit <= this.timeUnits.length; unit++) {
          if (!this.getUnit(unit, day)) {
            let u: Unit = {
              id: -1,
              day: day,
              unit: unit,
              teacherId: -1,
              subject: 'frei',
              classId: this.selectedClass ?? ''
            }
            this.units.push(u);
          }
        }
      }
    });
  }

  getUnit(unit: number, day: number): Unit | undefined {
    if (!this.units) return undefined;
    return this.units.filter(u => {
      return u.unit == unit && u.day == day
    }).pop();
  }

  updateUnit(unit: Unit) {
    if (!this.unitsToUpdate.includes(unit))
      this.unitsToUpdate.push(unit);
    console.log(this.unitsToUpdate)
  }

  saveChanged() {
    this.unitsToUpdate.forEach((unit) => {
      this.http.update(unit).subscribe(() => {
        console.log("updated");
        this.updateTimetable();
      });
    })
  }
}

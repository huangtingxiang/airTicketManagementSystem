import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material';


@Component({
  selector: 'app-flight-management-add',
  templateUrl: './flight-management-add.component.html',
  styleUrls: ['./flight-management-add.component.css']
})
export class FlightManagementAddComponent implements OnInit {

  flightManagementForm: FormGroup;

  nowDate = new Date();

  constructor(private fb: FormBuilder,
              private adapter: DateAdapter<any>) {
    adapter.setLocale('ch');
  }

  ngOnInit() {
    this.flightManagementForm = this.fb.group({
      startTime: [null, Validators.required],
      arrivalTime: [null, Validators.required],
      startingPlace: [null, Validators.required],
      destination: [null, Validators.required],
      startingAirPort: [null, Validators.required],
      destinationAirPort: [null, Validators.required],
      plane: [null, Validators.required]
    });
  }

}

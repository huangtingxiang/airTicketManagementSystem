import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatTableDataSource} from '@angular/material';
import {FlightManagement} from '../../../entity/FlightManagement';

@Component({
  selector: 'app-flight-management-index',
  templateUrl: './flight-management-index.component.html',
  styleUrls: ['./flight-management-index.component.css']
})
export class FlightManagementIndexComponent implements OnInit {

  searchForm: FormGroup;

  filterForm: FormGroup;

  dataSource: MatTableDataSource<FlightManagement> = new MatTableDataSource<FlightManagement>([new FlightManagement()]);

  // 显示的列
  displayedColumns: string[] = ['airlineCompany', 'plane', 'startTime', 'arrivalTime', 'startingAirPort', 'destinationAirPort', 'operation'];

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.searchForm = this.fb.group({
      startingPlace: [null],
      destination: [null],
      startTime: [null]
    });
    this.filterForm = this.fb.group({
      airlineCompany: [null]
    });
  }

  pageChange(event) {
    console.log(event);
  }

}

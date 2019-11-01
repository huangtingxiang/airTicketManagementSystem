import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material';
import {AirPortService} from '../../../core/service/air-port.service';
import {City} from '../../../entity/City';
import {AirPort} from '../../../entity/AirPort';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {AirlineCompany} from '../../../entity/AirlineCompany';
import {PlaneService} from '../../../core/service/plane.service';
import {Pageable} from '../../../entity/norm/pageable';
import {Plane} from '../../../entity/Plane';


@Component({
  selector: 'app-flight-management-add',
  templateUrl: './flight-management-add.component.html',
  styleUrls: ['./flight-management-add.component.css']
})
export class FlightManagementAddComponent implements OnInit {

  flightManagementForm: FormGroup;

  airlineCompanySearch: FormControl = new FormControl(null);

  nowDate = new Date();

  startingAirPorts: AirPort[] = [];

  destinationAirPorts: AirPort[] = [];

  airlineCompanies: AirlineCompany[] = [];

  planes: Plane[] = [];

  allPlane: Plane[] = [];

  constructor(private fb: FormBuilder,
              private airPortService: AirPortService,
              private planeService: PlaneService,
              private airlineCompanyService: AirlineCompanyService,
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
    // 注册当始发地改变时 过滤始发机场
    this.flightManagementForm.get('startingPlace').valueChanges.subscribe((value: City) => {
      if (value && value.id) {
        this.airPortService.getByCity(value.id)
          .subscribe((airPorts) => {
            this.startingAirPorts = airPorts;
          });
      } else {
        this.startingAirPorts = [];
      }
      this.flightManagementForm.get('startingAirPort').setValue(null);
    });
    // 当目的地改变时 过滤目的机场
    this.flightManagementForm.get('destination').valueChanges.subscribe((value: City) => {
      if (value && value.id) {
        this.airPortService.getByCity(value.id)
          .subscribe((airPorts) => {
            this.destinationAirPorts = airPorts;
          });
      } else {
        this.destinationAirPorts = [];
      }
      this.flightManagementForm.get('destinationAirPort').setValue(null);
    });
    // 初始化飞机
    this.planeService.pageByName('', new Pageable(100, 0))
      .subscribe((planesPage) => {
        this.planes = planesPage.content;
        this.allPlane = this.planes;
      });
    // 获取所有的航空公司
    this.airlineCompanyService.getAll()
      .subscribe((airlineCompanies) => {
        this.airlineCompanies = airlineCompanies;
      });
    // 当航空公司改变时过滤飞机
    this.airlineCompanySearch.valueChanges
      .subscribe((airlineCompany: AirlineCompany) => {
        if (airlineCompany && airlineCompany.id) {
          this.planeService.getByAirlineCompany(airlineCompany.id)
            .subscribe((planes) => {
              this.planes = planes;
            });
        } else {
          this.planes = this.allPlane;
        }
        this.flightManagementForm.get('plane').setValue(null);
      });
  }

}

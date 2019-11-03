import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material';
import {AirPortService} from '../../../core/service/air-port.service';
import {City} from '../../../entity/City';
import {AirPort} from '../../../entity/AirPort';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {AirlineCompany} from '../../../entity/AirlineCompany';
import {PlaneService} from '../../../core/service/plane.service';
import {Pageable} from '../../../entity/norm/pageable';
import {Plane} from '../../../entity/Plane';
import {FlightManagementService} from '../../../core/service/flight-management.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-flight-management-add',
  templateUrl: './flight-management-add.component.html',
  styleUrls: ['./flight-management-add.component.css']
})
export class FlightManagementAddComponent implements OnInit {

  flightManagementForm: FormGroup; // 航班表单

  airlineCompanySearch: FormControl = new FormControl(null); // 航空公司搜索框

  nowDate = new Date(); // 当前时间

  startingAirPorts: AirPort[] = []; // 起始机场集合

  destinationAirPorts: AirPort[] = []; // 到达机场集合

  airlineCompanies: AirlineCompany[] = []; // 航空公司集合

  planes: Plane[] = []; // 飞机集合

  allPlane: Plane[] = []; // 所有飞机

  hours: number[] = []; // 小时

  minutes: number[] = []; // 分钟

  seconds: number[] = []; // 秒

  startTime: FormGroup; // 开始时间

  arrivalTime: FormGroup; // 结束时间

  ticketPrices: FormArray; // 舱位价钱

  constructor(private fb: FormBuilder,
              private airPortService: AirPortService,
              private planeService: PlaneService,
              private router: Router,
              private flightManagementService: FlightManagementService,
              private airlineCompanyService: AirlineCompanyService,
              private adapter: DateAdapter<any>) {
    adapter.setLocale('ch');
  }

  ngOnInit() {
    this.ticketPrices = this.fb.array([]);
    // 初始化时分秒
    this.initTime();
    // 初始化航班表单
    this.initFlightManagementForm();
    // 注册相应级联事件
    this.registerValueChange();
    // 初始化数据
    this.initData();
  }

  //  初始化表单
  initFlightManagementForm() {
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

  // 初始化 时 分 秒
  initTime() {
    for (let i = 0; i <= 23; i++) {
      this.hours.push(i);
    }
    for (let i = 0; i <= 59; i++) {
      this.seconds.push(i);
      this.minutes.push(i);
    }
    const time = {
      hour: [0, Validators.required],
      minute: [0, Validators.required],
      second: [0, Validators.required]
    };
    this.startTime = this.fb.group(time);
    this.arrivalTime = this.fb.group(time);
  }

  // 注册相应级联事件
  registerValueChange() {
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
    // 当飞机改变是 改变舱位formarray
    this.flightManagementForm.get('plane').valueChanges.subscribe((plane: Plane) => {
      this.ticketPrices.clear();
      if (plane && plane.id) {
        plane.shipSpaces.forEach((shipSpace) => {
          this.ticketPrices.push(this.fb.group({
            shipSpace: [shipSpace, Validators.required],
            price: [0, Validators.required]
          }));
        });
      }
    });
  }

  // 初始化数据
  initData() {
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

  }

  save() {
    // 设置始发时间和到达时间
    const startDate = new Date(this.flightManagementForm.get('startTime').value);
    const arrivalDate = new Date(this.flightManagementForm.get('arrivalTime').value);
    startDate.setHours(this.startTime.get('hour').value, this.startTime.get('minute').value, this.startTime.get('second').value);
    arrivalDate.setHours(this.arrivalTime.get('hour').value, this.arrivalTime.get('minute').value, this.arrivalTime.get('second').value);
    const flightManagement = this.flightManagementForm.value;
    flightManagement.startTime = startDate;
    flightManagement.arrivalTime = arrivalDate;
    flightManagement.ticketPrices = this.ticketPrices.value;
    this.flightManagementService.save(flightManagement)
      .subscribe(() => {
        this.router.navigateByUrl('/flight-management');
      });
  }
}

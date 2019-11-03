import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog, MatPaginator, MatTableDataSource} from '@angular/material';
import {FlightManagement} from '../../../entity/FlightManagement';
import {FlightManagementService} from '../../../core/service/flight-management.service';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {AirlineCompany} from '../../../entity/AirlineCompany';
import {AirPort} from '../../../entity/AirPort';
import {CommonService} from '../../../core/service/common.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';

@Component({
  selector: 'app-flight-management-index',
  templateUrl: './flight-management-index.component.html',
  styleUrls: ['./flight-management-index.component.css']
})
export class FlightManagementIndexComponent implements OnInit {

  searchForm: FormGroup; // 搜索表单组

  filterForm: FormGroup; // 过滤表单组

  pageable = new Pageable(1000, PAGEABLE.page); // 分页信息

  dataSource: MatTableDataSource<FlightManagement> = new MatTableDataSource<FlightManagement>([]); // 数据源

  // 显示的列
  displayedColumns: string[]
    = ['airlineCompany', 'plane', 'startTime', 'arrivalTime', 'startingAirPort', 'destinationAirPort', 'operation'];

  searchTime: any[] = []; // 始发时间过滤选项

  airlineCompanies: AirlineCompany[] = []; // 航空公司过滤选项

  startAirports: AirPort[] = []; // 起飞机场过滤选项

  allAirport = {name: '全部选择', id: null}; // 机场默认 值

  allCompany = {name: '全部选择', id: null}; // 航空公司默认值

  allManagement: FlightManagement[] = [];

  // 分页dom
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private fb: FormBuilder,
              private dialog: MatDialog,
              private commonService: CommonService,
              private flightManagementService: FlightManagementService) {
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.initSearchTime();
    this.initFilterForm();
    this.initSearchForm();
  }

  initSearchTime() {
    this.searchTime.push({label: '全部时间段', value: null});
    this.searchTime.push({label: '00:00~6:00', value: {start: 0, end: 6}});
    this.searchTime.push({label: '6:00~12:00', value: {start: 6, end: 12}});
    this.searchTime.push({label: '12:00~18:00', value: {start: 12, end: 18}});
    this.searchTime.push({label: '18:00~24:00', value: {start: 18, end: 24}});
  }

  // 初始化搜索表单
  initSearchForm() {
    this.searchForm = this.fb.group({
      startingPlace: [null],
      destination: [null],
      startTime: [null]
    });
  }

  // 初始化过滤表单
  initFilterForm() {
    this.filterForm = this.fb.group({
      startingTime: [this.searchTime[0]],
      airlineCompany: [this.allCompany],
      startAirport: [this.allAirport]
    });
  }

  pageChange(event) {
  }

  search() {
    this.flightManagementService
      .pageByStartingPlaceAndDestinationStartTime(
        this.searchForm.get('startingPlace').value.id,
        this.searchForm.get('destination').value.id,
        this.searchForm.get('startTime').value, this.pageable)
      .subscribe((data) => {
        this.dataSource.data = data.content;
        this.allManagement = data.content;
        this.setData();
      });
  }

  setData() {
    // 提取出所有航空公司和起飞机场
    this.airlineCompanies = [];
    this.startAirports = [];

    this.filterForm.get('startAirport').setValue(this.allAirport);
    this.startAirports.push(this.allAirport as AirPort);

    this.filterForm.get('airlineCompany').setValue(this.allCompany);
    this.airlineCompanies.push(this.allCompany as AirlineCompany);

    this.dataSource.data.forEach((management: FlightManagement) => {
      if (!this.commonService.findEntityInArray(this.airlineCompanies, management.plane.airlineCompany)) {
        this.airlineCompanies.push(management.plane.airlineCompany);
      }

      if (!this.commonService.findEntityInArray(this.startAirports, management.startingAirPort)) {
        this.startAirports.push(management.startingAirPort);
      }
    });
  }

  filter() {
    this.dataSource.data = this.allManagement.filter((management: FlightManagement) => {
      const starting = this.filterForm.get('startingTime').value;
      // 过滤时间
      if (starting && starting.value) {
        const date = new Date(management.startTime);
        if (!(date.getHours() >= starting.value.start && date.getHours() < starting.value.end)) {
          return false;
        }
      }
      // 过滤航空公司
      const company = this.filterForm.get('airlineCompany').value;
      if (company && company.id) {
        if (management.plane.airlineCompany.id !== company.id) {
          return false;
        }
      }
      // 过滤始发机场
      const airport = this.filterForm.get('startAirport').value;
      if (airport && airport.id) {
        if (airport.id !== management.startingAirPort.id) {
          return false;
        }
      }
      return true;
    });
  }

  delete(id: number) {
    this.flightManagementService.delete(id)
      .subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter((manager => manager.id !== id));
      }, (response: HttpErrorResponse) => {
        const title = '删除失败';
        let content;
        if (response.status === 409) {
          content = '存在约束关系，不可删除';
        } else {
          content = '服务器错误';
        }
        const dialogRef = this.dialog.open(ErrorDialogComponent, {
          width: '500px',
          data: {title, content}
        });
      });
  }

}

import { Component, OnInit } from '@angular/core';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {MatTableDataSource, PageEvent} from '@angular/material';
import {AirPort} from '../../../entity/AirPort';
import {Page} from '../../../entity/norm/page';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {AirlineCompany} from '../../../entity/AirlineCompany';

@Component({
  selector: 'app-airline-company-index',
  templateUrl: './airline-company-index.component.html',
  styleUrls: ['./airline-company-index.component.css']
})
export class AirlineCompanyIndexComponent implements OnInit {

  // 显示的列
  displayedColumns: string[] = ['index', 'name', 'city', 'operation'];

  pageable: Pageable = PAGEABLE;

  dataSource: MatTableDataSource<AirlineCompany>;

  totalElements: number;

  constructor(private airlineCompanyService: AirlineCompanyService) {
  }

  ngOnInit() {
    this.reload();
  }

  reload() {
    this.airlineCompanyService.getAllByPage(this.pageable)
      .subscribe((pageData: Page<AirlineCompany>) => {
        this.dataSource = new MatTableDataSource<AirPort>(pageData.content);
        this.totalElements = pageData.totalElements;
        console.log(this.totalElements, pageData);
      });
  }

  pageChange(event: PageEvent) {
    this.pageable.page = event.pageIndex;
    this.pageable.size = event.pageSize;
    this.reload();
  }

}

import {Component, OnInit} from '@angular/core';
import {MatTableDataSource, PageEvent} from '@angular/material';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {AirPort} from '../../../entity/AirPort';
import {AirPortService} from '../../../core/service/air-port.service';
import {Page} from '../../../entity/norm/page';

@Component({
  selector: 'app-air-port-index',
  templateUrl: './air-port-index.component.html',
  styleUrls: ['./air-port-index.component.css']
})
export class AirPortIndexComponent implements OnInit {

  // 显示的列
  displayedColumns: string[] = ['index', 'name', 'city', 'operation'];

  pageable: Pageable = new Pageable(PAGEABLE.size, PAGEABLE.page);

  dataSource: MatTableDataSource<AirPort>;

  totalElements: number;

  constructor(private airPortService: AirPortService) {
  }

  ngOnInit() {
    this.reload();
  }

  reload() {
    this.airPortService.getAllByPage(this.pageable)
      .subscribe((pageData: Page<AirPort>) => {
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

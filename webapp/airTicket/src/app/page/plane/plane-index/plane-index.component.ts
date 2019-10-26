import {Component, OnInit} from '@angular/core';
import {MatTableDataSource, PageEvent} from '@angular/material';
import {Plane} from '../../../entity/Plane';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {PlaneService} from '../../../core/service/plane.service';
import {Page} from '../../../entity/norm/page';
import {AirPort} from '../../../entity/AirPort';

@Component({
  selector: 'app-plane-index',
  templateUrl: './plane-index.component.html',
  styleUrls: ['./plane-index.component.css']
})
export class PlaneIndexComponent implements OnInit {

  dataSource: MatTableDataSource<Plane>;

  totalElements: number;

  // 显示的列
  displayedColumns: string[] = ['index', 'name', 'planeType', 'airlineCompany', 'shipSpaces', 'seats', 'operation'];

  pageable: Pageable = new Pageable(PAGEABLE.size, PAGEABLE.page);

  constructor(private planeService: PlaneService) {
  }

  ngOnInit() {
    this.reload();
  }

  pageChange(event: PageEvent) {
    this.pageable.page = event.pageIndex;
    this.pageable.size = event.pageSize;
    this.reload();
  }

  reload() {
    this.planeService.getAllByPage(this.pageable)
      .subscribe((pageData: Page<Plane>) => {
        this.dataSource = new MatTableDataSource<Plane>(pageData.content);
        this.totalElements = pageData.totalElements;
      });
  }

}

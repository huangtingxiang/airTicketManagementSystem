import {Component, OnInit} from '@angular/core';
import {Pageable} from '../../../entity/norm/pageable';
import {PAGEABLE} from '../../../config/PageInfo';
import {MatDialog, MatTableDataSource, PageEvent} from '@angular/material';
import {AirPort} from '../../../entity/AirPort';
import {Page} from '../../../entity/norm/page';
import {AirlineCompanyService} from '../../../core/service/airline-company.service';
import {AirlineCompany} from '../../../entity/AirlineCompany';
import {FormControl} from '@angular/forms';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorDialogComponent} from '../../../share/component/error-dialog/error-dialog.component';

@Component({
  selector: 'app-airline-company-index',
  templateUrl: './airline-company-index.component.html',
  styleUrls: ['./airline-company-index.component.css']
})
export class AirlineCompanyIndexComponent implements OnInit {

  searchCtrl: FormControl = new FormControl('');

  // 显示的列
  displayedColumns: string[] = ['index', 'name', 'city', 'operation'];

  pageable: Pageable = new Pageable(PAGEABLE.size, PAGEABLE.page);

  dataSource: MatTableDataSource<AirlineCompany> = new MatTableDataSource<AirlineCompany>([]);

  totalElements: number;

  constructor(private airlineCompanyService: AirlineCompanyService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.reload();
    // 输入框变化时加载分页数据
    this.searchCtrl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
    ).subscribe((data) => {
      this.reload();
    });
  }

  reload() {
    this.airlineCompanyService.pageByName(this.searchCtrl.value, this.pageable)
      .subscribe((airlinePage: Page<AirlineCompany>) => {
        this.dataSource.data = airlinePage.content;
        this.totalElements = airlinePage.totalElements;
      });
  }

  pageChange(event: PageEvent) {
    this.pageable.page = event.pageIndex;
    this.pageable.size = event.pageSize;
    this.reload();
  }

  // 删除
  delete(id: number) {
    this.airlineCompanyService.delete(id)
      .subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter((city => city.id !== id));
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
